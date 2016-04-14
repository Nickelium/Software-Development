package BugReportsPackageTest;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.DeveloperAssignmentService;
import Model.Project.Project;
import Model.Project.SubSystem;
import Model.Project.TheDate;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Created by Tom on 10/03/16.
 */
public class DeveloperAssignmentServiceTest extends AssignmentTestsInitialization {

    private Project extraProject1;
    private SubSystem extraSubsystem1;
    private BugReport extraBugReport1;

    public void reinitializeExtra() throws ReportErrorToUserException {
        extraProject1 = projectService.createProject("Test project", "des", TheDate.TheDateNow(), 0.0, lead1);
        projectService.assignRole(extraProject1, tester1, lead1.getDeveloper());
        projectService.assignRole(extraProject1, programmer1, lead1.getDeveloper());
        projectService.assignRole(extraProject1, programmer2, lead1.getDeveloper());

        extraSubsystem1 = projectService.createSubsystem("Subsys test", "des", extraProject1);
        extraBugReport1 = bugReportService.createBugReport("Bugreport1", "Des bugreport1", issuer1, subSystem1, BugReport.PUBLIC,
                TheDate.TheDateNow(), Arrays.asList(dev3, dev6));
    }

    @Test (expected = IllegalArgumentException.class)
    public void constructor_FAIL()
    {
    	DeveloperAssignmentService d = new DeveloperAssignmentService(null);
    }

    @Test
    public void assignDeveloperToBugReportTest_ValidTester() throws ReportErrorToUserException {
        this.reinitializeExtra();
        developerAssignmentService.assignDeveloperToBugReport(tester1.getDeveloper(), dev5, extraBugReport1);
        assertTrue(extraBugReport1.getAssignees().contains(dev5));
    }

    @Test
    public void assignDeveloperToBugReportTest_ValidLead() throws ReportErrorToUserException {
        this.reinitializeExtra();
        developerAssignmentService.assignDeveloperToBugReport(lead1.getDeveloper(), dev5, extraBugReport1);
        assertTrue(extraBugReport1.getAssignees().contains(dev5));
    }

    @Test(expected = ReportErrorToUserException.class)
    public void assignDeveloperToBugReportTest_InValid_Programmer() throws ReportErrorToUserException {
        developerAssignmentService.assignDeveloperToBugReport(programmer1.getDeveloper(), dev5, bugReport1);
    }

    @Test(expected = ReportErrorToUserException.class)
    public void assignDeveloperToBugReportTest_Invalid_NewAssignee() throws ReportErrorToUserException {
        developerAssignmentService.assignDeveloperToBugReport(lead1.getDeveloper(), dev7, bugReport1);
    }
}
