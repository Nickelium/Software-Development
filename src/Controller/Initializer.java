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
		
		SubSystem subSystemA1 = projectService.createSubsystem("SubSystemA1", "SubsystemA1 description", projectA);
		SubSystem subSystemA2 = projectService.createSubsystem("SubSystemA2","SubsystemA2 description", projectA);
		SubSystem subSystemA3 = projectService.createSubsystem("SubSystemA3","SubsystemA3 description", projectA);
		SubSystem subSystemA31 = projectService.createSubsystem("SubSystemA3.1","SubsystemA3.1 description", subSystemA3);
		SubSystem subSystemA32 = projectService.createSubsystem("SubSystemA3.2","SubsystemA3.2 description", subSystemA3);
		
		
		Lead leadMaria = new Lead(maria);
		Project projectB = projectService.createProject("ProjectB","ProjectB description", new TheDate(5,6,2016), 0.0, leadMaria);
		Programmer programmerMajorB = new Programmer(major);
		projectB.addRole(programmerMajorB);
		
		SubSystem subSystemB1 = projectService.createSubsystem("SubSystemB1", "SubsystemB1 description", projectB);
		SubSystem subSystemB2 = projectService.createSubsystem("SubSystemB2", "SubsystemB2 description", projectB);
		SubSystem subSystemB21 = projectService.createSubsystem("SubSystemB2.1", "SubsystemB2.1 description.", subSystemB2);
		
		
		bugReportService.createBugReport("The function parse_ewd returns unexpected results",
				"If the function parse_ewd is invoked while ...",
				doc,
				subSystemB1,
                new TheDate(3,1,2016),
                new Closed(),
                Collections.singletonList(maria));
		
		bugReportService.createBugReport("Crash while processing user input",
				"If incorrect user input is entered into the system ...",
				doc,
				subSystemA31,
                new TheDate(15,1,2016),
				new Assigned(),
                Arrays.asList(major, maria));
		
		bugReportService.createBugReport("SubsystemA2 feezes",
				"If the function process_dfe is invoked with ...",
				charlie,
				subSystemA2,
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
