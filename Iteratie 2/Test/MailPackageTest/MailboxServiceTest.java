package MailPackageTest;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.BugReport.TagAssignmentService;
import Model.BugReport.TagTypes.Assigned;
import Model.BugReport.TagTypes.NotABug;
import Model.Mail.Mailbox;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MailboxServiceTest {
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
	public void setup() throws ReportErrorToUserException {
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
	public void registerCreationBugReport_SUCCESS() throws ReportErrorToUserException, IndexOutOfBoundsException
	{	
		mailboxService.registerCreationBugReport(dev, p);
		BugReport bugReportNew = bugReportService.createBugReport("T", "D", user, s, BugReport.PUBLIC);
		List<ObserverAspect> registrations = mailboxService.getRegistrations(dev);
		assertEquals(registrations.size(), 1);
	
		List<Notification> notifications = mailboxService.getNotifications(dev, 1);
		assertEquals(notifications.size(), 1);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void registerCreationBugReport_OTHERBRANCH() throws ReportErrorToUserException, IndexOutOfBoundsException
	{	
		mailboxService.registerCreationBugReport(dev, p);
		bugReportService.createComment("Comm", dev, bugReport);
		List<ObserverAspect> registrations = mailboxService.getRegistrations(dev);
		assertEquals(registrations.size(), 1);
	
		List<Notification> notifications = mailboxService.getNotifications(dev, 1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void registerCreationBugReport_FAILUSER() throws ReportErrorToUserException, IndexOutOfBoundsException
	{	
		mailboxService.registerCreationBugReport(null, p);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void registerCreationBugReport_FAILSUBJECT() throws ReportErrorToUserException, IndexOutOfBoundsException
	{	
		mailboxService.registerCreationBugReport(dev, null);
	}
	
	@Test (expected = IndexOutOfBoundsException.class) 
	public void registerCreationBugReport_NOUPDATE() throws ReportErrorToUserException, IndexOutOfBoundsException
	{	
		mailboxService.registerCreationBugReport(dev, p);
		BugReport bugReportNew = bugReportService.createBugReport("T", "D", user, s2, BugReport.PUBLIC);
		List<ObserverAspect> registrations = mailboxService.getRegistrations(dev);
		assertEquals(registrations.size(), 1);
	
		List<Notification> notifications = mailboxService.getNotifications(dev, 1);
	}
	
	
	
	@Test 
	public void registerComment_SUCCESS() throws ReportErrorToUserException, IndexOutOfBoundsException
	{	
		mailboxService.registerComment(dev, p);
		bugReportService.createComment("Comm", dev, bugReport);
		List<ObserverAspect> registrations = mailboxService.getRegistrations(dev);
		assertEquals(registrations.size(), 1);
		//assertEquals(registrations.get(0).toString(), "Registration for creation of new comment in \n" + p );
	
		List<Notification> notifications = mailboxService.getNotifications(dev, 1);
		assertEquals(notifications.size(), 1);
		//assertEquals(notifications.get(0).toString(), "New comment" + "\n"  + bugReport  + "\n in \n" + p +"\n");
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void registerComment_OTHERBRANCH() throws ReportErrorToUserException, IndexOutOfBoundsException
	{	
		mailboxService.registerComment(dev, p);
		BugReport bugReportNew = bugReportService.createBugReport("T", "D", user, s2, BugReport.PUBLIC);		
		List<ObserverAspect> registrations = mailboxService.getRegistrations(dev);
		assertEquals(registrations.size(), 1);
	
		List<Notification> notifications = mailboxService.getNotifications(dev, 1);
	
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void registerComment_FAILUSER() throws ReportErrorToUserException, IndexOutOfBoundsException
	{	
		mailboxService.registerComment(null, p);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void registerComment_FAILSUBJECT() throws ReportErrorToUserException, IndexOutOfBoundsException
	{	
		mailboxService.registerComment(dev, null);
	}
	
	@Test (expected = IndexOutOfBoundsException.class) 
	public void registerComment_NOUPDATE() throws ReportErrorToUserException, IndexOutOfBoundsException
	{	
		mailboxService.registerCreationBugReport(dev, p);
		bugReportService.createComment("Comm", dev, bugReport2);
		List<ObserverAspect> registrations = mailboxService.getRegistrations(dev);
		assertEquals(registrations.size(), 1);
	
		List<Notification> notifications = mailboxService.getNotifications(dev, 1);

	}
	
	@Test 
	public void registerTag_SUCCESS() throws ReportErrorToUserException, IndexOutOfBoundsException
	{	
		mailboxService.registerTag(dev, p);
		tagAssignmentService.assignTag(dev, bugReport, new NotABug());
		List<ObserverAspect> registrations = mailboxService.getRegistrations(dev);
		assertEquals(registrations.size(), 1);
		assertEquals(registrations.get(0).toString(), "Registration for change of tag in \n" + p );
	
		List<Notification> notifications = mailboxService.getNotifications(dev, 1);
		assertEquals(notifications.size(), 1);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void registerTag_OTHERBRANCH() throws ReportErrorToUserException, IndexOutOfBoundsException
	{	
		mailboxService.registerTag(dev, p);
		bugReportService.createComment("Comm", dev, bugReport);
		List<ObserverAspect> registrations = mailboxService.getRegistrations(dev);
		assertEquals(registrations.size(), 1);
	
		List<Notification> notifications = mailboxService.getNotifications(dev, 1);
	
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void registerTag_FAILUSER() throws ReportErrorToUserException, IndexOutOfBoundsException
	{	
		mailboxService.registerTag(null, p);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void registerTag_FAILSUBJECT() throws ReportErrorToUserException, IndexOutOfBoundsException
	{	
		mailboxService.registerTag(dev, null);
	}
	
	@Test (expected = IndexOutOfBoundsException.class) 
	public void registerTag_NOUPDATE() throws ReportErrorToUserException, IndexOutOfBoundsException
	{	
		mailboxService.registerTag(dev, s2);
		tagAssignmentService.assignTag(dev, bugReport, new NotABug());
		List<ObserverAspect> registrations = mailboxService.getRegistrations(dev);
		assertEquals(registrations.size(), 1);

	
		List<Notification> notifications = mailboxService.getNotifications(dev, 1);

	}
	
	@Test 
	public void registerSpecificTag_SUCCESS() throws ReportErrorToUserException, IndexOutOfBoundsException
	{	
		mailboxService.registerSpecificTag(dev, p, new NotABug());
		tagAssignmentService.assignTag(dev, bugReport, new NotABug());
		List<ObserverAspect> registrations = mailboxService.getRegistrations(dev);
		assertEquals(registrations.size(), 1);
	
		List<Notification> notifications = mailboxService.getNotifications(dev, 1);
		assertEquals(notifications.size(), 1);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void registerSpecificTag_OTHERBRANCH() throws ReportErrorToUserException, IndexOutOfBoundsException
	{	
		mailboxService.registerSpecificTag(dev, p, new NotABug());
		BugReport bugReportNew = bugReportService.createBugReport("T", "D", user, s2, BugReport.PUBLIC);		
		List<ObserverAspect> registrations = mailboxService.getRegistrations(dev);
		assertEquals(registrations.size(), 1);
	
		List<Notification> notifications = mailboxService.getNotifications(dev, 1);
	
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void registerSpecificTag_FAILUSER() throws ReportErrorToUserException, IndexOutOfBoundsException
	{	
		mailboxService.registerSpecificTag(null, p, new NotABug());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void registerSpecificTag_FAILSUBJECT() throws ReportErrorToUserException, IndexOutOfBoundsException
	{	
		mailboxService.registerSpecificTag(dev, null, new NotABug());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void registerSpecificTag_FAILTAG() throws ReportErrorToUserException, IndexOutOfBoundsException
	{	
		mailboxService.registerSpecificTag(dev, p, null);
	}
	
	@Test (expected = IndexOutOfBoundsException.class) 
	public void registerSpecificTag_NOUPDATE() throws ReportErrorToUserException, IndexOutOfBoundsException
	{	
		mailboxService.registerSpecificTag(dev, s2, new NotABug());
		tagAssignmentService.assignTag(dev, bugReport, new NotABug());
		List<ObserverAspect> registrations = mailboxService.getRegistrations(dev);
		assertEquals(registrations.size(), 1);
	
		List<Notification> notifications = mailboxService.getNotifications(dev, 1);

	}

	@Test
	public void unregister_SUCCESS() throws ReportErrorToUserException, IndexOutOfBoundsException
	{	
		mailboxService.registerSpecificTag(dev, s2, new NotABug());
		List<ObserverAspect> registrations = mailboxService.getRegistrations(dev);
		assertEquals(registrations.size(), 1);
		
		ObserverAspect registration = registrations.get(0);
	
		mailboxService.unregister(dev, registration);
		assertEquals(registrations.size(), 0);

	}
	
	@Test(expected = IllegalArgumentException.class)
	public void unregister_FAIL() throws ReportErrorToUserException, IndexOutOfBoundsException
	{	
		mailboxService.registerSpecificTag(dev, s2, new NotABug());
		List<ObserverAspect> registrations = mailboxService.getRegistrations(dev);
		assertEquals(registrations.size(), 1);
		
		ObserverAspect registration = registrations.get(0);
	
		mailboxService.unregister(null, registration);

	}
	
	@Test
	public void getAllMailboxes_SUCCESS() throws ReportErrorToUserException, IndexOutOfBoundsException
	{	
		assertEquals(mailboxService.getAllMailboxes().size(),2);
		
		List<Mailbox> mailboxes = new ArrayList<>();
		mailboxes.add(user.getMailbox());
		mailboxes.add(dev.getMailbox());
		
		assertTrue(mailboxService.getAllMailboxes().containsAll(mailboxes));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void getNotifications_FAIL() throws ReportErrorToUserException, IndexOutOfBoundsException
	{	
		mailboxService.getNotifications(null, 0);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void getRegistrations_FAIL() throws ReportErrorToUserException, IndexOutOfBoundsException
	{	
		mailboxService.getRegistrations(null);
	}
}
