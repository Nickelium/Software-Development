package BugReportsPackageTest;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.BugReport.DeveloperAssignmentService;
import Model.BugReport.TagAssignmentService;
import Model.BugReport.TagTypes.Resolved;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.SubSystem;
import Model.Project.TheDate;
import Model.Roles.Lead;
import Model.Roles.Programmer;
import Model.Roles.Tester;
import Model.User.Developer;
import Model.User.Issuer;
import Model.User.UserService;
import org.junit.Before;

import java.util.Arrays;

/**
 * Created by Tom on 10/03/16.
 */
public class AssignmentTestsInitialization {
    protected ProjectService projectService;
    protected UserService userService;
    protected BugReportService bugReportService;
    protected TagAssignmentService tagAssignmentService;
    protected DeveloperAssignmentService developerAssignmentService;
    protected Project project1;
    protected Project project2;
    protected SubSystem subSystem1;
    protected SubSystem subSystem2;
    protected SubSystem subSystem3;
    protected SubSystem subSystem4;
    protected BugReport bugReport1;
    protected BugReport bugReport2;
    protected BugReport bugReport3;
    protected Developer dev1;
    protected Developer dev2;
    protected Developer dev3;
    protected Developer dev4;
    protected Developer dev5;
    protected Developer dev6;
    protected Developer dev7;
    protected Issuer issuer1;
    protected Issuer issuer2;
    protected Lead lead1;
    protected Lead lead2;
    protected Tester tester1;
    protected Tester tester2;
    protected Programmer programmer1;
    protected Programmer programmer2;


    @Before
    public void initialization() throws ReportErrorToUserException {
        projectService = new ProjectService();
        userService = new UserService();
        bugReportService = new BugReportService(projectService);
        tagAssignmentService = new TagAssignmentService(projectService);
        developerAssignmentService = new DeveloperAssignmentService(projectService);
        dev1 = userService.createDeveloper("", "", "", "dev1");
        dev2 = userService.createDeveloper("", "", "", "dev2");
        dev3 = userService.createDeveloper("", "", "", "dev3");
        dev4 = userService.createDeveloper("", "", "", "dev4");
        dev5 = userService.createDeveloper("", "", "", "dev5");
        dev6 = userService.createDeveloper("", "", "", "dev6");
        dev7 = userService.createDeveloper("", "", "", "dev7");
        issuer1 = userService.createIssuer("", "", "", "iss1");
        issuer2 = userService.createIssuer("", "", "", "iss2");
        lead1 = new Lead(dev1);
        lead2 = new Lead(dev2);
        tester1 = new Tester(dev3);
        tester2 = new Tester(dev4);
        programmer1 = new Programmer(dev5);
        programmer2 = new Programmer(dev6);

        project1 = projectService.createProject("Project1", "Des Project1", TheDate.TheDateNow(), 0.0, lead1);
        subSystem1 = projectService.createSubsystem("Subsystem1", "Des Subsystem1", project1);
        subSystem2 = projectService.createSubsystem("Subsystem2", "Des Subsystem2", project1);
        subSystem3 = projectService.createSubsystem("Subsystem3", "Des Subsystem3", subSystem1);
        project1.addRole(tester1);
        project1.addRole(programmer1);
        project1.addRole(programmer2);

        project2 = projectService.createProject("Project2", "Des Project2", TheDate.TheDateNow(), 0.0, lead2);
        subSystem4 = projectService.createSubsystem("Subsystem4", "Des Subsystem4", project2);
        project2.addRole(tester2);
        project2.addRole(programmer2);

        bugReport1 = bugReportService.createBugReport("Bugreport1", "Des bugreport1", issuer1, subSystem1, BugReport.PUBLIC,
                TheDate.TheDateNow(), Arrays.asList(dev3, dev6));

        bugReport2 = bugReportService.createBugReport("Bugreport2", "Des bugreport2", issuer2, subSystem3, BugReport.PUBLIC,
                TheDate.TheDateNow(), Arrays.asList(dev4, dev5));

        bugReportService.createTest("Test", dev4, bugReport2);
        bugReportService.createPatch("Patch", dev5, bugReport2);
        tagAssignmentService.assignTag(dev1, bugReport2, new Resolved(0));

        bugReport3 = bugReportService.createBugReport("Bugreport3", "Des bugreport3", issuer1, subSystem4, BugReport.PUBLIC,
                TheDate.TheDateNow(), Arrays.asList(dev4, dev6));
    }
}
