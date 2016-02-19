package Model;


public class SubSystem 
{
	private double versionID;
	private String name;
	private String description;
	
	private List<SubSystem> subSystems;
	
	/**
	 * Constructoren
	 */
	public SubSystem();
	
	/**
	 * Getters
	 */
	public double getVersionID();
	
	public String getName();
	
	public String getDescription();
	
	public List<SubSystem> getSubSystems();
	
	/**
	 * Setters
	 */
	
	public void setVersionID(double newVersionID);
	
	public void setName(String newName);
	
	public void setDescription(String newDescription);
	
	/**
	 * Operations
	 */
	public void addSubSystem(SubSystem newSubSystem);
	
	public List<SubSystem> getAllSubSystems();
	
	/**
	 * Destructor
	 */
	public void destructor();
	
	
}
