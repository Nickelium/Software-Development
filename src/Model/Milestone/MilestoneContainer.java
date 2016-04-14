package Model.Milestone;

import Model.BugReport.BugReport;
import Model.Project.SubSystem;

import java.util.List;

/**
 * Created by Tom on 14/04/16.
 */
public interface MilestoneContainer {
    List<SubSystem> getAllSubSystems();

    List<Milestone> getAllMilestones();

    List<BugReport> getAllBugReports();

    Milestone getLatestAchievedMilestone();

}
