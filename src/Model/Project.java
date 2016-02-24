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
		
		this.creationDate = TheDate.TheDateNow();
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
	public TheDate getCreationDate()
	{
		return creationDate;
	}
	
	//Intern gebruik maken van TheDate type, afschermen op hoger niveau door strings te gebruiken
	/**
	 * Returns the starting date of the project
	 * @return The starting date of the project
	 */
	public TheDate getStartingDate()
	{
		return startingDate;
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
		
		if(s == null) throw new NullPointerException("The given subsystem cannot be null.");
		
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
		{
			list.add(s);
			list.addAll(s.getAllSubSystems());
		}
		
		return list;
	}
	

	/**
	 * Clone a project
	 * @return A cloned project
	 */
	public Project clone()
	{
		Project p = new Project(name,description);
		
		p.creationDate = creationDate;
		p.startingDate = startingDate;
		p.budget = budget;
		p.subSystems =  new ArrayList<SubSystem>(subSystems);
		
		return p;
	}


}
