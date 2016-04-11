

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.SubSystem;
import Model.Project.TheDate;
import Model.Roles.Lead;
import Model.Roles.Programmer;
import Model.User.Developer;
import Model.User.UserService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SubSystemTest {

	private String name;
	private String description;
	private TheDate startingDate;
	private double  budget;
	private double versionID;
	private SubSystem s;
	private SubSystem ss;
	private SubSystem sss;
	private Project p;
	private int day;
	private int month;
	private int year;
	private ProjectService projectService;
	private UserService userService;
	private BugReportService bugReportService;
	private Developer dev;
	private Lead lead;
	private Programmer programmer;

	@Before
	public void setup() throws ReportErrorToUserException {
		
		projectService = new ProjectService();
		userService = new UserService();
		bugReportService = new BugReportService(projectService);
		dev = (Developer) this.userService.createDeveloper("Test", "", "testing", "test1");
		lead = new Lead(dev);
		this.name = "FirstName LastName";
		this.description = "This is an unknown person.";
		this.startingDate = new TheDate("19/02/2030");
		this.budget = 10;
		this.versionID = 2.0;
		this.p = projectService.createProject(this.name, this.description, this.startingDate, 0.0, lead);
		this.s = projectService.createSubsystem("Test1", "Test1 description",p);
		this.ss = projectService.createSubsystem("Test2", "Test2 description",s);
		this.sss = projectService.createSubsystem("Test3", "Test3 description",ss);

		this.day = 24;
		this.month = 2;
		this.year = 2020;
		this.programmer	 = new Programmer(dev);
	}
	
	@Test (expected = ReportErrorToUserException.class)
	public void setName_FAILNULL() throws ReportErrorToUserException {
		s.setName(null);
	}
	
	@Test (expected = ReportErrorToUserException.class)
	public void setName_FAILEMPTY() throws ReportErrorToUserException {
		s.setName("");
	}
	
	@Test (expected = ReportErrorToUserException.class)
	public void setDescription_FAILNULL() throws ReportErrorToUserException {
		s.setDescription(null);
	}
	
	@Test (expected = ReportErrorToUserException.class)
	public void setDescription_FAILEMPTY() throws ReportErrorToUserException {
		s.setDescription("");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addSubSystem_FAILNULL() throws ReportErrorToUserException {
		s.addSubSystem(null);
	}
	
	@Test (expected = ReportErrorToUserException.class)
	public void addSubSystem_FAILSELFDEEP() throws ReportErrorToUserException {
		s.addSubSystem(ss);
		ss.addSubSystem(sss);
		s.addSubSystem(sss);
	}
	
	@Test (expected = ReportErrorToUserException.class)
	public void addSubSystem_FAILSELF() throws ReportErrorToUserException {
		s.addSubSystem(s);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addBugReport_FAIL() throws ReportErrorToUserException {
		s.addBugReport(null);
	}
	
	@Test
	public void getAllBugReports_SUCCES() throws Exception
	{
		List<BugReport> list = new ArrayList<>();
		BugReport bug1 = bugReportService.createBugReport("bug1", "d", dev, s, BugReport.PUBLIC);
		BugReport bug2 = bugReportService.createBugReport("bug2", "d", dev, ss, BugReport.PUBLIC);
		list.add(bug1);
		list.add(bug2);
	
		assertEquals(s.getAllBugReports().size(), list.size());
		assertTrue(s.getAllBugReports().containsAll(list));
	}
	
	@Test 
	public void getAllSubSystem_SUCCES() throws ReportErrorToUserException {
		List<SubSystem> list = new ArrayList<>();
		list.add(ss);
		list.add(sss);
		

		assertEquals(s.getAllSubSystems().size(), list.size());
		assertTrue(s.getAllSubSystems().containsAll(list));
	}
	
	
	
	@Test
	public void toString_SUCCES() throws Exception
	{
	
		String str = "Subsystem name: " + "Test1" + "\nDescription: " + "Test1 description" 
					+ "\nVersionID: " + s.getVersionID(); 
		assertEquals(s.toString(), str);

	}
	

}
