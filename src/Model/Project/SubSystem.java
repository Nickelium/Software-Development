package Model.Project;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.HealthIndicator.IHealthIndicator;
import Model.HealthIndicator.IHealthIndicatorAndBugImpact;
import Model.Mail.Observer;
import Model.Mail.Subject;
import Model.Memento.Memento;
import Model.Memento.Originator;
import Model.Milestone.Milestone;
import Model.Milestone.MilestoneContainer;
import Model.Milestone.SetMilestoneHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * This class represents a subsystem with all its related attributes.
 */
public class SubSystem extends Subject implements Observer, Originator<SubSystem.SubSystemMemento, SubSystem>, MilestoneContainer, IHealthIndicatorAndBugImpact {

    private String name;
    private String description;
    private double versionID = 1.0;

    private List<SubSystem> subSystems = new ArrayList<>();
    private List<BugReport> bugReports = new ArrayList<>();
    private List<Milestone> milestones = new ArrayList<>();
    private Milestone latestAchievedMilestone = null;

    //region Constructor

    /**
     * Construct a new instance subsystem with given name and description
     *
     * @param name        The name of the project
     * @param description The description of the project
     * @throws ReportErrorToUserException The name or description is not valid. (see attribute setters for rules)
     */
    SubSystem(String name, String description) throws ReportErrorToUserException {
        this.setName(name);
        this.setDescription(description);
        this.latestAchievedMilestone = new Milestone();
    }

    //endregion

    //region Getters

    /**
     * Getter to request the latest achieved milestone of the subsystem.
     *
     * @return The latest achieved milestone of the subsystem
     */
    public Milestone getLatestAchievedMilestone(){
        return this.latestAchievedMilestone;
    }

    /**
     * Getter to request the versionId of the subsystem.
     *
     * @return The version ID of the subsystem
     */
    public double getVersionID() {
        return versionID;
    }

    /**
     * Getter to request the name of the subsystem.
     *
     * @return The name of the subsystem
     */
    public String getName() {
        return name;
    }

    /**
     * Getter to request the description of the subsystem.
     *
     * @return The description of the subsystem
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter to request the subsystems list of this subsystem
     *
     * @return The subsystems of this subsystem
     */
    public List<SubSystem> getSubSystems() {
        return Collections.unmodifiableList(subSystems);
    }

    /**
     * Method that returns a list of all milestones added to the subsystem and all
     * the subsystems that it (recursively) contains.
     *
     * @return an unmodifiable list of all the milestones
     */
    public List<Milestone> getAllMilestones() {
        List<Milestone> milestones = new ArrayList<>();
        milestones.addAll(this.getCurrentSubsystemMilestones());
        for (SubSystem subsystem : this.getAllSubSystems()) {
            milestones.addAll(subsystem.getCurrentSubsystemMilestones());
        }
        return new ArrayList<>(new LinkedHashSet<>(milestones));
    }

    /**
     * Method that returns a list of the milestones added to this subsystem,
     * excluding the latest achieved milestone
     *
     * @return the list of milestones added to this subsystem, excluding the latest achieved milestone.
     */
    public List<Milestone> getCurrentSubsystemMilestones() {
        return Collections.unmodifiableList(this.milestones);
    }

    /**
     * Getter to request the bugreports of the current subsystem.
     *
     * @return A list of the bugreports of the current subsystem.
     */
    public List<BugReport> getBugReports() {
        return this.bugReports;
    }

    /**
     * Getter to request all subsystems of this subsystem.
     *
     * @return An unmodifiable list of the subsystems of this subsystem. (recursively)
     */
    public List<SubSystem> getAllSubSystems() {
        List<SubSystem> list = new ArrayList<SubSystem>();
        for (SubSystem s : subSystems) {
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
    public List<BugReport> getAllBugReports() {
        List<BugReport> bugReports = new ArrayList<>();
        bugReports.addAll(this.bugReports);
        for (SubSystem subsystem : this.getAllSubSystems()) {
            bugReports.addAll(subsystem.getAllBugReports());
        }
        return Collections.unmodifiableList(bugReports);
    }

    /**
     * Method to get the height of this node
     * 
     * @return The height of this subsystem
     */
    public int getHeight()
    {
    	int max = 0;
		for(SubSystem sub : subSystems)
			if(max < sub.getHeight()) 
				max = sub.getHeight();
		return max + 1;
    }

    /**
     * Method returning a list of all health indicator components of a subsystem.
     * @return a list of all health indicator components of a subsystem
     */
    public List<IHealthIndicator> getDirectHealthIndicatorComponents() {
        return Collections.unmodifiableList(this.subSystems);
    }

    /**
     * Method returning the bug impact of the subsystem
     * The bug impact is equal to the summation of (Multiplier(br) * ImpactFactor(br)), for each bug report br.
     *
     * @return the bug impact of the subsystem
     */
    public double getBugImpact() {
        double impact = 0.0;

        for (BugReport bugReport : getBugReports()) {
            impact += bugReport.getBugReportImpact();
        }

        return impact;
    }
    
    //endregion

    //region Setters



    // The code below has been refactored, but will be saved for future reference.

//	/**
//	 * Setter to set the versionId of the subsystem.
//     *
//	 * @param versionID The versionID of the subsystem.
//     *
//	 * @throws ReportErrorToUserException The current versionid is greater than or equal to the new one.
//	 */
//	private void setVersionID(double versionID) throws ReportErrorToUserException
//	{
//		if (!isValidVersionID(versionID)) throw new ReportErrorToUserException("The versionId must be higher than the current one.");
//		this.versionID = versionID;
//	}

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
     * Setter to set the name of the subsystem.
     *
     * @param name The name of the subsystem
     * @throws ReportErrorToUserException The given name is empty.
     */
    void setName(String name) throws ReportErrorToUserException {
        if (!isValidName(name)) throw new ReportErrorToUserException("The give name is empty.");
        this.name = name;
    }

    /**
     * Setter to set the description of the subsystem.
     *
     * @param description The description of the subsystem.
     * @throws ReportErrorToUserException The given description is empty.
     */
    void setDescription(String description) throws ReportErrorToUserException {
        if (!isValidDescription(description)) throw new ReportErrorToUserException("The given description is empty.");
        this.description = description;
    }

    /**
     * Method to set a new subsystem milestone.
     * <p>
     * There occurs consistency checking:
     * first pass: subsystem milestone should not exceed any recursive subsystem's milestone
     * second pass: subsystem milestone should not exceed the target milestone of
     * any related bug report with a non-final tag.
     *
     * @param newSubsystemMilestone the new subsystem milestone that has to be set
     * @throws ReportErrorToUserException is thrown in case that a constraint is broken.
     */
    void setNewSubSystemMilestone(Milestone newSubsystemMilestone) throws ReportErrorToUserException {
        if (!SetMilestoneHelper.mileStoneIsBiggerThanCurrent(this, newSubsystemMilestone))
            throw new ReportErrorToUserException("The new milestone is smaller than the current one");
        if (!SetMilestoneHelper.milestoneDoesNotExceedSubsystemMilestone(this, newSubsystemMilestone))
            throw new ReportErrorToUserException("The new milestone exceeds milestone of subsystem!");
        if (!SetMilestoneHelper.milestoneDoesNotExceedBugReportMilestone(this, newSubsystemMilestone))
            throw new ReportErrorToUserException("The new milestone exceeds the milestone of the projects target bug report!");

        setLatestAchievedMilestone(newSubsystemMilestone);
        addMilestoneToList(newSubsystemMilestone);
    }

    /**
     * Method to set the latest achieved milestone to a new value.
     *
     * @param latestAchievedMilestone the new milestone to be set as the latest achieved milestone
     * 
     * @throws IllegalArgumentException latestAchievedMilestone is null
     */
    private void setLatestAchievedMilestone(Milestone latestAchievedMilestone) {
    	if(latestAchievedMilestone == null) throw new IllegalArgumentException("The milestone cannot be negative");
        this.latestAchievedMilestone = latestAchievedMilestone;
        notifyObservers(this, latestAchievedMilestone);
    }

    //endregion

    //region Checkers

    /**
     * Checker to check if the name is valid.
     *
     * @param name the name to check.
     * @return True if the name is not empty.
     */
    public boolean isValidName(String name) {
        if (name == null) return false;
        if (name.equals("")) return false;
        else return true;
    }

    /**
     * Checker to check if the description is valid.
     *
     * @param description The description is valid.
     * @return True if the description is not empty.
     */
    public boolean isValidDescription(String description) {
        if (description == null) return false;
        if (description.equals("")) return false;
        else return true;
    }

    /**
     * Checker to check if the subsystem is a valid subsystem.
     *
     * @param subSystem The subsystem to check.
     * @return True if the subsystem is not already a subsystem of this subsystem.
     */
    public boolean isValidSubsystem(SubSystem subSystem) {
        if (this == subSystem) return false;
        if (this.getAllSubSystems().contains(subSystem)) return false;
        else return true;
    }

    /**
     * Checker to check if the bug report is a valid bug report.
     *
     * @param bugReport The bug report to check.
     * @return True if the bug report is not already a bug report of this subsystem or recursively.
     */
    public boolean isValidBugReport(BugReport bugReport) {
    	if(bugReport == null) return false;
        if (this.getAllBugReports().contains(bugReport)) return false;
        else return true;
    }

    //endregion

    // region Operations

    /**
     * Method for adding a subsystem to the list of subsystems.
     *
     * @param subSystem The subsystem to add.
     * @throws IllegalArgumentException   The given subsystem is null.
     * @throws ReportErrorToUserException The subsystem is not a valid subsystem.
     */
    public void addSubSystem(SubSystem subSystem) throws ReportErrorToUserException {
        if (subSystem == null) throw new IllegalArgumentException("Subsystem is null");
        if (!isValidSubsystem(subSystem)) throw new ReportErrorToUserException("The subsystem cannot be added!");
        subSystems.add(subSystem);
        subSystem.addObserver(this);
    }
    
    /**
     * Method to check if the given subsystem is a direct child of this subsystem
     * 
     * @param subsystem The subsystem to check
     * 
     * @return Return whether the given subsystem is a direct child of this object or not
     */
    public boolean isParent(SubSystem subsystem)
    {
    	if(subsystem == null) return false;
    	if(getSubSystems().contains(subsystem)) return true;
    	return false;
    }
    
    /**
	 * Method to remove a subsystem
	 * 
	 * @param subSystem The subsystem to remove.
     *
     * @throws IllegalArgumentException The given subsystem is null.
	 */
	void removeSubSystem(SubSystem subSystem)
	{
		if(subSystem == null) throw new IllegalArgumentException("Subsystem is null");
		if(subSystems.contains(subSystem)) 
		{
			subSystems.remove(subSystem);
			//unbind
			subSystem.removeObserver(this);
		}
		else
			for(SubSystem subSystemChild : subSystems)
				subSystemChild.removeSubSystem(subSystem);
	}

    /**
     * Method for adding a bug report to the list of bugreports.
     *
     * @param bugReport The bug report to add.
     * @throws IllegalArgumentException   The given bug report is null.
     * @throws ReportErrorToUserException The bug report is not a valid bug report
     */
    public void addBugReport(BugReport bugReport) throws ReportErrorToUserException {
        if (bugReport == null) throw new IllegalArgumentException("Bugreport is null");
        if (!isValidBugReport(bugReport)) throw new ReportErrorToUserException("The bug report cannot be added!");
        bugReports.add(bugReport);

        bugReport.addObserver(this);
        notifyObservers(bugReport, bugReport);
    }

    /**
     * Method to fork a subsystem.
     *
     * @return The forked subsystem.
     * @throws ReportErrorToUserException One of the attributes of the subsystem could not be forked.
     */
    //fork != clone
    SubSystem fork() throws ReportErrorToUserException {
        SubSystem forkedSubSystem = new SubSystem(name, description);
        forkedSubSystem.versionID = versionID;

        for (SubSystem subsystem : subSystems)
            forkedSubSystem.subSystems.add(subsystem.fork());

        //no bugreports

        return forkedSubSystem;
    }

    /**
     * Method to add an old milestone to the list of milestones.
     * @param milestone the old milestone to be add to the milestone list.
     * 
     * @throws IllegalArgumentException milestone is null
     */
    private void addMilestoneToList(Milestone milestone) {
    	if(milestone == null) throw new IllegalArgumentException("The milestone cannot be null");
    	
        this.milestones.add(milestone);
        Collections.sort(this.milestones);
    }

    /**
     * Method to represent a subsystem as a string.
     *
     * @return The subsystem as a string.
     */
    @Override
    public String toString() {
        return "Subsystem name: " + getName() + "\nDescription: " + getDescription()
                + "\nVersionID: " + getVersionID()
                + "\nMilestone: " + this.getLatestAchievedMilestone();
    }

    //endregion

    //region Memento functions

    /**
     * Method called to notify this observer
     *
     * @param structure The structure 
     * @param subject      The subject
     * @param aspect The aspect that has changed
     * 
     * @throws IllegalArgumentException The subject, structure or aspect is null.
     */
    @Override
    public void update(Subject structure, Subject subject, Object aspect)
    {
    	if(structure == null) throw new IllegalArgumentException("The structure cannot be null");
		if(subject == null) throw new IllegalArgumentException("The subject cannot be null");
		if(aspect == null) throw new IllegalArgumentException("The aspect cannot be null");
        notifyObservers(subject, aspect);
    }

    /**
     * Method to create a memento of this object
     * 
     * @return The memento of this object
     */
	@Override
	public SubSystemMemento createMemento() 
	{
		return new SubSystemMemento(this);
	}

	/**
	 * Method to restore this object given the memento
	 * 
	 * @param memento The memento to restore to
	 * 
	 * @throws IllegalArgumentException the memento is null
	 */
	@Override
	public void restoreMemento(SubSystemMemento memento) 
	{
		if(memento == null) throw new IllegalArgumentException("The memento cannot be null");
		
		this.name = memento.getName();
		this.description = memento.getDescription();
		
		this.subSystems = memento.getSubSystems();
		
		for(SubSystemMemento subsystemMemento : memento.getSubSystemMementos())
			subsystemMemento.getOriginator().restoreMemento(subsystemMemento);
		
		this.bugReports = memento.getBugReports();
		
		for(BugReport.BugReportMemento bugreportMemento : memento.getBugReportMementos())
			bugreportMemento.getOriginator().restoreMemento(bugreportMemento);
		
		this.latestAchievedMilestone = memento.getLatestAchievedMilestone();
		this.milestones = memento.getMilestones();

    }

    //endregion

    //region Innerclass Memento

	 /**
    * This class provides utility for saving the state of the system at a certain point in time
    * during execution of the Bug Trap software.
    *
    * The subsystem memento saves the state of the following attributes of the subsystem:
    * subsystems, bugreports, latestAchievedMilestone, milestones.
    *
    * This class provides private methods to request the values of the saved fields.
    * This wide interface (private getters + public constructor) is provided to the class ProjectService,
    * while the narrow interface (public constructor) is provided to any class.
    */
	public class SubSystemMemento extends Memento<SubSystem>
	{
		private String name;
		private String description;
		private List<SubSystem> subsystems;
		private List<SubSystemMemento> subsystemMementos = new ArrayList<>();
		
		private List<BugReport> bugreports;
		private List<BugReport.BugReportMemento> bugreportMementos = new ArrayList<>();
		
		private Milestone latestAchievedMilestone;
		private List<Milestone> milestones;
		
		/**
    	 * Constructor 
    	 * 
    	 * @param originator The originator to build a memento from
    	 * 
    	 * @throws IllegalArgumentException the originator is null
    	 */
		public SubSystemMemento(SubSystem originator)
		{
			super(originator);
			this.name = originator.getName();
			this.description = originator.getDescription();
			this.subsystems =  new ArrayList<>(originator.getSubSystems());
			for(SubSystem subsystem : subsystems)
				subsystemMementos.add(subsystem.createMemento());
			
			this.bugreports =  new ArrayList<>(originator.getBugReports());
			for(BugReport bugReport : bugreports)
				bugreportMementos.add(bugReport.createMemento());
			
			this.latestAchievedMilestone = originator.getLatestAchievedMilestone();
			this.milestones =  new ArrayList<>(originator.milestones);
			
		}
		
		private String getName()
		{
			return name;
		}
		
		private String getDescription()
		{
			return description;
		}
		
		private List<SubSystem> getSubSystems()
		{
			return new ArrayList<>(subsystems);
		}
		
		private List<SubSystemMemento> getSubSystemMementos()
		{
			return subsystemMementos;
		}
		
		private List<BugReport> getBugReports()
		{
			return new ArrayList<>(bugreports);
		}
		
		private List<BugReport.BugReportMemento> getBugReportMementos()
		{
			return bugreportMementos;
		}
		
		private Milestone getLatestAchievedMilestone()
		{
			return latestAchievedMilestone;
		}
		
		private List<Milestone> getMilestones()
		{
			return new ArrayList<>(milestones);
		}

    }

    //endregion
}
