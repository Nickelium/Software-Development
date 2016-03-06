package BugReportsPackageTest;

import CustomExceptions.ModelException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportID;
import Model.BugReport.BugReportService;
import Model.Project.ProjectService;
import Model.Project.SubSystem;
import Model.Project.TheDate;
import Model.Tags.New;
import Model.User.Issuer;
import Model.User.UserService;
import com.sun.tools.internal.ws.processor.model.Model;
import com.sun.tools.internal.xjc.reader.RawTypeSet;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by Tom on 28/02/16.
 */
public class BugReportTest {
    private SubSystem subsys;
    private Issuer issuer;
    private BugReportService bugReportService;
    private ProjectService projectService;
    private UserService userService;

    @Before
    public void initialization() throws ModelException{
        projectService = new ProjectService();
        userService = new UserService();
        bugReportService = new BugReportService(projectService);
        this.subsys = new SubSystem("Test", "Test subsystem");
        this.issuer = (Issuer) userService.createIssuer("Test", "T", "Testing", "user1");
    }

    @Test(expected = ModelException.class)
    public void constructor_IllegalTitle() throws ModelException {
        bugReportService.createBugReport(null, "description", this.issuer, this.subsys);
    }

    @Test(expected = ModelException.class)
    public void constructor_IllegalDescription() throws ModelException {
        bugReportService.createBugReport("Test", null, this.issuer, this.subsys);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_IllegalSubsystem() throws ModelException {
        bugReportService.createBugReport("Test", "description", null, this.subsys);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_IllegalIsser() throws ModelException {
        bugReportService.createBugReport("Test", "description", this.issuer, null);
    }

    @Test()
    public void getId() throws ModelException {
        BugReport bugReport = bugReportService.createBugReport("title", "description", this.issuer, this.subsys);
        assertEquals(bugReport.getId().getClass(), BugReportID.class);
    }

    @Test()
    public void getTitle() throws ModelException {
        BugReport bugReport = bugReportService.createBugReport("title", "description", this.issuer, this.subsys);
        assertEquals(bugReport.getTitle(), "title");
    }

    @Test()
    public void getDescription() throws ModelException {
        BugReport bugReport = bugReportService.createBugReport("title", "description", this.issuer, this.subsys);
        assertEquals(bugReport.getDescription(), "description");
    }

    @Test()
    public void getCreationDate() throws ModelException {
        BugReport bugReport = bugReportService.createBugReport("title", "description", this.issuer, this.subsys);
        assertEquals(bugReport.getCreationDate(), TheDate.TheDateNow());
    }

    @Test()
    public void getCreator() throws ModelException {
        BugReport bugReport = bugReportService.createBugReport("title", "description", this.issuer, this.subsys);
        assertEquals(bugReport.getCreator(), this.issuer);
    }

    @Test()
    public void getTag_AfterCreation() throws ModelException{
        BugReport bugReport = bugReportService.createBugReport("title", "description", this.issuer, this.subsys);
        assertEquals(bugReport.getTag().getClass(), New.class);
    }

    @Test()
    public void getAssignees_NotNull() throws ModelException {
        BugReport bugReport = bugReportService.createBugReport("title", "description", this.issuer, this.subsys);
        assertNotNull(bugReport.getAssignees());
    }

    @Test()
    public void getAssignees_Empty() throws ModelException{
        BugReport bugReport = bugReportService.createBugReport("title", "description", this.issuer, this.subsys);
        assertEquals(bugReport.getAssignees().size(), 0);
    }

    @Test()
    public void getComments_NotNull() throws ModelException{
        BugReport bugReport = bugReportService.createBugReport("title", "description", this.issuer, this.subsys);
        assertNotNull(bugReport.getComments());
    }

    @Test
    public void getComments_Empty() throws ModelException{
        BugReport bugReport = bugReportService.createBugReport("title", "description", this.issuer, this.subsys);
        assertEquals(bugReport.getComments().size(), 0);
    }

    @Test()
    public void getDependencies_NotNull() throws ModelException{
        BugReport bugReport = bugReportService.createBugReport("title", "description", this.issuer, this.subsys);
        assertNotNull(bugReport.getDependencies());
    }

    @Test()
    public void getDependencies_Empty() throws ModelException{
        BugReport bugReport = bugReportService.createBugReport("title", "description", this.issuer, this.subsys);
        assertEquals(bugReport.getDependencies().size(), 0);
    }
}

