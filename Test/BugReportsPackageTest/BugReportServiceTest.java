package BugReportsPackageTest;

import CustomExceptions.ModelException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportID;
import Model.BugReport.BugReportService;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.SubSystem;
import Model.Project.TheDate;
import Model.Roles.Lead;
import Model.Tags.Assigned;
import Model.Tags.Closed;
import Model.User.Developer;
import Model.User.Issuer;
import Model.User.UserService;
import com.sun.tools.internal.ws.processor.model.Model;
import com.sun.tools.internal.ws.processor.modeler.ModelerException;
import com.sun.tools.javac.comp.Flow;
import junit.framework.Assert;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Tom on 9/03/16.
 */
public class BugReportServiceTest {
    private ProjectService projectService;
    private BugReportService bugReportService;
    private UserService userService;
    private Project project1;
    private Project project2;
    private SubSystem subSystem1A;
    private SubSystem subSystem1B;
    private SubSystem subSystem1A1;
    private SubSystem subSystem2;
    private Developer dev1;
    private Developer dev2;
    private Issuer issuer1;
    private Issuer issuer2;
    private BugReport bugReport1;
    private BugReport bugReport2;
    private BugReport bugReport3;

    @Before
    public void initialization() throws ModelException {
        projectService = new ProjectService();
        bugReportService = new BugReportService(projectService);
        userService = new UserService();

        dev1 = (Developer) userService.createDeveloper("Dev1", "", "", "dev1");
        dev2 = (Developer) userService.createDeveloper("Dev2", "","","dev2");
        issuer1 = (Issuer) userService.createIssuer("Iss1", "","","iss1");
        issuer2 = (Issuer) userService.createIssuer("Iss2", "","","iss2");
        project1 = projectService.createProject("Test1", "", TheDate.TheDateNow(), 0.0, new Lead(dev1));
        project2 = projectService.createProject("Test2", "", TheDate.TheDateNow(), 0.0, new Lead(dev2));
        subSystem1A = projectService.createSubsystem("Sub1A", "Des Sub1A", project1);
        subSystem1B = projectService.createSubsystem("Sub1B", "Des Sub1B", project1);
        subSystem1A1 = projectService.createSubsystem("Sub1A1", "Des Sub1A1", subSystem1A);
        subSystem2 = projectService.createSubsystem("Sub2", "Des Sub2", project2);
        bugReport1 = bugReportService.createBugReport("Bug1", "Des Bug1", issuer1, subSystem1B);
        bugReport2 = bugReportService.createBugReport("Bug2", "Des Bug2", issuer2, subSystem1A1, TheDate.TheDateNow(), new Closed(), Collections.singletonList(dev2));
        bugReport3 = bugReportService.createBugReport("Bug3", "Des Bug3", issuer1, subSystem2, TheDate.TheDateNow(), new Assigned(), Arrays.asList(dev1, dev2));
    }

    @Test
    public void constructorTest_Valid(){
        BugReportService brs = new BugReportService(projectService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorTest_InValid(){
        BugReportService brs = new BugReportService(null);
    }

    @Test
    public void getAllBugReportsTest(){
        assertEquals(3, bugReportService.getAllBugReports().size());
    }

    @Test
    public void getBugReportTest_Valid() throws ModelException{
        BugReport report = bugReportService.getBugReport(bugReport2.getId());
        assertEquals(bugReport2, report);
    }

    @Test(expected = ModelException.class)
    public void getBugReportTest_InValid() throws ModelException{
        bugReportService.getBugReport(new BugReportID());
    }

    @Test
    public void getBugReportsForProjectTest(){
        assertEquals(2, bugReportService.getBugReportsForProject(project1).size());
    }

    @Test
    public void getBugReportAssignedToUserTest(){
        assertEquals(2, bugReportService.getBugReportsAssignedToUser(dev2).size());
        assertEquals(1, bugReportService.getBugReportsAssignedToUser(dev1).size());
    }

    @Test
    public void getBugReportsFiledByUserTest(){
        assertEquals(2, bugReportService.getBugReportsFiledByUser(issuer1).size());
        assertEquals(1, bugReportService.getBugReportsFiledByUser(issuer2).size());
    }

    @Test
    public void getBugReportsWithTitleContainingTest_Valid() throws ModelException{
        assertEquals(3, bugReportService.getBugReportsWithTitleContaining("Bug").size());
        assertEquals(1, bugReportService.getBugReportsWithTitleContaining("Bug1").size());
    }

    @Test(expected = ModelException.class)
    public void getBugReportswithTitleContainingTest_Invalid_EmptyString() throws ModelException{
        bugReportService.getBugReportsWithTitleContaining("");
    }

    @Test(expected = ModelException.class)
    public void getBugReportswithTitleContainingTest_Invalid_WhiteSpace() throws ModelException{
        bugReportService.getBugReportsWithTitleContaining(" ");
    }

    @Test
    public void getBugReportsWithDescriptionContainingTest_Valid() throws ModelException{
        assertEquals(3, bugReportService.getBugReportsWithDescriptionContaining("Bug").size());
        assertEquals(1, bugReportService.getBugReportsWithDescriptionContaining("Des Bug1").size());
    }

    @Test(expected = ModelException.class)
    public void getBugReportswithDescriptionContainingTest_Invalid_EmptyString() throws ModelException{
        bugReportService.getBugReportsWithDescriptionContaining("");
    }

    @Test(expected = ModelException.class)
    public void getBugReportswithDescriptionContainingTest_Invalid_WhiteSpace() throws ModelException{
        bugReportService.getBugReportsWithDescriptionContaining(" ");
    }

    @Test
    public void isValidTitleString_Test(){
        assertEquals(false, bugReportService.isValidTitleString(null));
        assertEquals(false, bugReportService.isValidTitleString(""));
        assertEquals(false, bugReportService.isValidTitleString(" "));
        assertEquals(true, bugReportService.isValidTitleString("Test"));
    }

    @Test
    public void isValidDescriptionString_Test(){
        assertEquals(false, bugReportService.isValidDescription(null));
        assertEquals(false, bugReportService.isValidDescription(""));
        assertEquals(false, bugReportService.isValidDescription(" "));
        assertEquals(true, bugReportService.isValidTitleString("Test"));
    }
}
