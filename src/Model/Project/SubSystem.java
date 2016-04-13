package Model.Project;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.Mail.Observer;
import Model.Mail.Subject;
import Model.Memento.Memento;
import Model.Memento.Originator;
import Model.Milestone.Milestone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represent a subsystem with all it's related attributes.
 */
public class SubSystem extends Subject implements Observer<BugReport>, Originator<SubSystem.SubSystemMemento,SubSystem> {

    private String name;
    private String description;
    private double versionID = 1.0;

    private List<SubSystem> subSystems = new ArrayList<>();
    private List<BugReport> bugReports = new ArrayList<>();
    private List<Milestone> milestones = new ArrayList<>();
    private Milestone latestAchievedMilestone = null;

    /**
     * Constructoren
     */

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

    /**
     * Getters
     */


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
     * Setters
     */

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

    /**
     * Setter to set the name of the subsystem.
     *
     * @param name The name of the subsystem
     * @throws ReportErrorToUserException The given name is empty.
     */
    public void setName(String name) throws ReportErrorToUserException {
        if (!isValidName(name)) throw new ReportErrorToUserException("The give name is empty.");
        this.name = name;
    }

    /**
     * Setter to set the description of the subsystem.
     *
     * @param description The description of the subsystem.
     * @throws ReportErrorToUserException The given description is empty.
     */
    public void setDescription(String description) throws ReportErrorToUserException {
        if (!isValidDescription(description)) throw new ReportErrorToUserException("The given description is empty.");
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
     * @return True if the name is not empty.
     */
    private boolean isValidName(String name) {
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
    private boolean isValidDescription(String description) {
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
    private boolean isValidSubsystem(SubSystem subSystem) {
        if (this == subSystem) return false;
        if (this.getAllSubSystems().contains(subSystem)) return false;
        else return true;
    }

    /**
     * Checker to check if the bugreport is a valid bugreport.
     *
     * @param bugReport The bugreport to check.
     * @return True if the bugreport is not already a bugreport of this subsystem or recursively.
     */
    private boolean isValidBugReport(BugReport bugReport) {
        if (this.getAllBugReports().contains(bugReport)) return false;
        else return true;
    }

    /**
     * Operations
     */

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
     * Method for adding a bugreport to the list of bugreports.
     *
     * @param bugReport The bugreport to add.
     * @throws IllegalArgumentException   The given bugreport is null.
     * @throws ReportErrorToUserException The bugreport is not a valid bugreport
     */
    public void addBugReport(BugReport bugReport) throws ReportErrorToUserException {
        if (bugReport == null) throw new IllegalArgumentException("Bugreport is null");
        if (!isValidBugReport(bugReport)) throw new ReportErrorToUserException("The bugreport cannot be added!");
        bugReports.add(bugReport);

        bugReport.addObserver(this);
        notifyObservers(bugReport, bugReport);
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
     * Getter to request the bugreports of the current subsystem.
     *
     * @return A list of the bugreports of the current subsystem.
     */
    List<BugReport> getBugReports() {
        return this.bugReports;
    }

    /**
     * Method to fork a subsystem.
     *
     * @return The forked subsystem.
     * @throws ReportErrorToUserException One of the attributes of the subsystem could not be forked.
     */
    //fork != clone
    public SubSystem fork() throws ReportErrorToUserException {
        SubSystem forkedSubSystem = new SubSystem(name, description);
        forkedSubSystem.versionID = versionID;

        for (SubSystem subsystem : subSystems)
            forkedSubSystem.subSystems.add(subsystem.fork());

        //no bugreports

        return forkedSubSystem;
    }

    public List<Milestone> getAllMilestones() {
        List<Milestone> milestones = new ArrayList<>();
        milestones.add(this.getLatestAchievedMilestone());
        milestones.addAll(this.getMilestones());
        for (SubSystem subsystem : this.getAllSubSystems()) {
            milestones.addAll(subsystem.getMilestones());
        }
        return Collections.unmodifiableList(milestones);
    }

    List<Milestone> getMilestones() {
        return Collections.unmodifiableList(this.milestones);
    }

    public void setNewSubSystemMilestone(Milestone newProjectMilestone) throws ReportErrorToUserException {
        if (!milestoneDoesNotExceedSubsystems(newProjectMilestone))
            throw new ReportErrorToUserException("The new milestone exceeds milestone of subsystem!");
        if (!milestoneDoesNotExceedBugReportMilestone(newProjectMilestone))
            throw new ReportErrorToUserException("The new milestone exceeds the milestone of the projects bugreport!");

        this.setLatestAchievedMilestone(newProjectMilestone);
        this.addMilestoneToList(newProjectMilestone);
        Collections.sort(milestones);
    }

    private boolean milestoneDoesNotExceedSubsystems(Milestone milestone) {
        double max = 0.0;
        List<Milestone> milestones = new ArrayList<>();

        for (SubSystem subSystem : getAllSubSystems()) {
            milestones.addAll(subSystem.getMilestones());
        }

        if (milestones.isEmpty()) return true;

        for (Milestone ms : milestones) {
            if (ms.getIDvalue() > max) {
                max = ms.getIDvalue();
            }
        }

        return milestone.getIDvalue() <= max;
    }

    private boolean milestoneDoesNotExceedBugReportMilestone(Milestone milestone) {
        double max = 0.0;
        List<BugReport> bugReports = this.getAllBugReports();

        if (bugReports.isEmpty()) return true;
        for (BugReport br : bugReports) {
            if (!br.getTag().isFinal() && br.getTargetMilestone() != null && br.getTargetMilestone().getIDvalue() > max) {
                max = br.getTargetMilestone().getIDvalue();
            }
        }

        return milestone.getIDvalue() <= max;
    }

    private void setLatestAchievedMilestone(Milestone latestAchievedMilestone) {
        this.latestAchievedMilestone = latestAchievedMilestone;
    }

    private void addMilestoneToList(Milestone milestone) {
        this.milestones.add(milestone);
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

    /**
     * Method called to notify this observer
     *
     * @param s      The subject
     * @param aspect The aspect that has changed
     */
    @Override
    public void update(Subject s, BugReport bugreport, Object aspect) {
        notifyObservers(bugreport, aspect);

    }

	@Override
	public SubSystemMemento createMemento() 
	{
		return new SubSystemMemento(this);
	}

	@Override
	public void restoreMemento(SubSystemMemento memento) 
	{
		this.subSystems = memento.getSubSystems();
		
		for(SubSystemMemento subsystemMemento : memento.getSubSystemMementos())
			subsystemMemento.getOriginator().restoreMemento(subsystemMemento);
		
		this.bugReports = memento.getBugReports();
		
		for(BugReport.BugReportMemento bugreportMemento : memento.getBugReportMementos())
			bugreportMemento.getOriginator().restoreMemento(bugreportMemento);
		
		this.latestAchievedMilestone = memento.getLatestAchievedMilestone();
		this.milestones = memento.getMilestones();
		
	}
	
	/**
	 * Innerclass
	 */
	public class SubSystemMemento extends Memento<SubSystem>
	{
		private List<SubSystem> subsystems;
		private List<SubSystemMemento> subsystemMementos = new ArrayList<>();
		
		private List<BugReport> bugreports;
		private List<BugReport.BugReportMemento> bugreportMementos = new ArrayList<>();
		
		private Milestone latestAchievedMilestone;
		private List<Milestone> milestones;
		
		
		public SubSystemMemento(SubSystem originator)
		{
			super(originator);
			this.subsystems =  new ArrayList<>(originator.getSubSystems());
			for(SubSystem subsystem : subsystems)
				subsystemMementos.add(subsystem.createMemento());
			
			this.bugreports =  new ArrayList<>(originator.getBugReports());
			for(BugReport bugreport : bugreports)
				bugreportMementos.add(bugreport.createMemento());
			
			this.latestAchievedMilestone = originator.getLatestAchievedMilestone();
			this.milestones =  new ArrayList<>(originator.getAllMilestones());
			
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
}
