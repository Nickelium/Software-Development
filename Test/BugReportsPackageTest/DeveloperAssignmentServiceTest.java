package BugReportsPackageTest;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.TagTypes.Assigned;
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
    @Test
    public void assignDeveloperToBugReportTest_ValidTester() throws ReportErrorToUserException {
        Project project = projectService.createProject("Test project", "des", TheDate.TheDateNow(), 0.0, lead1);
        project.addRole(tester1);
        project.addRole(programmer1);
        project.addRole(programmer2);

        SubSystem subSystem = projectService.createSubsystem("Subsys test", "des", project);
        BugReport bugreport = bugReportService.createBugReport("Bugreport1", "Des bugreport1", issuer1, subSystem1,
                TheDate.TheDateNow(), new Assigned(), Arrays.asList(dev3, dev6));


        developerAssignmentService.assignDeveloperToBugReport(tester1.getDeveloper(), dev5, bugreport);
        assertTrue(bugreport.getAssignees().contains(dev5));
    }

    @Test
    public void assignDeveloperToBugReportTest_ValidLead() throws ReportErrorToUserException {
        Project project = projectService.createProject("Test project", "des", TheDate.TheDateNow(), 0.0, lead1);
        project.addRole(tester1);
        project.addRole(programmer1);
        project.addRole(programmer2);

        SubSystem subSystem = projectService.createSubsystem("Subsys test", "des", project);
        BugReport bugreport = bugReportService.createBugReport("Bugreport1", "Des bugreport1", issuer1, subSystem1,
                TheDate.TheDateNow(), new Assigned(), Arrays.asList(dev3, dev6));


        developerAssignmentService.assignDeveloperToBugReport(lead1.getDeveloper(), dev5, bugreport);
        assertTrue(bugreport.getAssignees().contains(dev5));
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
