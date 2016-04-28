package Controller;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.*;
import Model.BugReport.TagTypes.Closed;
import Model.BugReport.TagTypes.Resolved;
import Model.Mail.MailboxService;
import Model.Memento.Caretaker;
import Model.Milestone.Milestone;
import Model.Milestone.TargetMilestone;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.SubSystem;
import Model.Project.TheDate;
import Model.Roles.Lead;
import Model.Roles.Programmer;
import Model.Roles.Tester;
import Model.User.Admin;
import Model.User.Developer;
import Model.User.Issuer;
import Model.User.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Initializer implements IInitializer {
    private UserService userService;
    private ProjectService projectService;
    private BugReportService bugReportService;
    private DeveloperAssignmentService developerAssignmentService;
    private TagAssignmentService tagAssignmentService;
    private MailboxService mailboxService;
    private Caretaker caretaker;

    public Initializer() {
        init();
    }

    private void init() {
        try {

            this.userService = new UserService();
            // refactor ProjectService class first
            this.projectService = new ProjectService();
            this.bugReportService = new BugReportService(projectService);
            this.developerAssignmentService = new DeveloperAssignmentService(projectService);
            this.tagAssignmentService = new TagAssignmentService(projectService);
            this.mailboxService = new MailboxService(bugReportService, userService);
            this.caretaker = new Caretaker(projectService, mailboxService);

            // init users
            Admin sam = userService.createAdmin("Frederick", "Sam", "Curtis", "curt");
            Issuer doc = userService.createIssuer("John", "", "Doctor", "doc");
            Issuer charlie = userService.createIssuer("Charles", "Arnold", "Berg", "charlie");
            Developer major = userService.createDeveloper("Joseph", "", "Mays", "major");
            Developer maria = userService.createDeveloper("Maria", "", "Carney", "maria");

            // project A
            Lead leadMajor = new Lead(major);
            Programmer programmerMaria = new Programmer(maria);
            Tester testerMaria = new Tester(maria);

            Project projectA = projectService.createProject("ProjectA", "ProjectA description", new TheDate(12, 5, 2016), 0.0, leadMajor);

            projectService.assignRole(projectA, programmerMaria, leadMajor.getDeveloper());
            projectService.assignRole(projectA, testerMaria, leadMajor.getDeveloper());

            SubSystem subSystemA1 = projectService.createSubsystem("SubSystemA1", "SubsystemA1 description", projectA);
            projectService.setNewSubSystemMilestone(subSystemA1, new Milestone("M2.5.1"));
            SubSystem subSystemA2 = projectService.createSubsystem("SubSystemA2", "SubsystemA2 description", projectA);
            projectService.setNewSubSystemMilestone(subSystemA2, new Milestone("M2.5"));
            SubSystem subSystemA3 = projectService.createSubsystem("SubSystemA3", "SubsystemA3 description", projectA);
            projectService.setNewSubSystemMilestone(subSystemA3, new Milestone("M2.8.5"));
            SubSystem subSystemA31 = projectService.createSubsystem("SubSystemA3.1", "SubsystemA3.1 description", subSystemA3);
            projectService.setNewSubSystemMilestone(subSystemA31, new Milestone("M2.8.5.3"));
            SubSystem subSystemA32 = projectService.createSubsystem("SubSystemA3.2", "SubsystemA3.2 description", subSystemA3);
            projectService.setNewSubSystemMilestone(subSystemA32, new Milestone("M2.9"));

            projectService.setNewProjectMilestone(projectA, new Milestone("M2.5"));

            // project B
            Lead leadMaria = new Lead(maria);
            Programmer programmerMajorB = new Programmer(major);

            // moeten we toevoegen omdat hun opgave bs is
            Tester testerMajorB = new Tester(major);

            Project projectB = projectService.createProject("ProjectB", "ProjectB description", new TheDate(5, 6, 2016), 0.0, leadMaria);

            projectService.assignRole(projectB, programmerMajorB, leadMaria.getDeveloper());
            projectService.assignRole(projectB, testerMajorB, leadMaria.getDeveloper());

            SubSystem subSystemB1 = projectService.createSubsystem("SubSystemB1", "SubsystemB1 description", projectB);
            SubSystem subSystemB2 = projectService.createSubsystem("SubSystemB2", "SubsystemB2 description", projectB);
            projectService.setNewSubSystemMilestone(subSystemB2, new Milestone("M1.2"));
            SubSystem subSystemB21 = projectService.createSubsystem("SubSystemB2.1", "SubsystemB2.1 description.", subSystemB2);
            projectService.setNewSubSystemMilestone(subSystemB21, new Milestone("M1.2"));

            projectService.setNewProjectMilestone(projectB, new Milestone("M1.2"));

            // Bug report 1
            BugReport bugreport1 = bugReportService.createBugReport("The function parse_ewd returns unexpected results",
                    "If the function parse_ewd is invoked while ...",
                    doc,
                    subSystemB1,
                    BugReport.PUBLIC,
                    new TheDate(3, 1, 2016),
                    Collections.singletonList(maria)
            );
            bugReportService.setTargetMilestone(bugreport1, new TargetMilestone("M1.1"));
            bugReportService.createTest("bool test_invalid_args1(){...}", major, bugreport1);
            Patch patch = bugReportService.createPatch("e3109fcc9...", major, bugreport1);
            tagAssignmentService.assignTag(maria, bugreport1, new Resolved(patch));
            tagAssignmentService.assignTag(doc, bugreport1, new Closed(1));

            projectService.setNewSubSystemMilestone(subSystemB1, new Milestone("M1.3"));

            // Bug report 2
            BugReport bugreport2 = bugReportService.createBugReport("Crash while processing user input",
                    "If incorrect user input is entered into the system ...",
                    doc,
                    subSystemA31,
                    BugReport.PUBLIC,
                    new TheDate(15, 1, 2016),
                    Arrays.asList(major, maria)
            );
            bugReportService.setErrorMessage(bugreport2, "Internal Error 45: The...");

            // Bug report 3
            BugReport bugreport3 = bugReportService.createBugReport("SubsystemA2 freezes",
                    "If the function process_dfe is invoked with ...",
                    charlie,
                    subSystemA2,
                    BugReport.PRIVATE,
                    new TheDate(4, 2, 2016),
                    new ArrayList<>()
            );
            bugReportService.setProcedureBug(bugreport3, "Launch with command line invocation:...");
            bugReportService.setStackTrace(bugreport3, "Exception in thread \"main\" java.lang...");

        } catch (ReportErrorToUserException e) {
            //invalid input
            e.printStackTrace();
            System.exit(1);
        }

    }

    public UserService getUserService() {
        return this.userService;
    }

    public ProjectService getProjectService() {
        return this.projectService;
    }

    public BugReportService getBugReportService() {
        return this.bugReportService;
    }

    public DeveloperAssignmentService getDeveloperAssignmentService() {
        return this.developerAssignmentService;
    }

    public TagAssignmentService getTagAssignmentService() {
        return this.tagAssignmentService;
    }

	public MailboxService getMailboxService() {
		return this.mailboxService;
	}
	
	public Caretaker getCaretaker()
	{
		return caretaker;
	}


}
