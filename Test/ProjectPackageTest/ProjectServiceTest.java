package ProjectPackageTest;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
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
		p1.setLeadRole(new Lead(dev));
		p2.setLeadRole(new Lead(dev));
		
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
}
