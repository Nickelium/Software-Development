package BugReportsPackageTest;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.TagTypes.Assigned;
import Model.BugReport.TagTypes.Closed;
import Model.BugReport.TagTypes.Resolved;
import Model.BugReport.TagTypes.UnderReview;
import Model.Project.Project;
import Model.Project.SubSystem;
import Model.Project.TheDate;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by Tom on 10/03/16.
 */
public class TagAssignmentServiceTest extends AssignmentTestsInitialization {

    @Test
    public void assignTagTest_ValidDeveloperPermission() throws ReportErrorToUserException {
        Project project = projectService.createProject("Test project", "des", TheDate.TheDateNow(), 0.0, lead1);
        project.addRole(tester1);
        project.addRole(programmer2);

        SubSystem subSystem = projectService.createSubsystem("Subsys test", "des", project);
        BugReport bugreport = bugReportService.createBugReport("Bugreport1", "Des bugreport1", issuer1, BugReport.PUBLIC, subSystem1,
                TheDate.TheDateNow(), new Assigned(), Arrays.asList(dev3, dev6));

        tagAssignmentService.assignTag(dev3, bugreport, new UnderReview());
        assertEquals(UnderReview.class, bugreport.getTag().getClass());
    }

    @Test(expected = ReportErrorToUserException.class)
    public void assignTagTest_IllegalDeveloper() throws ReportErrorToUserException {
        Project project = projectService.createProject("Test project", "des", TheDate.TheDateNow(), 0.0, lead1);
        project.addRole(tester1);
        project.addRole(programmer2);

        SubSystem subSystem = projectService.createSubsystem("Subsys test", "des", project);
        BugReport bugreport = bugReportService.createBugReport("Bugreport1", "Des bugreport1", issuer1, BugReport.PUBLIC, subSystem1,
                TheDate.TheDateNow(), new Assigned(), Arrays.asList(dev3, dev6));

        tagAssignmentService.assignTag(dev7, bugreport, new UnderReview());
        assertEquals(UnderReview.class, bugreport.getTag().getClass());
    }

    @Test(expected = ReportErrorToUserException.class)
    public void assignTagTest_IllegalDeveloperPermission() throws ReportErrorToUserException {
        tagAssignmentService.assignTag(dev5, bugReport2, new UnderReview());
    }

    @Test
    public void assignTagTest_ValidCreatorToResolved() throws ReportErrorToUserException {
        Project project = projectService.createProject("Test project", "des", TheDate.TheDateNow(), 0.0, lead1);
        project.addRole(tester1);
        project.addRole(programmer2);

        SubSystem subSystem = projectService.createSubsystem("Subsys test", "des", project);
        BugReport bugreport = bugReportService.createBugReport("Bugreport1", "Des bugreport1", issuer1, BugReport.PUBLIC, subSystem1,
                TheDate.TheDateNow(), new Assigned(), Arrays.asList(dev3, dev6));

        tagAssignmentService.assignTag(dev6, bugreport, new UnderReview());
        tagAssignmentService.assignTag(issuer1, bugreport, new Resolved());
        assertEquals(Resolved.class, bugreport.getTag().getClass());
    }

    @Test(expected = ReportErrorToUserException.class)
    public void assignTagTest_InValidTagSequence() throws ReportErrorToUserException {
        tagAssignmentService.assignTag(issuer2, bugReport2, new Assigned());
    }

    @Test(expected = ReportErrorToUserException.class)
    public void assignTagTest_InValidIssuerToClosed() throws ReportErrorToUserException {
        tagAssignmentService.assignTag(issuer2, bugReport2, new Closed());
    }

    @Test
    public void assignTagTest_ValidLeadDeveloperResolvedToClosed() throws ReportErrorToUserException {
        tagAssignmentService.assignTag(dev1, bugReport2, new Closed());
        assertEquals(Closed.class, bugReport2.getTag().getClass());
    }

    @Test
    public void assignTagTest_ValidLeadDeveloperUnderReviewToClosed() throws ReportErrorToUserException {
        Project project = projectService.createProject("Test project", "des", TheDate.TheDateNow(), 0.0, lead1);
        project.addRole(tester1);
        project.addRole(programmer2);

        SubSystem subSystem = projectService.createSubsystem("Subsys test", "des", project);
        BugReport bugreport = bugReportService.createBugReport("Bugreport1", "Des bugreport1", issuer1, BugReport.PUBLIC, subSystem1,
                TheDate.TheDateNow(), new UnderReview(), Arrays.asList(dev3, dev6));

        tagAssignmentService.assignTag(dev1, bugreport, new Closed());
        assertEquals(Closed.class, bugreport.getTag().getClass());
    }
}
