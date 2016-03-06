package BugReportsPackageTest;

import Model.BugReport.BugReport;
import Model.BugReport.BugReportID;
import Model.Project.SubSystem;
import Model.Project.TheDate;
import Model.Tags.New;
import Model.User.Issuer;
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

    @Before
    public void initialization() {
        this.subsys = new SubSystem("Test", "Test subsystem");
        this.issuer = new Issuer("Test", "T", "Testing", "user1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_IllegalTitle() {
        new BugReport(null, "description", this.subsys, this.issuer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_IllegalDescription() {
        new BugReport("Test", null, this.subsys, this.issuer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_IllegalSubsystem() {
        new BugReport("Test", "description", null, this.issuer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_IllegalIsser() {
        new BugReport("Test", "description", this.subsys, null);
    }

    @Test()
    public void getId() {
        BugReport bugReport = new BugReport("title", "description", this.subsys, this.issuer);
        assertEquals(bugReport.getId().getClass(), BugReportID.class);
    }

    @Test()
    public void getTitle() {
        BugReport bugReport = new BugReport("title", "description", this.subsys, this.issuer);
        assertEquals(bugReport.getTitle(), "title");
    }

    @Test()
    public void getDescription() {
        BugReport bugReport = new BugReport("title", "description", this.subsys, this.issuer);
        assertEquals(bugReport.getDescription(), "description");
    }

    @Test()
    public void getSubsystem() {
        BugReport bugReport = new BugReport("title", "description", this.subsys, this.issuer);
        assertEquals(bugReport.getSubsystem(), this.subsys);
    }

    @Test()
    public void getCreationDate() {
        BugReport bugReport = new BugReport("title", "description", this.subsys, this.issuer);
        assertEquals(bugReport.getCreationDate(), TheDate.TheDateNow());
    }

    @Test()
    public void getCreator() {
        BugReport bugReport = new BugReport("title", "description", this.subsys, this.issuer);
        assertEquals(bugReport.getCreator(), this.issuer);
    }

    @Test()
    public void getTag_AfterCreation() {
        BugReport bugReport = new BugReport("title", "description", this.subsys, this.issuer);
        assertEquals(bugReport.getTag().getClass(), New.class);
    }

    @Test()
    public void getAssignees_NotNull() {
        BugReport bugReport = new BugReport("title", "description", this.subsys, this.issuer);
        assertNotNull(bugReport.getAssignees());
    }

    @Test()
    public void getAssignees_Empty() {
        BugReport bugReport = new BugReport("title", "description", this.subsys, this.issuer);
        assertEquals(bugReport.getAssignees().size(), 0);
    }

    @Test()
    public void getComments_NotNull() {
        BugReport bugReport = new BugReport("title", "description", this.subsys, this.issuer);
        assertNotNull(bugReport.getComments());
    }

    @Test
    public void getComments_Empty() {
        BugReport bugReport = new BugReport("title", "description", this.subsys, this.issuer);
        assertEquals(bugReport.getComments().size(), 0);
    }

    @Test()
    public void getDependencies_NotNull() {
        BugReport bugReport = new BugReport("title", "description", this.subsys, this.issuer);
        assertNotNull(bugReport.getDependencies());
    }

    @Test()
    public void getDependencies_Empty() {
        BugReport bugReport = new BugReport("title", "description", this.subsys, this.issuer);
        assertEquals(bugReport.getDependencies().size(), 0);
    }
}

