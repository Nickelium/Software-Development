package ProjectPackageTest;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.Milestone.Milestone;
import Model.Milestone.TargetMilestone;
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
	private SubSystem subsystem1;
	private SubSystem subsystem2;
	private SubSystem subsystem3;
	private Project project;
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
		this.project = projectService.createProject(this.name, this.description, this.startingDate, 0.0, lead);
		this.subsystem1 = projectService.createSubsystem("Test1", "Test1 description", project);
		this.subsystem2 = projectService.createSubsystem("Test2", "Test2 description", subsystem1);
		this.subsystem3 = projectService.createSubsystem("Test3", "Test3 description", subsystem2);

		this.day = 24;
		this.month = 2;
		this.year = 2020;
		this.programmer	 = new Programmer(dev);
	}
	
	@Test (expected = ReportErrorToUserException.class)
	public void setName_FAILNULL() throws ReportErrorToUserException {
		projectService.setSubSystemName(subsystem1, null);
	}
	
	@Test (expected = ReportErrorToUserException.class)
	public void setName_FAILEMPTY() throws ReportErrorToUserException {
		projectService.setSubSystemName(subsystem1, "");
	}
	
	@Test (expected = ReportErrorToUserException.class)
	public void setDescription_FAILNULL() throws ReportErrorToUserException {
		projectService.setSubSystemDescription(subsystem1, null);
	}
	
	@Test (expected = ReportErrorToUserException.class)
	public void setDescription_FAILEMPTY() throws ReportErrorToUserException {
		projectService.setSubSystemDescription(subsystem1, "");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addSubSystem_FAILNULL() throws ReportErrorToUserException {
		subsystem1.addSubSystem(null);
	}
	
	@Test (expected = ReportErrorToUserException.class)
	public void addSubSystem_FAILSELFDEEP() throws ReportErrorToUserException {
		subsystem1.addSubSystem(subsystem2);
		subsystem2.addSubSystem(subsystem3);
		subsystem1.addSubSystem(subsystem3);
	}
	
	@Test (expected = ReportErrorToUserException.class)
	public void addSubSystem_FAILSELF() throws ReportErrorToUserException {
		subsystem1.addSubSystem(subsystem1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addBugReport_FAIL() throws ReportErrorToUserException {
		subsystem1.addBugReport(null);
	}
	
	@Test
	public void getAllBugReports_SUCCES() throws Exception
	{
		List<BugReport> list = new ArrayList<>();
		BugReport bug1 = bugReportService.createBugReport("bug1", "d", dev, subsystem1, BugReport.PUBLIC,1);
		BugReport bug2 = bugReportService.createBugReport("bug2", "d", dev, subsystem2, BugReport.PUBLIC,1);
		list.add(bug1);
		list.add(bug2);
	
		assertEquals(subsystem1.getAllBugReports().size(), list.size());
		assertTrue(subsystem1.getAllBugReports().containsAll(list));
	}
	
	@Test 
	public void getAllSubSystem_SUCCES() throws ReportErrorToUserException {
		List<SubSystem> list = new ArrayList<>();
		list.add(subsystem2);
		list.add(subsystem3);
		

		assertEquals(subsystem1.getAllSubSystems().size(), list.size());
		assertTrue(subsystem1.getAllSubSystems().containsAll(list));
	}

	@Test
	public void getAllMilestones_SUCCES() throws Exception{

		projectService.setNewSubSystemMilestone(subsystem1, new Milestone("M1"));
		projectService.setNewSubSystemMilestone(subsystem1, new Milestone("M2"));
		assertEquals(subsystem1.getAllMilestones().get(1).getMilestoneID(), "M2");
		assertEquals(subsystem1.getAllMilestones().get(0).getMilestoneID(), "M1");

	}

	@Test (expected = ReportErrorToUserException.class)
	public void getAllMilestones_FAIL1() throws Exception{

		projectService.setNewSubSystemMilestone(subsystem1, new Milestone("M1"));
		SubSystem s2 = projectService.createSubsystem("0","0",subsystem1);
		projectService.setNewSubSystemMilestone(s2, new Milestone("M1.5"));
		projectService.setNewSubSystemMilestone(subsystem1, new Milestone("M2"));

	}

	@Test (expected = ReportErrorToUserException.class)
	public void getAllMilestones_FAIL2() throws Exception{

		projectService.setNewSubSystemMilestone(subsystem1,  new Milestone("M1"));
		BugReport bug1 = bugReportService.createBugReport("bug1", "d", dev, subsystem1, BugReport.PUBLIC,1);
		bugReportService.setTargetMilestone(bug1, new TargetMilestone("M1.2"));
		projectService.setNewSubSystemMilestone(subsystem1, new Milestone("M1.5"));

	}
	
	@Test
	public void getHeight_SUCCESS1()
	{
		assertEquals(subsystem1.getHeight(), 3);
	}
	
	@Test
	public void getHeight_SUCCESS2()
	{
		assertEquals(subsystem3.getHeight(), 1);
	}
	
	@Test
	public void toString_SUCCES() throws Exception
	{
		String string = "Subsystem name: " + "Test1" + "\nDescription: " + "Test1 description"
				+ "\nVersionID: " + subsystem1.getVersionID()
				+ "\nMilestone: " + subsystem1.getLatestAchievedMilestone();
		assertEquals(subsystem1.toString(), string);

	}
	

}
