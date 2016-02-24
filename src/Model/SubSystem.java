package Model;

import java.util.ArrayList;
import java.util.List;

public class SubSystem 
{

	private String name;
	private String description;
	//Same as project, how to handle versionID ?
	private double versionID;
	
	private List<SubSystem> subSystems = new ArrayList<SubSystem>();
	
	/**
	 * Constructoren
	 */
	
	/**
	 * Construct a new instance subsystem with default values
	 * Note: Probably only for testing
	 */
	public SubSystem()
	{
		this(null,null,1.0);
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
	
	/**
	 * Setters
	 */
	
	/**
	 * Returns the versionID of the subsystem
	 * @param newVersionID The versionID of the subsystem
	 */
	public void setVersionID(double newVersionID)
	{
		versionID = newVersionID;
	}
	
	/**
	 * Returns the name of the subsystem
	 * @param newName The name of the subsystem
	 */
	public void setName(String newName)
	{
		if(newName == null) throw new NullPointerException("The given name cannot be null.");
		name = newName;
	}
	
	/**
	 * Returns the description of the subsystem
	 * @param newDescription The description of the subystem
	 */
	public void setDescription(String newDescription)
	{
		if(newDescription == null) throw new NullPointerException("The given description cannot be null.");
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
	
	/**
	 * Returns recursively all subsystems
	 * @return A list of subsystems
	 */
	public List<SubSystem> getAllSubSystems()
	{
		List<SubSystem> list = new ArrayList<SubSystem>();
		for(SubSystem s : subSystems)
			list.addAll(s.getAllSubSystems());
		return list;
	}
	
	/**
	 * Clone the subsystem
	 * @return The cloned subsystem
	 */
	public SubSystem clone()
	{
		SubSystem s = null;
		try
		{
			s =  (SubSystem) super.clone();
		}
		catch(CloneNotSupportedException | ClassCastException e)
		{
			e.printStackTrace(System.err);
		}
		
		s.versionID = versionID;
		s.name = name;
		s.description = description;
		s.subSystems =  new ArrayList<SubSystem>(subSystems);
		
		return s;
	}
	
	/**
	 * Destructor
	 */
	
	/**
	 * Destroy the subsytem
	 */
	public void destructor()
	{
		if(subSystems != null)
		{
			for(SubSystem s : subSystems)
				s.destructor();
			subSystems = null;
		}
		
		versionID = 0.0;
		name = null;
		description = null;
		
	}
	
	
}
