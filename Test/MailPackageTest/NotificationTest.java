package MailPackageTest;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.BugReport.TagAssignmentService;
import Model.Mail.MailboxService;
import Model.Mail.Notification;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.SubSystem;
import Model.Project.TheDate;
import Model.Roles.Lead;
import Model.User.Developer;
import Model.User.Issuer;
import Model.User.UserService;

public class NotificationTest
{
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
	
	private Notification notif;
	private BugReport bugReportNew;
	
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
		bugReport = bugReportService.createBugReport("T", "D", user, s, BugReport.PUBLIC,1);
		bugReport2 = bugReportService.createBugReport("T", "D", user, s2, BugReport.PUBLIC,1);
		
		
		mailboxService.registerCreationBugReport(dev, p);
		bugReportNew = bugReportService.createBugReport("T", "D", user, s, BugReport.PUBLIC,1);
		
		notif = mailboxService.getNotifications(dev, 1).get(0);
	}
	
	@Test
	public void getChangeDescription()
	{
		assertEquals(notif.getChangeDescription(),"New bug report");
	}
	
	@Test
	public void getSubject()
	{
		assertEquals(notif.getSubject(),bugReportNew);
	}
	
	@Test
	public void getStructure()
	{
		assertEquals(notif.getStructure(),p);
	}
	
	@Test
	public void toString_SUCCESS()
	{
		assertEquals(notif.toString(),"New bug report" + "\n" +
							bugReportNew + "\n in \n" + p +"\n");
	}
	

}
