package BugReportsPackageTest;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.*;
import Model.BugReport.TagTypes.Closed;
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
import java.util.Collections;

/**
 * Created by Tom on 9/03/16.
 */
public class BugReportInitializaton {
    protected ProjectService projectService;
    protected BugReportService bugReportService;
    protected UserService userService;
    protected TagAssignmentService tagAssignmentService;
    protected DeveloperAssignmentService developerAssignmentService;
    protected Project project1;
    protected Project project2;
    protected SubSystem subSystem1A;
    protected SubSystem subSystem1B;
    protected SubSystem subSystem1A1;
    protected SubSystem subSystem2;
    protected Developer dev1;
    protected Developer dev2;
    protected Developer dev3;
    protected Developer dev4;
    protected Issuer issuer1;
    protected Issuer issuer2;
    protected BugReport bugReport1;
    protected BugReport bugReport2;
    protected BugReport bugReport3;
    protected Comment comment1;

    @Before
    public void initialization() throws ReportErrorToUserException {
        projectService = new ProjectService();
        bugReportService = new BugReportService(projectService);
        userService = new UserService();
        this.tagAssignmentService = new TagAssignmentService(projectService);
        developerAssignmentService = new DeveloperAssignmentService(projectService);

        dev1 = userService.createDeveloper("Dev1", "", "", "dev1");
        dev2 = userService.createDeveloper("Dev2", "", "", "dev2");
        issuer1 = userService.createIssuer("Iss1", "", "", "iss1");
        issuer2 = userService.createIssuer("Iss2", "", "", "iss2");

        dev3 = userService.createDeveloper("Dev3", "", "", "dev3");
        dev4 = userService.createDeveloper("Dev4", "", "", "dev4");

        project1 = projectService.createProject("Test1", "Des Test1", TheDate.TheDateNow(), 0.0, new Lead(dev1));
        project2 = projectService.createProject("Test2", "Des Test2", TheDate.TheDateNow(), 0.0, new Lead(dev2));

        projectService.assignRole(project1, new Tester(dev3), dev1);
        projectService.assignRole(project1, new Programmer(dev4), dev1);

        subSystem1A = projectService.createSubsystem("Sub1A", "Des Sub1A", project1);
        subSystem1B = projectService.createSubsystem("Sub1B", "Des Sub1B", project1);
        subSystem1A1 = projectService.createSubsystem("Sub1A1", "Des Sub1A1", subSystem1A);
        subSystem2 = projectService.createSubsystem("Sub2", "Des Sub2", project2);

        bugReport1 = bugReportService.createBugReport("Bug1", "Des Bug1", issuer1, subSystem1B, BugReport.PRIVATE);

        bugReport2 = bugReportService.createBugReport("Bug2", "Des Bug2", issuer2, subSystem1A1, BugReport.PUBLIC, TheDate.TheDateNow(),
                Collections.singletonList(dev2));
        bugReportService.createTest("Test", dev3, bugReport2);
        Patch patch = bugReportService.createPatch("Patch", dev4, bugReport2);
        tagAssignmentService.assignTag(dev1, bugReport2, new Resolved(patch));
        tagAssignmentService.assignTag(issuer2, bugReport2, new Closed(1));

        bugReport3 = bugReportService.createBugReport("Bug3", "Des Bug3", issuer1, subSystem2, BugReport.PUBLIC, TheDate.TheDateNow(), Arrays.asList(dev1, dev2));
        comment1 = bugReportService.createComment("Test Comment", issuer1, bugReport1);
    }
}
