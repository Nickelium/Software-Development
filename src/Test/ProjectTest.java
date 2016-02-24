package Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

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
	
	@Before
	public void setup(){
		this.name = "FirstName LastName";
		this.description = "This is an unknown person.";
		this.budget = 10;
		this.s = new SubSystem();
		this.ss = new SubSystem();
		this.p = new Project(this.name, this.description);
		this.day = 24;
		this.month = 2;
		this.year = 2016;
	}
	

	@Test
	public void constructor2Param_SUCCES(){
		Project project = new Project(this.name,this.description);
		assertEquals(project.getName(), this.name);
		assertEquals(project.getDescription(), this.description);
		assertEquals(project.getBudget(),0.0,0.0);
		assertTrue(project.getSubSystems().isEmpty());
	}
	
	@Test
	public void constructor3Param_SUCCES(){
		Project project = new Project(this.name,this.description,this.budget);
		assertEquals(project.getName(), this.name);
		assertEquals(project.getDescription(), this.description);
		assertEquals(project.getBudget(),this.budget,0.0);
		assertTrue(project.getSubSystems().isEmpty());
	}
	
	@Test (expected = NullPointerException.class)
	public void setName_FAIL(){
		p.setName(null);
	}
	
	@Test
	public void setName_SUCCES(){
		p.setName(this.name);

		assertEquals(p.getName(), this.name);
	}
	
	@Test (expected = NullPointerException.class)
	public void setDescription_FAIL(){
		p.setDescription(null);
	}
	
	@Test
	public void setDescription_SUCCES(){
		p.setDescription(this.description);
		assertEquals(p.getDescription(), this.description);
	}
	
	@Test 
	public void setStartingDate_SUCCES()
	{
		p.setStartingDate(this.day, this.month, this.year);
		assertEquals(p.getCreationDate().getDay(),this.day);
		assertEquals(p.getCreationDate().getMonth(),this.month);
		assertEquals(p.getCreationDate().getYear(),this.year);	
	}
	
	@Test
	public void setBudget_SUCCES(){
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
		
		assertEquals(p.getAllSubSystem().size(), list.size());
		assertTrue(p.getAllSubSystem().containsAll(list));
	}
	
	@Test
	public void getAllSubSystem_EMPTY_SUCCES() throws Exception
	{
		assertTrue(p.getAllSubSystem().isEmpty());
	}
	
	@Test
	public void clone_SUCCES()
	{
		Project project = new Project(this.name,this.description,this.budget);
		Project clone = project.clone();
		
		assertEquals(project.getName(),clone.getName());
		assertEquals(project.getDescription(),clone.getDescription());
		assertEquals(project.getCreationDate(),clone.getCreationDate());
		assertEquals(project.getStartingDate(),clone.getStartingDate());
		assertEquals(project.getBudget(),clone.getBudget(),0.0);
		assertTrue(project.getSubSystems().containsAll(clone.getAllSubSystem()));
	}
}
