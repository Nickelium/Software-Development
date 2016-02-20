package Model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Project
{

	private String name;
	private String description;
	private TheDate creationDate;
	private TheDate startingDate;
	private double budget;
	
	private List<SubSystem> subSystems;
	
	//developer todo later
	
	/**
	 * Constructoren
	 */
	public Project()
	{
		this(null,null,null,0.0);
	}
	
	public Project(String newName, String newDescription)
	{
		this(newName, newDescription,null,0.0);
	}
	
	public Project(String newName, String newDescription, TheDate newStartingDate, double newBudget)
	{
		name = newName;
		description = newDescription;
		startingDate = newStartingDate;
		budget = newBudget;
	}
	
	/**
	 * Getters
	 * @return
	 */
	public String getName()
	{
		return name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public TheDate getCreationDate()
	{
		return creationDate;
	}
	
	public TheDate getStartingDate()
	{
		return startingDate;
	}
	
	public double getBudget()
	{
		return budget;
	}
	
	public List<SubSystem> getSubSystems()
	{
		return new ArrayList<SubSystem>(subSystems);
	}
	
	/**
	 * Setters
	 */
	public void setName(String newName)
	{
		name = newName;
	}
	
	public void setDescription(String newDescription)
	{
		description = newDescription;
	}
	
	public void setCreationDate(TheDate newCreationDate)
	{
		creationDate = newCreationDate;
	}
	
	public void setStartingDate(TheDate newStartingDate)
	{
		startingDate = newStartingDate;
	}
	
	public void setBudget(double newBudget)
	{
		budget = newBudget;
	}
	
	/**
	 * Operations
	 */
	public void addSubSystem(SubSystem s)
	{
		if(subSystems == null )
			subSystems = new ArrayList<SubSystem>();
		subSystems.add(s);
	}
	
	public List<SubSystem> getAllSubSystem()
	{
		List<SubSystem> list = new ArrayList<SubSystem>();
		for(SubSystem s : subSystems)
			list.addAll(s.getAllSubSystems());

		return list;
	}
	

	@Override
	public Project clone()
	{
		Project p = null;
		try
		{
			p =  (Project) super.clone();
		}
		catch(CloneNotSupportedException | ClassCastException e)
		{
			e.printStackTrace(System.err);
		}
		
		p.name = name;
		p.description = description;
		p.startingDate = startingDate;
		p.budget = budget;
		p.subSystems =  new ArrayList<SubSystem>(subSystems);
		
		return p;
	}
	
	
	/**
	 * Destructor
	 */
	public void destructor()
	{
		if(subSystems != null)
		{
			for(SubSystem s : subSystems)
				s.destructor();
			subSystems = null;
		}
		
		name = null;
		description = null;
		startingDate = null;
		budget = 0.0;
	}



}
