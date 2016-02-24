package Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Model.Project;
import Model.SubSystem;

public class ProjectTest {

	private String name;
	private String description;
	private double  budget;
	private Project project;
	private SubSystem s;
	@Before
	public void setup(){
		this.name = "FirstName LastName";
		this.description = "This is an unknown person.";
		this.budget = 10;
		this.project = new Project();
		this.s = new SubSystem();
	}
	
	@Test
	public void constuctorNoParam_SUCCES(){
		Project p = new Project();
		
		assertNull(p.getName());
		assertNull(p.getDescription());
		assertEquals(p.getBudget(),0.0,0.0); //don't know how to do with double to check
		assertTrue(p.getSubSystems().isEmpty());
	}
	
	@Test (expected =  NullPointerException.class)
	public void constuctor2Param_FAIL_1(){
		new Project(null, this.description);
		
	}
	
	@Test (expected = NullPointerException.class)
	public void constructor2Param_FAIL_2(){
		new Project(this.name,null);
	}
	
	@Test
	public void constructor2Param_SUCCES(){
		Project p = new Project(this.name, this.description);
		
		assertEquals(p.getName(), this.name);
		assertEquals(p.getDescription(), this.description);
		assertEquals(p.getBudget(),0.0,0.0);
		assertTrue(p.getSubSystems().isEmpty());
	}
	
	@Test (expected =  NullPointerException.class)
	public void constuctor3Param_FAIL_1(){
		new Project(null, this.description,this.budget);
		
	}
	
	@Test (expected = NullPointerException.class)
	public void constructor3Param_FAIL_2(){
		new Project(this.name,null,this.budget);
	}
	
	
	@Test
	public void constructor3Param_SUCCES(){
		Project p = new Project(this.name, this.description,this.budget);
		
		assertEquals(p.getName(), this.name);
		assertEquals(p.getDescription(), this.description);
		assertEquals(p.getBudget(),this.budget,0.0);
		assertTrue(p.getSubSystems().isEmpty());
	}
	
	@Test (expected = NullPointerException.class)
	public void setName_FAIL(){
		project.setName(null);
	}
	
	@Test
	public void setName_SUCCES(){
		project.setName(this.toString());

		assertEquals(project.getName(), this.name);
	}
	
	@Test (expected = NullPointerException.class)
	public void setDescription_FAIL(){
		project.setDescription(null);
	}
	
	@Test
	public void setDescription_SUCCES(){
		project.setDescription(this.description);
		assertEquals(project.getDescription(), this.description);
	}
	
	@Test
	public void setBudget_SUCCES(){
		project.setBudget(this.budget);
		assertEquals(project.getBudget(),this.budget,0.0);
	}
	
	@Test (expected = NullPointerException.class)
	public void addSubSystem_FAIL(){
		project.addSubSystem(null);
	}
	
	@Test
	public void addSubSystem_SUCCES(){
		project.addSubSystem(s);
		assertTrue(project.getSubSystems().contains(s));
	}
}
