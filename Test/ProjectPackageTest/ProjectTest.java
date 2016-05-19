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
import Model.Roles.Role;
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
	private SubSystem subsystem1;
	private SubSystem subsystem2;
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
		this.project = projectService.createProject("Project", "Project description", this.startingDate, this.budget, this.lead);
		this.subsystem1 = projectService.createSubsystem("Test1", "Test1 description", project);
		this.subsystem2 =projectService.createSubsystem("Test2", "Test2 description", subsystem1);
		//this.subsystem1 = new SubSystem("Test1", "Test1 description");
		//this.subsystem2 = new SubSystem("Test2", "Test2 description");
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
		projectService.setProjectName(project, null);
	}
	
	@Test (expected = ReportErrorToUserException.class)
	public void setName_FAILEMPTY() throws ReportErrorToUserException {
		projectService.setProjectName(project, "");
	}

	@Test
	public void setName_SUCCES() throws ReportErrorToUserException {
		projectService.setProjectName(project, this.name);
		assertEquals(project.getName(), this.name);
	}

	@Test (expected = ReportErrorToUserException.class)
	public void setDescription_FAILNULL() throws ReportErrorToUserException {
		projectService.setProjectDescription(project, null);
	}
	
	@Test (expected = ReportErrorToUserException.class)
	public void setDescription_FAILEMPTY() throws ReportErrorToUserException {
		projectService.setProjectDescription(project, "");
	}

	@Test
	public void setDescription_SUCCES() throws ReportErrorToUserException {
		projectService.setProjectDescription(project, this.description);
		assertEquals(project.getDescription(), this.description);
	}

	@Test
	public void setStartingDate_SUCCES() throws ReportErrorToUserException
	{
		projectService.setProjectStartingDate(project, new TheDate(this.day, this.month, this.year));
		assertEquals(project.getStartingDate().getDay(),this.day);
		assertEquals(project.getStartingDate().getMonth(),this.month);
		assertEquals(project.getStartingDate().getYear(),this.year);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void setStartingDate_FAILNULL() throws ReportErrorToUserException
	{
		projectService.setProjectStartingDate(project, null);
	}
	
	@Test (expected = ReportErrorToUserException.class)
	public void setStartingDate_FAILCONDITION() throws ReportErrorToUserException
	{
		projectService.setProjectStartingDate(project, new TheDate("20/10/2010"));
	}


	@Test
	public void setBudget_SUCCES() throws ReportErrorToUserException {
		projectService.setProjectBudget(project, this.budget);
		assertEquals(project.getBudget(),this.budget,0.0);
	}
	
	@Test (expected = ReportErrorToUserException.class)
	public void setBudget_FAIL() throws ReportErrorToUserException {
		projectService.setProjectBudget(project, -2.0);
	}
	
	@Test
	public void setVersionID_SUCCES() throws ReportErrorToUserException {
		projectService.setProjectVersionID(project, this.versionID);
		assertEquals(project.getVersionID(),this.versionID,0.0);
	}

	@Test (expected = ReportErrorToUserException.class)
	public void setVersionID_FAIL() throws ReportErrorToUserException {
		projectService.setProjectVersionID(project, 0.2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void setLeadRole_FAIL() throws ReportErrorToUserException {
		projectService.setProjectLeadRole(project, null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addSubSystem_FAIL(){
		project.addSubSystem(null);
	}

	@Test
	public void getAllSubSystem_SUCCES() throws Exception
	{
		List<SubSystem> list = new ArrayList<SubSystem>();
		list.add(subsystem1);
		list.add(subsystem2);

		assertEquals(project.getAllSubSystems().size(), list.size());
		assertTrue(project.getAllSubSystems().containsAll(list));
	}

	@Test
	public void addRole_SUCCES() throws Exception
	{
		projectService.assignRole(project, programmer, lead.getDeveloper());
		assert (project.getDevsRoles().contains(programmer));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addRole_FAIL() throws Exception
	{
		projectService.assignRole(project, null, lead.getDeveloper());

	}
	
	@Test
	public void getAllBugReports_SUCCES() throws Exception
	{		
		List<BugReport> list = new ArrayList<>();
		BugReport bug1 = bugReportService.createBugReport("bug1", "d", dev, subsystem1, BugReport.PUBLIC,1);
		BugReport bug2 = bugReportService.createBugReport("bug2", "d", dev, subsystem2, BugReport.PUBLIC,1);
		list.add(bug1);
		list.add(bug2);

		assertEquals(project.getAllBugReports().size(), list.size());
		assertTrue(project.getAllBugReports().containsAll(list));
	}

	@Test
	public void getAllMilestones_SUCCES() throws Exception{

		projectService.setNewProjectMilestone(project, new Milestone("M1"));
		assertEquals(project.getLatestAchievedMilestone().getMilestoneID(), "M1");
		projectService.setNewProjectMilestone(project, new Milestone("M2"));
		assertEquals(project.getLatestAchievedMilestone().getMilestoneID(), "M2");

	}

	@Test (expected = ReportErrorToUserException.class)
	public void getAllMilestones_FAIL1() throws Exception{

		projectService.setNewProjectMilestone(project,  new Milestone("M1"));
		SubSystem s1 = projectService.createSubsystem("0","0",project);
		projectService.setNewSubSystemMilestone(s1, new Milestone("M1.5"));
		projectService.setNewProjectMilestone(project, new Milestone("M2"));

	}

	@Test (expected = ReportErrorToUserException.class)
	public void getAllMilestones_FAIL2() throws Exception{

		projectService.setNewProjectMilestone(project,  new Milestone("M1"));
		BugReport bug1 = bugReportService.createBugReport("bug1", "d", dev, subsystem1, BugReport.PUBLIC,1);
		bugReportService.setTargetMilestone(bug1, new TargetMilestone("M1.2"));
		projectService.setNewProjectMilestone(project, new Milestone("M1.5"));

	}

	@Test
	public void fork_SUCCES() throws Exception
	{
		BugReport bug1 = bugReportService.createBugReport("bug1", "d", dev, subsystem1, BugReport.PUBLIC,1);
		BugReport bug2 = bugReportService.createBugReport("bug2", "d", dev, subsystem2, BugReport.PUBLIC,1);

		projectService.assignRole(project, programmer, lead.getDeveloper());

		Project fork = projectService.forkProject(project);
		
		assertEquals(project.getName(), fork.getName());
		assertEquals(project.getDescription(), fork.getDescription());
		assertEquals(project.getStartingDate(), fork.getStartingDate());
		assertEquals(project.getAllSubSystems().size(), fork.getAllSubSystems().size());
		assertTrue(project.getAllBugReports().size() != fork.getAllBugReports().size());
	
	}
	
	@Test
	public void toString_SUCCES() throws Exception {
		projectService.assignRole(project, programmer, lead.getDeveloper());

		String string = "Project name: " + "Project"
				+ "\nDescription: " + "Project description"
				+ "\nCreation Date: " + project.getCreationDate()
				+ "\nStarting Date: " + project.getStartingDate() + "\nBudget: " + project.getBudget()
				+ "\nVersionID: " + project.getVersionID()
				+ "\nMilestone: " + project.getLatestAchievedMilestone()
				+ "\nLead developer: " + project.getLeadRole().getDeveloper();

		for (Role role : project.getDevsRoles()) {
			string += "\n" +role.toString() ;
		}

		assertEquals(project.toString(), string);

	}
	
	@Test
	public void getHeight_SUCCESS2()
	{
		assertEquals(project.getHeight(), 3);
	}
	
	
}
