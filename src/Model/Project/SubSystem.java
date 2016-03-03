package Model.Project;

import java.util.ArrayList;
import java.util.List;

import Model.BugReport.BugReport;

public class SubSystem 
{

	private String name;
	private String description;
	//Same as project, how to handle versionID ?
	private double versionID;
	
	private List<SubSystem> subSystems = new ArrayList<>();
	private List<BugReport> bugReports = new ArrayList<>();
	
	/**
	 * Constructoren
	 */
	
	public SubSystem(String newName)
	{
		this(newName, "");
	}
	/**
	 * Construct a new instance subsystem with given name and description
	 * @param newName The name of the project
	 * @param newDescription The description of the project
	 */
	public SubSystem(String newName, String newDescription)
	{
		this(newName, newDescription,1.0);
	}
	
	/**
	 * Construct a new instance subsystem with given name, description and versionID
	 * @param newName The name of the subsystem
	 * @param newDescription The description of the subsystem
	 * @param newVersionID The version ID of the subsystem
	 */
	public SubSystem(String newName, String newDescription, double newVersionID)
	{
		name = newName;
		description = newDescription;
		versionID = newVersionID;
	}
	
	/**
	 * Getters
	 */
	
	/**
	 * Return the version ID of the subsystem
	 * @return The version ID of the subsystem
	 */
	public double getVersionID()
	{
		return versionID;
	}
	
	/**
	 * Return the name of the subsystem
	 * @return The name of the subsystem
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Returns the description of the subsystem
	 * @return The description of the subsystem
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * Returns the subsystems of the subsystem
	 * @return A list of subsystems
	 */
	public List<SubSystem> getSubSystems()
	{
		return new ArrayList<SubSystem>(subSystems);
	}
	
	public List<BugReport> getBugReports()
	{
		return new ArrayList<BugReport>(bugReports);
	}
	
	/**
	 * Setters
	 */
	
	/**
	 * Returns the versionID of the subsystem
	 * @param newVersionID The versionID of the subsystem
	 * @throws Exception 
	 */
	public void setVersionID(double newVersionID) throws Exception
	{
		if(newVersionID < 0) throw new Exception("The versionID cannot be negative");
		versionID = newVersionID;
	}
	
	/**
	 * Returns the name of the subsystem
	 * @param newName The name of the subsystem
	 * @throws Exception 
	 */
	public void setName(String newName) throws Exception
	{
		if(newName == null) throw new Exception("The given name cannot be null.");
		name = newName;
	}
	
	/**
	 * Returns the description of the subsystem
	 * @param newDescription The description of the subystem
	 * @throws Exception 
	 */
	public void setDescription(String newDescription) throws Exception
	{
		if(newDescription == null) throw new Exception("The given description cannot be null.");
		description = newDescription;
	}
	
	/**
	 * Operations
	 */
	
	/**
	 * Add a subsystem to this subsystem
	 * @param newSubSystem The subsystem to add
	 * @throws Exception
	 */
	public void addSubSystem(SubSystem newSubSystem) throws Exception
	{
		if(newSubSystem == null) throw new NullPointerException("The given subsystem cannot be null.");
//		if(subSystems == null)
//			subSystems = new ArrayList<SubSystem>();
		
		/* QUESTIONS
		 * As stated in the assignement, "subsystems cannot be recursively part of itself", 
		 * if the versionID is self-generated, then no subsystem can ever be the same if equals is defined as :
		 * a.name == b.name && a.versionID == b.versionID
		 * due to the fact we generate the versionID in accounting of previous versionID's
		 * In this case, upon creation of a new subsystem, the new subsystem can never been created before, therefore a recursive check is redundant
		 * In the first iteration, projects cannot share subsystems, because the creation of a subsystem is immediately added to a certain project.
		 * Therefore, another project cannot contain it, unless moving pointer of the subsystem around.
		 * 
		 * If equality is defined as :
		 * a.name == b.name
		 * then we need a more profound check, where not only children of the subsystems needs to be check but maybe also it parents (if added directly to the subsystem without going through the project parent)
		 * Shared subsystems check need also to be done, in this case.
		 * 
		 * NOTE: The client doesn't seems to indicate the versionID, should we self-generate these versionID's then ?
		 * 
		 */
		if(newSubSystem == this) throw new Exception("The subsystem cannot add himself to his list of subsystems.");
		subSystems.add(newSubSystem);
	}
	
	public void addBugReport(BugReport newBugReport)
	{
		if(newBugReport == null) throw new NullPointerException("The given bugreport cannot be null.");
		bugReports.add(newBugReport);
	}
	
	/**
	 * Returns recursively all subsystems
	 * @return A list of subsystems
	 */
	public List<SubSystem> getAllSubSystems()
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
	 * Clone the subsystem
	 * @return The cloned subsystem
	 */
	public SubSystem clone()
	{
		SubSystem s = new SubSystem(name,description);
		
		s.versionID = versionID;
		if(subSystems != null) s.subSystems =  new ArrayList<SubSystem>(subSystems);
		
		return s;
	}
	/*
	public void destructor()
	{
		name = null;
		description = null;
		versionID = 0.0;

		subSystems = null;
		bugReports = null;
		
	}
	*/
	
	
}
