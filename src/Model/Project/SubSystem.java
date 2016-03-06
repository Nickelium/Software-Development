package Model.Project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import CustomExceptions.ModelException;
import Model.BugReport.BugReport;

public class SubSystem 
{

	private String name;
	private String description;
	private double versionID = 1.0;
	
	private List<SubSystem> subSystems = new ArrayList<>();
	private List<BugReport> bugReports = new ArrayList<>();
	
	/**
	 * Constructoren
	 */

	/**
	 * Construct a new instance subsystem with given name and description
	 * @param name The name of the project
	 * @param description The description of the project
     *
     * @throws ModelException The name or description is not valid. (see attribute setters for rules)
	 */
	public SubSystem(String name, String description) throws ModelException
	{
		this.setName(name);
		this.setDescription(description);
	}
	
	/**
	 * Getters
	 */
	
	/**
	 * Getter to request the versionId of the subsystem.
	 *
     * @return The version ID of the subsystem
	 */
	public double getVersionID()
	{
		return versionID;
	}
	
	/**
	 * Getter to request the name of the subsystem.
     *
	 * @return The name of the subsystem
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Getter to request the description of the subsystem.
     *
	 * @return The description of the subsystem
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * Setters
	 */
	
	/**
	 * Setter to set the versionId of the subsystem.
     *
	 * @param versionID The versionID of the subsystem.
     *
	 * @throws ModelException The current versionid is greater than or equal to the new one.
	 */
	public void setVersionID(double versionID) throws ModelException
	{
		if (!isValidVersionID(versionID)) throw new ModelException("The versionId must be higher than the current one.");
		this.versionID = versionID;
	}
	
	/**
	 * Setter to set the name of the subsystem.
     *
	 * @param name The name of the subsystem
     *
	 * @throws ModelException The given name is empty.
	 */
	public void setName(String name) throws ModelException
	{
		if (!isValidName(name)) throw new ModelException("The give name is empty.");
	    this.name = name;
	}
	
	/**
	 * Setter to set the description of the subsystem.
     *
	 * @param description The description of the subsystem.
     *
	 * @throws ModelException The given description is empty.
	 */
	public void setDescription(String description) throws ModelException
	{
		if (!isValidDescription(description)) throw new ModelException("The given description is empty.");
		this.description = description;
	}

    /**
     * Checker to check if the versionID is valid.
     *
     * @param versionID The versionId to check.
     *
     * @return True if the versionId is greater than or equal to the current versionId.
     */
    public boolean isValidVersionID(double versionID){
        if (this.versionID >= versionID) return false;
        else return true;
    }

    /**
     * Checker to check if the name is valid.
     *
     * @param name the name to check.
     *
     * @return True if the name is not empty.
     */
    public boolean isValidName(String name){
        if (name == null) return false;
        if (name.equals("")) return false;
        else return true;
    }

    /**
     * Checker to check if the description is valid.
     *
     * @param description The description is valid.
     *
     * @return True if the description is not empty.
     */
    public boolean isValidDescription(String description){
        if (description == null) return false;
        else return true;
    }

	/**
	 * Operations
	 */
	
	/**
	 * Method for adding a subsystem to the list of subsystems.
     *
     * @param subSystem The subsystem to add.
     *
     * @throws IllegalArgumentException The given subsystem is null.
	 */
    public void addSubSystem(SubSystem subSystem)
	{
		if(subSystem == null) throw new IllegalArgumentException("Subsystem is null");
		subSystems.add(subSystem);
	}

    /**
     * Method for adding a bugreport to the list of bugreports.
     *
     * @param bugReport The bugreport to add.
     *
     * @throws IllegalArgumentException The given bugreport is null.
     */
	public void addBugReport(BugReport bugReport)
	{
		if(bugReport == null) throw new IllegalArgumentException("Bugreport is null");
		bugReports.add(bugReport);
	}
	
	public List<SubSystem> getSubSystems()
	{
		List<SubSystem> list = new ArrayList<SubSystem>();
		list = subSystems;
		return Collections.unmodifiableList(list);
	}
	/**
	 * Getter to request all subsystems of this subsystem.
     *
	 * @return An unmodifiable list of the subsystems of this subsystem. (recursively)
	 */
	public List<SubSystem> getAllSubSystems()
	{
		List<SubSystem> list = new ArrayList<SubSystem>();
		for(SubSystem s : subSystems)
		{
			list.add(s);
			list.addAll(s.getAllSubSystems());
		}
		
		return Collections.unmodifiableList(list);
	}

    /**
     * Getter to request all the bugreports of the subsystem.
     *
     * @return An unmodifiable list of all the bugreports of the subsystem. (recursively)
     */
    public List<BugReport> getAllBugReports(){
        List<BugReport> bugReports = new ArrayList<>();
        for (SubSystem subsystem: this.getAllSubSystems()){
            bugReports.addAll(subsystem.getSubsystemBugReports());
        }
        return Collections.unmodifiableList(bugReports);
    }

    /**
     * Getter to request hte bugreports of the current subsystem.
     *
     * @return A list of the bugreports of the current subsystem.
     */
    List<BugReport> getSubsystemBugReports(){
        return this.bugReports;
    }
}
