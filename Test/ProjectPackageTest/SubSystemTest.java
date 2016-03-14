

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import CustomExceptions.ModelException;
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
	public void setup() throws ModelException{
		
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
		this.s = new SubSystem("Test1", "Test1 description");
		this.ss = new SubSystem("Test2", "Test2 description");
		this.sss = new SubSystem("Test3", "Test3 description");
		this.p = projectService.createProject(this.name, this.description, this.startingDate, 0.0, lead);
		this.day = 24;
		this.month = 2;
		this.year = 2020;
		this.programmer	 = new Programmer(dev);
	}
	
//	@Test (expected = ModelException.class)
//	public void setVersionID_FAIL() throws ModelException{
//		s.setVersionID(-1.0);
//	}
//
//	@Test
//	public void setVersionID_SUCCES() throws ModelException{
//		s.setVersionID(this.versionID);
//
//		assertEquals(s.getVersionID(), this.versionID,0.0);
//	}
	
	@Test (expected = ModelException.class)
	public void setName_FAILNULL() throws ModelException{
		s.setName(null);
	}
	
	@Test (expected = ModelException.class)
	public void setName_FAILEMPTY() throws ModelException{
		s.setName("");
	}
	
	@Test (expected = ModelException.class)
	public void setDescription_FAILNULL() throws ModelException{
		s.setDescription(null);
	}
	
	@Test (expected = ModelException.class)
	public void setDescription_FAILEMPTY() throws ModelException{
		s.setDescription("");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addSubSystem_FAILNULL() throws ModelException{
		s.addSubSystem(null);
	}
	
	@Test (expected = ModelException.class)
	public void addSubSystem_FAILSELFDEEP() throws ModelException{
		s.addSubSystem(ss);
		ss.addSubSystem(sss);
		s.addSubSystem(sss);
	}
	
	@Test (expected = ModelException.class)
	public void addSubSystem_FAILSELF() throws ModelException{
		s.addSubSystem(s);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addBugReport_FAIL() throws ModelException{
		s.addBugReport(null);
	}
	
	@Test
	public void getAllBugReports_SUCCES() throws Exception
	{
		List<BugReport> list = new ArrayList<>();
		BugReport bug1 =bugReportService.createBugReport("bug1", "d", dev, s);
		BugReport bug2 = bugReportService.createBugReport("bug2", "d", dev, ss);
		list.add(bug1);
		list.add(bug2);
		
		s.addSubSystem(ss);

		assertEquals(s.getAllBugReports().size(), list.size());
		assertTrue(s.getAllBugReports().containsAll(list));
	}
	
	@Test 
	public void getSubSystem_SUCCES() throws ModelException{
		s.addSubSystem(ss);
		s.addSubSystem(sss);
		List<SubSystem> list = new ArrayList<>();
		list.add(ss);
		list.add(sss);
		

		assertEquals(s.getSubSystems().size(), list.size());
		assertTrue(s.getSubSystems().containsAll(list));
	}
	
	
	
	@Test
	public void toString_SUCCES() throws Exception
	{
	
		String str = "Subsystem name: " + "Test1" + "\nDescription: " + "Test1 description" 
					+ "\nVersionID: " + s.getVersionID(); 
		assertEquals(s.toString(), str);

	}
	

}