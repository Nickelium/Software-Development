import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import CustomExceptions.ModelException;
import Model.Project.ProjectService;
import Model.Roles.Lead;
import Model.Roles.Role;
import Model.User.Developer;
import Model.User.Issuer;
import Model.User.UserService;
import com.sun.tools.internal.ws.processor.model.Model;
import org.junit.Before;
import org.junit.Test;

import Model.Project.Project;
import Model.Project.SubSystem;
import Model.Project.TheDate;

public class ProjectTest {

	private String name;
	private String description;
	private double  budget;
	private SubSystem s;
	private SubSystem ss;
	private Project p;
	private int day;
	private int month;
	private int year;
	private ProjectService projectService;
	private UserService userService;
	private Developer dev;
	
	@Before
	public void setup() throws ModelException{
		projectService = new ProjectService();
		userService = new UserService();
		Developer dev = (Developer) this.userService.createDeveloper("Test", "", "testing", "test1");
		this.name = "FirstName LastName";
		this.description = "This is an unknown person.";
		this.budget = 10;
		this.s = new SubSystem("Test1", "Test1 description");
		this.ss = new SubSystem("Test2", "Test2 description");
		this.p = projectService.createProject(this.name, this.description, new TheDate(10,6,2016), 0.0, new Lead(dev));
		this.day = 24;
		this.month = 2;
		this.year = 2016;
	}
	

	@Test
	public void constructor_SUCCES() throws ModelException{
		Project project = projectService.createProject(this.name,this.description, new TheDate(7,7,2016), 0.0, new Lead(dev));
		assertEquals(project.getName(), this.name);
		assertEquals(project.getDescription(), this.description);
		assertEquals(project.getBudget(),0.0,0.0);
		assertTrue(project.getSubSystems().isEmpty());
	}
	
	@Test (expected = NullPointerException.class)
	public void setName_FAIL() throws ModelException{
		p.setName(null);
	}
	
	@Test
	public void setName_SUCCES() throws ModelException{
		p.setName(this.name);

		assertEquals(p.getName(), this.name);
	}
	
	@Test (expected = NullPointerException.class)
	public void setDescription_FAIL() throws ModelException{
		p.setDescription(null);
	}
	
	@Test
	public void setDescription_SUCCES() throws ModelException{
		p.setDescription(this.description);
		assertEquals(p.getDescription(), this.description);
	}
	
	@Test 
	public void setStartingDate_SUCCES() throws ModelException
	{
		p.setStartingDate(new TheDate(this.day, this.month, this.year));
		assertEquals(p.getCreationDate().getDay(),this.day);
		assertEquals(p.getCreationDate().getMonth(),this.month);
		assertEquals(p.getCreationDate().getYear(),this.year);	
	}
	
	@Test
	public void setBudget_SUCCES() throws ModelException{
		p.setBudget(this.budget);
		assertEquals(p.getBudget(),this.budget,0.0);
	}
	
	@Test (expected = NullPointerException.class)
	public void addSubSystem_FAIL(){
		p.addSubSystem(null);
	}
	
	@Test
	public void addSubSystem_SUCCES(){
		p.addSubSystem(s);
		assertTrue(p.getSubSystems().contains(s));
	}
	
	@Test
	public void getAllSubSystem_SUCCES() throws Exception
	{
		List<SubSystem> list = new ArrayList<SubSystem>();
		list.add(s);
		list.add(ss);
		
		p.addSubSystem(s);
		s.addSubSystem(ss);
		
		assertEquals(p.getAllSubSystems().size(), list.size());
		assertTrue(p.getAllSubSystems().containsAll(list));
	}
	
	@Test
	public void getAllSubSystem_EMPTY_SUCCES() throws Exception
	{
		assertTrue(p.getAllSubSystems().isEmpty());
	}
}
