package Model.Milestone;

import Model.BugReport.BugReport;
import Model.Project.SubSystem;

import java.util.List;

/**
 * Interface for milestone container classes.
 */
public interface MilestoneContainer {

    /**
     * Method to get all sub systems of a given object.
     * @return a list with all subsystems
     */
    List<SubSystem> getAllSubSystems();

    /**
     * Method to get all milestones of a given object.
     * @return a list with all milestones
     */
    List<Milestone> getAllMilestones();

    /**
     * Method to get all bug reports of a given object.
     * @return a list with all bug reports
     */
    List<BugReport> getAllBugReports();

    /**
     * Method to get the latest achieved milestone of a given object.
     * @return a milestone object denoting the latest achieved milestone
     */
    Milestone getLatestAchievedMilestone();

}
