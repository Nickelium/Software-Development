package Controller;

import CustomExceptions.ModelException;
import Model.BugReport.BugReportService;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.SubSystem;
import Model.Project.TheDate;
import Model.Roles.Lead;
import Model.Roles.Programmer;
import Model.Roles.Tester;
import Model.Tags.Assigned;
import Model.Tags.Closed;
import Model.Tags.New;
import Model.User.Admin;
import Model.User.Developer;
import Model.User.Issuer;
import Model.User.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Initializer implements IInitializer
{
	private UserService userService;
	private ProjectService projectService;
	private BugReportService bugReportService;

	public void init(){
        try{

		this.userService = new UserService();
		// refactor ProjectService class first
		this.projectService = new ProjectService();
		this.bugReportService = new BugReportService(projectService);

		// init users
		Admin sam = (Admin) userService.createAdmin("Frederick", "Sam","Curtis","curt");
		Issuer doc = (Issuer) userService.createIssuer("John","","Doctor","doc");
		Issuer charlie = (Issuer) userService.createIssuer("Charles","Arnold","Berg","charlie");
		Developer major = (Developer) userService.createDeveloper("Joseph","","Mays","major");
		Developer maria = (Developer) userService.createDeveloper("Maria","","Carney","maria");

		Lead leadMajor = new Lead(major);
		Project projectA = projectService.createProject("ProjectA", "ProjectA description", new TheDate(12,5,2016), 0.0, leadMajor);
		Programmer programmerMajor = new Programmer(major);
		projectA.addRole(programmerMajor);
		Tester testerMaria = new Tester(maria);
		projectA.addRole(testerMaria);
		
		SubSystem subSystemA1 = new SubSystem("SubSystemA1", "SubsystemA1 description");
		SubSystem subSystemA2 = new SubSystem("SubSystemA2","SubsystemA2 description");
		SubSystem subSystemA3 = new SubSystem("SubSystemA3","SubsystemA3 description");
		SubSystem subSystemA31 = new SubSystem("SubSystemA3.1","SubsystemA3.1 description");
		SubSystem subSystemA32 = new SubSystem("SubSystemA3.2","SubsystemA3.2 description");
		
		projectA.addSubSystem(subSystemA1);
		projectA.addSubSystem(subSystemA2);
		projectA.addSubSystem(subSystemA3);

		subSystemA3.addSubSystem(subSystemA31);
		subSystemA3.addSubSystem(subSystemA32);
		
		
		Lead leadMaria = new Lead(maria);
		Project projectB = projectService.createProject("ProjectB","ProjectB description", new TheDate(5,6,2016), 0.0, leadMaria);
		Programmer programmerMajorB = new Programmer(major);
		projectB.addRole(programmerMajorB);
		
		SubSystem subSystemB1 = new SubSystem("SubSystemB1", "SubsystemB1 description");
		SubSystem subSystemB2 = new SubSystem("SubSystemB2", "SubsystemB2 description");
		SubSystem subSystemB21 = new SubSystem("SubSystemB2.1", "SubsystemB2.1 description.");
		
		projectB.addSubSystem(subSystemB1);
		projectB.addSubSystem(subSystemB2);
		
		subSystemB2.addSubSystem(subSystemB21);
		
		
		bugReportService.createBugReport("The function parse_ewd returns unexpected results",
				"If the function parse_ewd is invoked while ...",
				subSystemB1,
                doc,
                new TheDate(3,1,2016),
                new Closed(),
                Collections.singletonList(maria));
		
		bugReportService.createBugReport("Crash while processing user input",
				"If incorrect user input is entered into the system ...", 
				subSystemA31,
                doc,
                new TheDate(15,1,2016),
				new Assigned(),
                Arrays.asList(major, maria));
		
		bugReportService.createBugReport("SubsystemA2 feezes",
				"If the function process_dfe is invoked with ...",
				subSystemA2,
                charlie,
                new TheDate(4,2,2016),
				new New(),
                new ArrayList<>());

		}
		catch(ModelException e)
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
