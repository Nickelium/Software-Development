package Controller;

import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.SubSystem;
import Model.Roles.Lead;
import Model.Roles.Programmer;
import Model.Roles.Tester;
import Model.Tags.Assigned;
import Model.Tags.Closed;
import Model.Tags.New;
import Model.User.*;
import Model.Wrapper.IListWrapper;
import Model.Wrapper.ListWrapper;

public class Initializer implements IInitializer
{
	private UserService userService;
	private ProjectService projectService;
	private BugReportService bugReportService;

	public void init(){
		this.userService = new UserService();
		// refactor ProjectService class first
		this.projectService = new ProjectService();
		this.bugReportService = new BugReportService(projectService);

		// init users
		Admin sam = (Admin) userService.addAdmin("Frederick","Curtis","curt", "Sam");
		Issuer doc = (Issuer) userService.addIssuer("John","","Doctor","doc");
		Issuer charlie = (Issuer) userService.addIssuer("Charles","Arnold","Berg","charlie");
		Developer major = (Developer) userService.addDeveloper("Joseph","","Mays","major");
		Developer maria = (Developer) userService.addDeveloper("Maria","","Carney","maria");

		Lead leadMajor = new Lead(major);
		Project projectA = projectService.addProject("ProjectA", leadMajor);
		Programmer programmerMajor = new Programmer(major);
		projectA.addRole(programmerMajor);
		Tester testerMaria = new Tester(maria);
		projectA.addRole(testerMaria);
		
		SubSystem subSystemA1 = new SubSystem("SubSystemA1");
		SubSystem subSystemA2 = new SubSystem("SubSystemA2");
		SubSystem subSystemA3 = new SubSystem("SubSystemA3");
		SubSystem subSystemA31 = new SubSystem("SubSystemA3.1");
		SubSystem subSystemA32 = new SubSystem("SubSystemA3.2");
		
		projectA.addSubSystem(subSystemA1);
		projectA.addSubSystem(subSystemA2);
		projectA.addSubSystem(subSystemA3);
		try
		{
		subSystemA3.addSubSystem(subSystemA31);
		subSystemA3.addSubSystem(subSystemA32);
		
		
		Lead leadMaria = new Lead(maria);
		Project projectB = projectService.addProject("ProjectB", leadMaria);
		Programmer programmerMajorB = new Programmer(major);
		projectB.addRole(programmerMajorB);
		
		SubSystem subSystemB1 = new SubSystem("SubSystemB1");
		SubSystem subSystemB2 = new SubSystem("SubSystemB2");
		SubSystem subSystemB21 = new SubSystem("SubSystemB2.1");
		
		projectB.addSubSystem(subSystemB1);
		projectB.addSubSystem(subSystemB2);
		
		subSystemB2.addSubSystem(subSystemB21);
		
		
		BugReport bugReport1 = bugReportService.createBugReport("The function parse_ewd returns unexpected results",
				"If the function parse_ewd is invoked while ...", 
				subSystemB1,
				new Closed());
		bugReport1.addAssignee(maria);
		
		BugReport bugReport2 = bugReportService.createBugReport("Crash while processing user input",
				"If incorrect user input is entered into the system ...", 
				subSystemA31,
				new Assigned());
		
		BugReport bugReport3 = bugReportService.createBugReport("SubsystemA2 feezes",
				"If the function process_dfe is invoked with ...", 
				subSystemA2,
				new New());
		
		bugReport1.addAssignee(major);
		bugReport1.addAssignee(maria);
		}
		catch(Exception e)
		{
			//invalid input
			e.printStackTrace();
			System.exit(1);
		}

	}

	public UserService getUserService() 
	{
		return userService;
	}

	public ProjectService getProjectService() 
	{
		return projectService;
	}

	public BugReportService getBugReportService() 
	{
		return bugReportService;
	}


}
