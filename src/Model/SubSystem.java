package Model;

import java.util.ArrayList;
import java.util.List;

public class SubSystem 
{
	private double versionID;
	private String name;
	private String description;
	
	private List<SubSystem> subSystems;
	
	/**
	 * Constructoren
	 */
	public SubSystem()
	{
		this(null,null,1.0);
	}
	
	public SubSystem(String newName, String newDescription)
	{
		this(newName, newDescription,1.0);
	}
	
	public SubSystem(String newName, String newDescription, double newVersionID)
	{
		name = newName;
		description = newDescription;
		versionID = newVersionID;
	}
	
	/**
	 * Getters
	 */
	public double getVersionID()
	{
		return versionID;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public List<SubSystem> getSubSystems()
	{
		return new ArrayList<SubSystem>(subSystems);
	}
	
	/**
	 * Setters
	 */
	public void setVersionID(double newVersionID)
	{
		versionID = newVersionID;
	}
	
	public void setName(String newName)
	{
		name = newName;
	}
	
	public void setDescription(String newDescription)
	{
		description = newDescription;
	}
	
	/**
	 * Operations
	 */
	public void addSubSystem(SubSystem newSubSystem) throws Exception
	{
		if(subSystems == null)
			subSystems = new ArrayList<SubSystem>();
		if(newSubSystem == this) throw new Exception();
		subSystems.add(newSubSystem);
	}
	
	public List<SubSystem> getAllSubSystems()
	{
		List<SubSystem> list = new ArrayList<SubSystem>();
		for(SubSystem s : subSystems)
			list.addAll(s.getAllSubSystems());
		return list;
	}
	
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
