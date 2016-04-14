package Model.Milestone;

import Model.BugReport.BugReport;
import Model.Project.SubSystem;

import java.util.List;

/**
 * Created by Tom on 14/04/16.
 */
public interface MilestoneContainer {

    void setLatestAchievedMilestone(Milestone latestAchievedMilestone);

    void addMilestoneToList(Milestone milestone);

    List<SubSystem> getAllSubSystems();

    List<BugReport> getAllBugReports();

}
