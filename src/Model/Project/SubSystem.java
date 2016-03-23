package Model.Project;

import CustomExceptions.ModelException;
import Model.BugReport.BugReport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represent a subsystem with all it's related attributes.
 *
 */
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
	 * Getter to request the subsystems list of this subsystem
	 * 
	 * @return The subsystems of this subsystem
	 */
	public List<SubSystem> getSubSystems()
	{
		return Collections.unmodifiableList(subSystems);
	}
	/**
	 * Setters
	 */
	
//	/**
//	 * Setter to set the versionId of the subsystem.
//     *
//	 * @param versionID The versionID of the subsystem.
//     *
//	 * @throws ModelException The current versionid is greater than or equal to the new one.
//	 */
//	private void setVersionID(double versionID) throws ModelException
//	{
//		if (!isValidVersionID(versionID)) throw new ModelException("The versionId must be higher than the current one.");
//		this.versionID = versionID;
//	}
	
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

//    /**
//     * Checker to check if the versionID is valid.
//     *
//     * @param versionID The versionId to check.
//     *
//     * @return True if the versionId is greater than or equal to the current versionId.
//     */
//    private boolean isValidVersionID(double versionID){
//        if (this.versionID >= versionID) return false;
//        else return true;
//    }

    /**
     * Checker to check if the name is valid.
     *
     * @param name the name to check.
     *
     * @return True if the name is not empty.
     */
    private boolean isValidName(String name){
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
    private boolean isValidDescription(String description){
        if (description == null) return false;
        if (description.equals("")) return false;
        else return true;
    }

	/**
	 * Checker to check if the subsystem is a valid subsystem.
	 *
	 * @param subSystem The subsystem to check.
	 *
	 * @return True if the subsystem is not already a subsystem of this subsystem.
     */
    private boolean isValidSubsystem(SubSystem subSystem){
    	if(this == subSystem) return false;
		if(this.getAllSubSystems().contains(subSystem)) return false;
		else return true;
	}
    
	/**
	 * Checker to check if the bugreport is a valid bugreport.
	 *
	 * @param bugreport The bugreport to check.
	 *
	 * @return True if the bugreport is not already a bugreport of this subsystem or recursively.
     */
    private boolean isValidBugReport(BugReport bugReport){
		if(this.getAllBugReports().contains(bugReport)) return false;
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
	 * @throws ModelException The subsystem is not a valid subsystem.
	 */
	public void addSubSystem(SubSystem subSystem) throws ModelException
	{
		if(subSystem == null) throw new IllegalArgumentException("Subsystem is null");
		if(!isValidSubsystem(subSystem)) throw new ModelException("The subsystem cannot be added!");
		subSystems.add(subSystem);
	}

    /**
     * Method for adding a bugreport to the list of bugreports.
     *
     * @param bugReport The bugreport to add.
     *
     * @throws IllegalArgumentException The given bugreport is null.
     * @throws ModelException The bugreport is not a valid bugreport
     */
	public void addBugReport(BugReport bugReport) throws ModelException
	{
		if(bugReport == null) throw new IllegalArgumentException("Bugreport is null");
		if(!isValidBugReport(bugReport)) throw new ModelException("The bugreport cannot be added!");
		bugReports.add(bugReport);
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
        bugReports.addAll(this.bugReports);
        for (SubSystem subsystem: this.getAllSubSystems()){
            bugReports.addAll(subsystem.getAllBugReports());
        }
        return Collections.unmodifiableList(bugReports);
    }

    /**
     * Getter to request hte bugreports of the current subsystem.
     *
     * @return A list of the bugreports of the current subsystem.
     */
    List<BugReport> getBugReports(){
        return this.bugReports;
    }
    
    /**
	 * Method to fork a subsystem.
	 * 
	 * @return The forked subsystem.
	 * 
	 * @throws ModelException One of the attributes of the subsystem could not be forked.
	 */
    //fork != clone
    public SubSystem fork() throws ModelException
    {
    	SubSystem forkedSubSystem = new SubSystem(name,description);
    	forkedSubSystem.versionID = versionID;
    	
    	for(SubSystem subsystem : subSystems )
    		forkedSubSystem.subSystems.add(subsystem.fork());
    	
    	//no bugreports
    	
    	return forkedSubSystem;
    }
    
    /**
	 * Method to represent a subsystem as a string.
	 * 
	 * @return The subsystem as a string.
	 */
    @Override
    public String toString()
    {
    	return "Subsystem name: " + getName() + "\nDescription: " + getDescription() 
    			+ "\nVersionID: " + getVersionID(); 
    }
}
