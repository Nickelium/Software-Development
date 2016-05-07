package ProjectPackageTest;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.Milestone.Milestone;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.SubSystem;
import Model.Project.TheDate;
import Model.Roles.Lead;
import Model.User.Developer;
import Model.User.UserService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ProjectServiceTest {

	private ProjectService projectService;
	private BugReportService bugReportService;
	private UserService userService;
	private Project p1;
	private Project p2;
	private Developer dev;
	private SubSystem s;
	
	@Before
	public void setup() throws ReportErrorToUserException {
		projectService = new ProjectService();
		bugReportService = new BugReportService(projectService);
		userService = new UserService();
		dev = this.userService.createDeveloper("Firstname", "Middlename", "Lastname", "Username");
		p1 = projectService.createProject("P1", "P1 description", new TheDate("10/02/2020"), 10, new Lead(dev));
		p2 = projectService.createProject("P2", "P2 description", new TheDate("10/02/2021"), 11, new Lead(dev));
		this.s = projectService.createSubsystem("Test 1", "Test 1 description", p2);
	}
	
	@Test
	public void getAllProjects_SUCCES() {
	
		List<Project> list = new ArrayList<>();
		list.add(p1);
		list.add(p2);
		assertEquals(projectService.getAllProjects().size(),list.size());
		assertTrue(projectService.getAllProjects().containsAll(list));
		
	}
	
	@Test
	public void forkProject_SUCCES() throws ReportErrorToUserException {

		BugReport bug1 = bugReportService.createBugReport("bug1", "d", dev, s, BugReport.PUBLIC);
		Project p3 = projectService.forkProject(p2);
		
		assertEquals(p3.getName(),p2.getName());
		assertEquals(p3.getAllSubSystems().size(),p2.getAllSubSystems().size());
		assertFalse(p3.getAllBugReports().size() == p2.getAllBugReports().size());
		
	}
	
	@Test
	public void createSubSystem_forProject() throws ReportErrorToUserException {
		
		SubSystem s = projectService.createSubsystem("A", "A", p1);
		
		assertEquals(s.getName(),"A");
		assertEquals(s.getDescription(),"A");
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void createSubSystem_forProject_FAIL() throws ReportErrorToUserException {
		Project p = null;
		SubSystem s = projectService.createSubsystem("A", "A", p);		
	}
	
	@Test
	public void createSubSystem_forSubSystem() throws ReportErrorToUserException {
		
		SubSystem s = projectService.createSubsystem("A", "A", p1);
		SubSystem ss = projectService.createSubsystem("B", "B", s);
		assertEquals(ss.getName(),"B");
		assertEquals(ss.getDescription(),"B");
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void createSubSystem_forSubSystem_FAIL() throws ReportErrorToUserException {
		SubSystem p = null;
		SubSystem s = projectService.createSubsystem("A", "A", p);		
	}
	
	@Test
	public void deleteProject() throws ReportErrorToUserException {
	
		projectService.deleteProject(p1);
		projectService.deleteProject(p2);
		
		assertTrue(projectService.getAllProjects().isEmpty());
	
	}
	
	@Test
	public void getAllSubSystems() throws ReportErrorToUserException {
	
		SubSystem ss = projectService.createSubsystem("B", "B", this.s);
		List<SubSystem> list = new ArrayList<>();
		list.add(this.s);
		list.add(ss);
		assertEquals(projectService.getAllSubSystems().size(),list.size());
		assertTrue(projectService.getAllSubSystems().containsAll(list));
	
	}
	
	@Test
	public void getProjectsOfLeadRole() throws ReportErrorToUserException
	{
		projectService.setProjectLeadRole(p1, new Lead(dev));
		projectService.setProjectLeadRole(p2, new Lead(dev));
		
		List<Project> list = new ArrayList<>();
		list.add(p1);
		list.add(p2);
		
		assertEquals(projectService.getProjectsOfLeadRole(dev).size(),list.size());
		assertTrue(projectService.getProjectsOfLeadRole(dev).containsAll(list));
	}

	@Test
	public void getProjectContainingBugReport_SUCCES() throws ReportErrorToUserException
	{
		SubSystem s = projectService.createSubsystem("A", "A", p1);
		BugReport bug1 = bugReportService.createBugReport("bug1", "d", dev, s, BugReport.PUBLIC);

		assertEquals(projectService.getProjectsContainingBugReport(bug1), p1);
	}
	
	@Test (expected = ReportErrorToUserException.class)
	public void getProjectContainingBugReport_FAIL() throws ReportErrorToUserException
	{
		Project pp = projectService.createProject("zae", "des", new TheDate("10/02/14"), 10, new Lead(dev));
		SubSystem ssss = projectService.createSubsystem("az", "des", pp);
		BugReport bug1 = bugReportService.createBugReport("bug1", "d", dev, ssss, BugReport.PUBLIC);
		projectService.getProjectsContainingBugReport(bug1);
	}
	
	@Test 
	public void setSubsystemName_SUCCESS() throws ReportErrorToUserException
	{
		projectService.setSubSystemName(s, "Name");
		assertEquals(s.getName(), "Name");
	}
	
	@Test 
	public void setSubsystemDescription_SUCCESS() throws ReportErrorToUserException
	{
		projectService.setSubSystemDescription(s, "des");
		assertEquals(s.getDescription(), "des");
	}
	
	@Test
	public void setNewSubSystemMilestone_SUCCESS() throws ReportErrorToUserException
	{
		Milestone m = new Milestone("M1");
		projectService.setNewSubSystemMilestone(s, m);
		assertEquals(s.getLatestAchievedMilestone(), m);
	}
	
	@Test
	public void removeSubSystem_SUCCESS() throws ReportErrorToUserException
	{
		SubSystem s2 = projectService.createSubsystem("A", "A", s);
		assertTrue(s.getSubSystems().contains(s2));
		projectService.removeSubSystem(p2, s2);
		assertTrue(!s.getSubSystems().contains(s2));
		
	}
	
	@Test
	public void removeSubSystem_NOREMOVE() throws ReportErrorToUserException
	{
		SubSystem s2 = projectService.createSubsystem("A", "A", s);
		assertTrue(s.getSubSystems().contains(s2));
		projectService.removeSubSystem(p1, s2);
		assertTrue(s.getSubSystems().contains(s2));
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void removeSubSystem_FAILPROJECT() throws ReportErrorToUserException
	{
		projectService.removeSubSystem(null, s);
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void removeSubSystem_FAILSUBSYSTEM() throws ReportErrorToUserException
	{
		projectService.removeSubSystem(p1, null);
		
	}
	
	@Test
	public void setNewSubSystemMilestone_LIST_SUCCESS() throws ReportErrorToUserException
	{
		List<Milestone> milestones = new ArrayList<>();
		milestones.add(new Milestone("M1.1"));
		milestones.add(new Milestone("M1.2"));
		projectService.setNewSubSystemMilestone(s, milestones);
		assertEquals(s.getLatestAchievedMilestone(), new Milestone("M1.2"));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void setNewSubSystemMilestone_LIST_FAILSUBSYSTEM() throws ReportErrorToUserException
	{
		List<Milestone> milestones = new ArrayList<>();
		milestones.add(new Milestone("M1.1"));
		milestones.add(new Milestone("M1.2"));
		projectService.setNewSubSystemMilestone(null, milestones);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void setNewSubSystemMilestone_LIST_FAILMILESTONE() throws ReportErrorToUserException
	{
		List<Milestone> milestones = null;
		projectService.setNewSubSystemMilestone(s, milestones);
	}
	
	@Test 
	public void getParent_SUCCESS() throws ReportErrorToUserException
	{
		SubSystem ss = projectService.createSubsystem("az", "zae", s);
		assertEquals(projectService.getParentSubSystem(ss),s);
	}
	
	@Test 
	public void getParent_NOPARENT() throws ReportErrorToUserException
	{
		assertEquals(projectService.getParentSubSystem(s),null);
	}
	
	@Test 
	public void getRelated_SUCCESS() throws ReportErrorToUserException
	{
		SubSystem ss = projectService.createSubsystem("az", "zae", s);
		SubSystem sss = projectService.createSubsystem("az", "zae", p2);
		List<SubSystem> list = new ArrayList<>();
		list.add(ss);
		list.add(sss);
		assertTrue(projectService.getRelated(p2, s).containsAll(list));
		
	}
	
	@Test 
	public void getRelated_SUCCESS2() throws ReportErrorToUserException
	{
		SubSystem ss = projectService.createSubsystem("az", "zae", s);
		SubSystem sss = projectService.createSubsystem("az", "zae", ss);
		List<SubSystem> list = new ArrayList<>();
		list.add(s);
		list.add(sss);
		assertTrue(projectService.getRelated(p2, ss).containsAll(list));
		
	}
}
