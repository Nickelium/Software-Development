package BugReportsPackageTest;

import CustomExceptions.ModelException;
import Model.BugReport.*;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.SubSystem;
import Model.Project.TheDate;
import Model.Roles.Lead;
import Model.Tags.TagTypes.Assigned;
import Model.Tags.TagTypes.Closed;
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
    protected Issuer issuer1;
    protected Issuer issuer2;
    protected BugReport bugReport1;
    protected BugReport bugReport2;
    protected BugReport bugReport3;
    protected Comment comment1;

    @Before
    public void initialization() throws ModelException {
        projectService = new ProjectService();
        bugReportService = new BugReportService(projectService);
        userService = new UserService();


        dev1 = userService.createDeveloper("Dev1", "", "", "dev1");
        dev2 = userService.createDeveloper("Dev2", "", "", "dev2");
        issuer1 = userService.createIssuer("Iss1", "", "", "iss1");
        issuer2 = userService.createIssuer("Iss2", "", "", "iss2");
        project1 = projectService.createProject("Test1", "Des Test1", TheDate.TheDateNow(), 0.0, new Lead(dev1));
        project2 = projectService.createProject("Test2", "Des Test2", TheDate.TheDateNow(), 0.0, new Lead(dev2));
        subSystem1A = projectService.createSubsystem("Sub1A", "Des Sub1A", project1);
        subSystem1B = projectService.createSubsystem("Sub1B", "Des Sub1B", project1);
        subSystem1A1 = projectService.createSubsystem("Sub1A1", "Des Sub1A1", subSystem1A);
        subSystem2 = projectService.createSubsystem("Sub2", "Des Sub2", project2);
        bugReport1 = bugReportService.createBugReport("Bug1", "Des Bug1", issuer1, subSystem1B);
        bugReport2 = bugReportService.createBugReport("Bug2", "Des Bug2", issuer2, subSystem1A1, TheDate.TheDateNow(), new Closed(), Collections.singletonList(dev2));
        bugReport3 = bugReportService.createBugReport("Bug3", "Des Bug3", issuer1, subSystem2, TheDate.TheDateNow(), new Assigned(), Arrays.asList(dev1, dev2));
        comment1 = bugReportService.createComment("Test Comment", issuer1, bugReport1);
    }
}
