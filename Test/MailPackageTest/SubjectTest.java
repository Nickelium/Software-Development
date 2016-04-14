package MailPackageTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.BugReport.TagAssignmentService;
import Model.Mail.MailboxService;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.SubSystem;
import Model.Project.TheDate;
import Model.Roles.Lead;
import Model.User.Developer;
import Model.User.Issuer;
import Model.User.UserService;

public class SubjectTest {

	private Issuer user;
	private Developer dev;
	private UserService userService;
	private BugReport bugReport;
	private BugReport bugReport2;
	private Project p;
	private Project p2;
	private SubSystem s;
	private SubSystem s2;
	private BugReportService bugReportService;
	private ProjectService projectService;
	private MailboxService mailboxService;
	private TagAssignmentService tagAssignmentService;
	
	@Before
	public void setup() throws ReportErrorToUserException 
	{
		userService  = new UserService();
		projectService = new ProjectService();
		bugReportService = new BugReportService(projectService);
		mailboxService = new MailboxService(bugReportService, userService);
		tagAssignmentService = new TagAssignmentService(projectService);
		
		user = userService.createIssuer("F", "M", "L", "U");
		dev = userService.createDeveloper("FF", "M", "L", "dev");
		p = projectService.createProject("P", "D", new TheDate("10/10/2018"), 10, new Lead(dev));
		p2 = projectService.createProject("P", "D", new TheDate("10/10/2018"), 10, new Lead(dev));
		s = projectService.createSubsystem("Sub", "des", p);
		s2 = projectService.createSubsystem("Sub", "des", p2);
		bugReport = bugReportService.createBugReport("T", "D", user, s, BugReport.PUBLIC);
		bugReport2 = bugReportService.createBugReport("T", "D", user, s2, BugReport.PUBLIC);
	}
	
	@Test
	public void getObservers()
	{
		assertEquals(s.getObservers().size(),1);
		assertEquals(s.getObservers().get(0),p);
	}

}
