package ProjectPackageTest;

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

public class ProjectTest {

	private String name;
	private String description;
	private TheDate startingDate;
	private double  budget;
	private double versionID;
	private SubSystem s;
	private SubSystem ss;
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
	private Project proj;

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
		this.p = projectService.createProject("Project", "Project description", this.startingDate, this.budget, this.lead);
		this.s = projectService.createSubsystem("Test1", "Test1 description", p);
		this.ss =projectService.createSubsystem("Test2", "Test2 description", s);
		//this.s = new SubSystem("Test1", "Test1 description");
		//this.ss = new SubSystem("Test2", "Test2 description");
		this.day = 24;
		this.month = 2;
		this.year = 2020;
		this.programmer	 = new Programmer(dev);
	}


	@Test
	public void constructor_SUCCES() throws ReportErrorToUserException {
		Project project = projectService.createProject(this.name,this.description, this.startingDate, this.budget, this.lead);
		assertEquals(project.getName(), this.name);
		assertEquals(project.getDescription(), this.description);
		assertEquals(project.getStartingDate(), this.startingDate);
		assertEquals(project.getBudget(),this.budget,0.0);
		assertEquals(project.getLeadRole(), this.lead);
		assertTrue(project.getSubSystems().isEmpty());
	}

	@Test (expected = ReportErrorToUserException.class)
	public void setName_FAILNULL() throws ReportErrorToUserException {
		projectService.setProjectName(p, null);
	}
	
	@Test (expected = ReportErrorToUserException.class)
	public void setName_FAILEMPTY() throws ReportErrorToUserException {
		projectService.setProjectName(p, "");
	}

	@Test
	public void setName_SUCCES() throws ReportErrorToUserException {
		projectService.setProjectName(p, this.name);
		assertEquals(p.getName(), this.name);
	}

	@Test (expected = ReportErrorToUserException.class)
	public void setDescription_FAILNULL() throws ReportErrorToUserException {
		projectService.setProjectDescription(p, null);
	}
	
	@Test (expected = ReportErrorToUserException.class)
	public void setDescription_FAILEMPTY() throws ReportErrorToUserException {
		projectService.setProjectDescription(p, "");
	}

	@Test
	public void setDescription_SUCCES() throws ReportErrorToUserException {
		projectService.setProjectDescription(p, this.description);
		assertEquals(p.getDescription(), this.description);
	}

	@Test
	public void setStartingDate_SUCCES() throws ReportErrorToUserException
	{
		projectService.setProjectStartingDate(p, new TheDate(this.day, this.month, this.year));
		assertEquals(p.getStartingDate().getDay(),this.day);
		assertEquals(p.getStartingDate().getMonth(),this.month);
		assertEquals(p.getStartingDate().getYear(),this.year);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void setStartingDate_FAILNULL() throws ReportErrorToUserException
	{
		projectService.setProjectStartingDate(p, null);
	}
	
	@Test (expected = ReportErrorToUserException.class)
	public void setStartingDate_FAILCONDITION() throws ReportErrorToUserException
	{
		projectService.setProjectStartingDate(p, new TheDate("20/10/2010"));
	}


	@Test
	public void setBudget_SUCCES() throws ReportErrorToUserException {
		projectService.setProjectBudget(p, this.budget);
		assertEquals(p.getBudget(),this.budget,0.0);
	}
	
	@Test (expected = ReportErrorToUserException.class)
	public void setBudget_FAIL() throws ReportErrorToUserException {
		projectService.setProjectBudget(p, -2.0);
	}
	
	@Test
	public void setVersionID_SUCCES() throws ReportErrorToUserException {
		projectService.setProjectVersionID(p, this.versionID);
		assertEquals(p.getVersionID(),this.versionID,0.0);
	}

	@Test (expected = ReportErrorToUserException.class)
	public void setVersionID_FAIL() throws ReportErrorToUserException {
		projectService.setProjectVersionID(p, 0.2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void setLeadRole_FAIL() throws ReportErrorToUserException {
		projectService.setProjectLeadRole(p, null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addSubSystem_FAIL(){
		p.addSubSystem(null);
	}

	@Test
	public void getAllSubSystem_SUCCES() throws Exception
	{
		List<SubSystem> list = new ArrayList<SubSystem>();
		list.add(s);
		list.add(ss);

		assertEquals(p.getAllSubSystems().size(), list.size());
		assertTrue(p.getAllSubSystems().containsAll(list));
	}

	@Test
	public void addRole_SUCCES() throws Exception
	{
		p.addRole(programmer);
		assertTrue(p.getDevsRoles().contains(programmer));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addRole_FAIL() throws Exception
	{
		p.addRole(null);

	}
	
	@Test
	public void getAllBugReports_SUCCES() throws Exception
	{		
		List<BugReport> list = new ArrayList<>();
		BugReport bug1 = bugReportService.createBugReport("bug1", "d", dev, s, BugReport.PUBLIC);
		BugReport bug2 = bugReportService.createBugReport("bug2", "d", dev, ss, BugReport.PUBLIC);
		list.add(bug1);
		list.add(bug2);

		assertEquals(p.getAllBugReports().size(), list.size());
		assertTrue(p.getAllBugReports().containsAll(list));
	}
	
	@Test
	public void fork_SUCCES() throws Exception
	{
		BugReport bug1 = bugReportService.createBugReport("bug1", "d", dev, s, BugReport.PUBLIC);
		BugReport bug2 = bugReportService.createBugReport("bug2", "d", dev, ss, BugReport.PUBLIC);
		
		p.addRole(programmer);
		
		Project fork = p.fork();
		
		assertEquals(p.getName(), fork.getName());
		assertEquals(p.getDescription(), fork.getDescription());
		assertEquals(p.getStartingDate(), fork.getStartingDate());
		assertEquals(p.getAllSubSystems().size(), fork.getAllSubSystems().size());
		assertTrue(p.getAllBugReports().size() != fork.getAllBugReports().size());
	
	}
	
	@Test
	public void toString_SUCCES() throws Exception
	{		
		p.addRole(programmer);
		String str= "Project name: " + "Project" + "\nDescription: " + "Project description" 
		+"\nCreation Date: " + p.getCreationDate() 
		+ "\nStarting Date: " + this.startingDate + "\nBudget: " + p.getBudget()
		+ "\nVersionID: " + p.getVersionID() + "\nLead developer: " 
		+ dev +"\n" + programmer.toString()+"\n";
		assertEquals(p.toString(), str);

	}
	
	
}
