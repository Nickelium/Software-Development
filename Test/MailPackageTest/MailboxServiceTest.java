package MailPackageTest;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.BugReport.TagAssignmentService;
import Model.BugReport.TagTypes.Assigned;
import Model.Mail.MailboxService;
import Model.Mail.Notification;
import Model.Mail.ObserverAspect;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.SubSystem;
import Model.Project.TheDate;
import Model.Roles.Lead;
import Model.User.Developer;
import Model.User.Issuer;
import Model.User.UserService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class MailboxServiceTest {
	private Issuer user;
	private Developer dev;
	private UserService userService;
	private BugReport bugReport;
	private Project p;
	private SubSystem s;
	private BugReportService bugReportService;
	private ProjectService projectService;
	private MailboxService mailboxService;
	private TagAssignmentService tagAssignmentService;

	@Before
	public void setup() throws ReportErrorToUserException {
		mailboxService = new MailboxService(bugReportService, userService);
		userService  = new UserService();
		user = userService.createIssuer("F", "M", "L", "U");
		dev = userService.createDeveloper("FF", "M", "L", "dev");
		projectService = new ProjectService();
		bugReportService = new BugReportService(projectService);
		p = projectService.createProject("P", "D", new TheDate("10/10/2018"), 10, new Lead(dev));
		s = projectService.createSubsystem("Sub", "des", p);
		bugReport = bugReportService.createBugReport("T", "D", user, s, BugReport.PUBLIC);
		tagAssignmentService = new TagAssignmentService(projectService);
	}
	
	@Test 
	public void registerComment_SUCCESS() throws ReportErrorToUserException
	{	
		mailboxService.registerComment(user, p);
		bugReportService.createComment("Comm", user, bugReport);
		List<ObserverAspect> registrations =mailboxService.getRegistrations(user);
		assertEquals(registrations.size(), 1);
		assertEquals(registrations.get(0).toString(), "Registration for creation of new comment in \n" + p );
	
		List<Notification> notifications = mailboxService.getNotifications(user, 1);
		assertEquals(notifications.size(), 1);
		assertEquals(notifications.get(0).toString(), "New comment in \n" + p );
	
	}
	
	@Test 
	public void registerTag_SUCCESS() throws ReportErrorToUserException
	{	
		mailboxService.registerTag(user, p);
		tagAssignmentService.assignTag(user, bugReport, new Assigned());
		List<ObserverAspect> registrations =mailboxService.getRegistrations(user);
		assertEquals(registrations.size(), 1);
		assertEquals(registrations.get(0).toString(), "Registration for change of tag in \n" + p );
	
		List<Notification> notifications = mailboxService.getNotifications(user, 1);
		assertEquals(notifications.size(), 1);
		assertEquals(notifications.get(0).toString(), "Tag changed in \n" + p );
	
	}

}
