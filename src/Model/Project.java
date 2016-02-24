package Model;
import java.util.ArrayList;
import java.util.List;

public class Project
{

	private String name;
	private String description;
	private TheDate creationDate;
	private TheDate startingDate;
	private double budget;
	//versionID niet gebruikt in de use cases -> zelf intern te genereren ? -> hoe ? 
	private double versionID; 
	
	private List<SubSystem> subSystems = new ArrayList<SubSystem>();
	
	//leadDeveloper todo later
	
	/**
	 * Constructoren
	*/
	
	/**
	 * 
	 * Construct a new instance of Project with default values
	 * Note: probably 'll never be used
	 */
	public Project()
	{
		this(null,null,0.0);
	}
	
	/**
	 * Construct a new instance of Project with the given name and description
	 * @param newName The name of the project
	 * @param newDescription The description of the project
	 */
	public Project(String newName, String newDescription)
	{
		this(newName, newDescription,0.0);
	}
	
	//Init value for TheDate? -> We don't want to directly use TheDate type, therefore we pass the parameters day, month and year but initializing the instance ...
	/**
	 * Construct a new instance of Project with the given name and description
	 * @param newName The name of the project
	 * @param newDescription The description of the project
	 * @param newBudget The budget of the project
	 */
	public Project(String newName, String newDescription, double newBudget)
	{
		this.name = newName;
		this.description = newDescription;
		this.budget = newBudget;
	}
	
	/**
	 * Getters
	 */
	
	/**
	 * Returns the name of the project
	 * @return The name of the project
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Returns the description of the project
	 * @return The description of the project
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * Returns the creation date of the project
	 * @return The creation date of the project
	 */
	public String getCreationDate()
	{
		return creationDate.toString();
	}
	
	//Intern gebruik maken van TheDate type, afschermen op hoger niveau door strings te gebruiken
	/**
	 * Returns the starting date of the project
	 * @return The starting date of the project
	 */
	public String getStartingDate()
	{
		return startingDate.toString();
	}
	
	/**
	 * Returns the budget of the project
	 * @return The budget of the project
	 */
	public double getBudget()
	{
		return budget;
	}
	
	/**
	 * Returns the version ID of the project
	 * @return The version ID of the project
	 */
	public double getVersionID()
	{
		return versionID;
	}
	
	/**
	 * Returns the subsystems of the project
	 * @return The subsystems of the project
	 */
	public List<SubSystem> getSubSystems()
	{
		return new ArrayList<SubSystem>(subSystems);
	}
	
	/**
	 * Setters
	 */
	
	/**
	 * Set the name of the project
	 * @param newName The name of the project
	 */
	public void setName(String newName)
	{
		if(newName == null) throw new NullPointerException("The given name cannot be null.");
		
		name = newName;
	}
	
	/**
	 * Set the description of the project
	 * @param newDescription The description of the project
	 */
	public void setDescription(String newDescription)
	{
		if(newDescription == null)  throw new NullPointerException("The given description cannot be null.");
		
		description = newDescription;
	}
	
	/**
	 * Set the creation date of the project
	 * @param day The day of creation
	 * @param month The month of creation
	 * @param year The year of creation
	 */
	public void setCreationDate(int day, int month, int year)
	{
	
		creationDate = new TheDate(day,month,year);
	}
	
	/**
	 * Set the starting date of the project
	 * @param day The day of start
	 * @param month The month of start
	 * @param year The year of start
	 */
	public void setStartingDate(int day, int month, int year)
	{
		startingDate = new TheDate(day,month,year);
	}
	
	/**
	 * Set the budget of the project
	 * @param newBudget The budget of the project
	 */
	public void setBudget(double newBudget)
	{
		budget = newBudget;
	}
	
	/**
	 * Set the version ID of the project
	 * @param newVersionID The version ID of the project
	 */
	public void setVersionID(double newVersionID)
	{
		versionID = newVersionID;
	}
	
	/**
	 * Operations
	 */
	
	/**
	 * Add a subsystem to this project
	 * @param s The subsystem to add
	 */
	public void addSubSystem(SubSystem s)
	{
		
		if(subSystems == null) throw new NullPointerException();
		
		subSystems.add(s);
//		if(subSystems == null )
//			subSystems = new ArrayList<SubSystem>();
//		subSystems.add(s);
	}
	
	/**
	 * Get recursively all the subsystems of the project
	 * @return A list of subsystems
	 */
	//COPY OF getubsystems()
	public List<SubSystem> getAllSubSystem()
	{
		List<SubSystem> list = new ArrayList<SubSystem>();
		for(SubSystem s : subSystems)
			list.addAll(s.getAllSubSystems());

		return list;
	}
	

	/**
	 * Clone a project
	 * @return A cloned project
	 */
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
	
	/**
	 * Destroy the project
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
