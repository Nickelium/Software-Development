package HealthIndicatorTest;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.*;
import Model.BugReport.TagTypes.Resolved;
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
import org.junit.Before;

/**
 * Created by Tom on 7/05/16.
 */
public class HealthIndicatorTestInitialization {

    protected UserService userService;
    protected ProjectService projectService;
    protected BugReportService bugReportService;
    protected DeveloperAssignmentService developerAssignmentService;
    protected TagAssignmentService tagAssignmentService;

    protected Admin admin;
    protected Issuer iss1;
    protected Issuer iss2;
    protected Issuer iss3;
    protected Developer dev1;
    protected Developer dev2;
    protected Developer dev3;

    protected Lead lead;
    protected Programmer prog;
    protected Tester test;

    protected Project project1;
    protected Project project2;
    protected SubSystem subSystem1;
    protected SubSystem subSystem2;
    protected SubSystem subSystem3;
    protected SubSystem subSystem4;
    protected SubSystem subSystem5;

    protected BugReport bugReport1;
    protected BugReport bugReport2;
    protected BugReport bugReport3;
    protected BugReport bugReport4;
    protected BugReport bugReport5;

    @Before
    public void initialize() throws ReportErrorToUserException {
        servicesInitialization();
        usersInitialization();
        rolesInitialization();
        projectInitialization();
        projectAssignInitialization();
        subsystemInitialization();
        bugReportInitialization();
    }


    private void servicesInitialization() {
        this.userService = new UserService();
        this.projectService = new ProjectService();
        this.bugReportService = new BugReportService(this.projectService);
        this.developerAssignmentService = new DeveloperAssignmentService(this.projectService);
        this.tagAssignmentService = new TagAssignmentService(this.projectService);
    }

    private void usersInitialization() throws ReportErrorToUserException {
        this.admin = this.userService.createAdmin("admin", "", "", "admin");
        this.iss1 = this.userService.createIssuer("iss1", "", "", "iss1");
        this.iss2 = this.userService.createIssuer("iss2", "", "", "iss2");
        this.iss3 = this.userService.createIssuer("iss3", "", "", "iss3");
        this.dev1 = this.userService.createDeveloper("dev1", "", "", "dev1");
        this.dev2 = this.userService.createDeveloper("dev2", "", "", "dev2");
        this.dev3 = this.userService.createDeveloper("dev3", "", "", "dev3");
    }

    private void rolesInitialization() {
        this.lead = new Lead(this.dev1);
        this.prog = new Programmer(this.dev2);
        this.test = new Tester(this.dev3);
    }

    private void projectInitialization() throws ReportErrorToUserException {
        this.project1 = this.projectService.createProject("Project1", "p1", TheDate.TheDateNow(), 0.0, this.lead);
        this.project2 = this.projectService.createProject("Project2", "p2", TheDate.TheDateNow(), 0.0, this.lead);
    }

    private void projectAssignInitialization() throws ReportErrorToUserException {
        this.projectService.assignRole(project1, prog, lead.getDeveloper());
        this.projectService.assignRole(project1, test, lead.getDeveloper());

    }

    private void subsystemInitialization() throws ReportErrorToUserException {
        this.subSystem1 = this.projectService.createSubsystem("Subsystem1", "s1", this.project1);
        this.subSystem2 = this.projectService.createSubsystem("Subsystem2", "s2", this.project1);
        this.subSystem3 = this.projectService.createSubsystem("Subsystem3", "s3", this.subSystem1);
        this.subSystem4 = this.projectService.createSubsystem("Subsystem4", "s4", this.subSystem3);
        this.subSystem5 = this.projectService.createSubsystem("Subsystem5", "s5", this.project2);
    }

    private void bugReportInitialization() throws ReportErrorToUserException {
        this.bugReport1 = this.bugReportService.createBugReport("BugReport1", "b1", iss1, subSystem1, true, 7);
        this.bugReport2 = this.bugReportService.createBugReport("BugReport2", "b2", iss2, subSystem3, true, 3);
        this.bugReport3 = this.bugReportService.createBugReport("BugReport3", "b3", iss3, subSystem2, true, 6);
        this.bugReport4 = this.bugReportService.createBugReport("BugReport4", "b4", iss3, subSystem3, true, 9);
        this.bugReport5 = this.bugReportService.createBugReport("BugReport5", "b5", iss2, subSystem1, true, 2);
    }

    protected void changeBugReport1TagToResolved() throws ReportErrorToUserException {
        this.developerAssignmentService.assignDeveloperToBugReport(this.lead.getDeveloper(), prog.getDeveloper(), bugReport1);
        this.developerAssignmentService.assignDeveloperToBugReport(this.lead.getDeveloper(), test.getDeveloper(), bugReport1);
        this.bugReportService.createTest("Test1", test.getDeveloper(), bugReport1);
        Patch patch = this.bugReportService.createPatch("Patch1", prog.getDeveloper(), bugReport1);
        this.tagAssignmentService.assignTag(this.lead.getDeveloper(), bugReport1, new Resolved(patch));
    }

    protected void changeBugReport5TagToAssigned() throws ReportErrorToUserException {
        this.developerAssignmentService.assignDeveloperToBugReport(this.lead.getDeveloper(), prog.getDeveloper(), bugReport5);
        this.developerAssignmentService.assignDeveloperToBugReport(this.lead.getDeveloper(), test.getDeveloper(), bugReport5);
        System.out.println(bugReport5.getTag());
    }
}