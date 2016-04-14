package Model.Milestone;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.Project.SubSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for setting the milestones in project and subsystem. To avoid duplicate code.
 */
public class SetMilestoneHelper {

    /**
     * No instances of this class can be created.
     */
    private SetMilestoneHelper() {
    }

    /**
     * Method to set a new project milestone.
     * <p>
     * There occurs consistency checking:
     * first pass: project milestone should not exceed any subsystem milestone
     * second pass: project milestone should not exceed the target milestone of
     * any related bug report with a non-final tag.
     *
     * @param ms                  The class containing the milestones.
     * @param newProjectMilestone the new project milestone that has to be set
     * @throws ReportErrorToUserException is thrown in case that a constraint is broken.
     */
    public static void setNewProjectMilestone(MilestoneContainer ms, Milestone newProjectMilestone) throws ReportErrorToUserException {
        if (!milestoneDoesNotExceedSubsystemMilestone(ms, newProjectMilestone))
            throw new ReportErrorToUserException("The new milestone exceeds milestone of subsystem!");
        if (!milestoneDoesNotExceedBugReportMilestone(ms, newProjectMilestone))
            throw new ReportErrorToUserException("The new milestone exceeds the milestone of the projects target bug report!");

        ms.setLatestAchievedMilestone(newProjectMilestone);
        ms.addMilestoneToList(newProjectMilestone);
    }

    /**
     * Check that the give milestone does not exceed the milestones of the subsystems.
     *
     * @param cont The class containing the milestones.
     * @param ms   The new milestone.
     * @return True if the given milestone does not exceed the milestones of the subsystems.
     */
    public static boolean milestoneDoesNotExceedSubsystemMilestone(MilestoneContainer cont, Milestone ms) {
        double max = 0.0;
        List<Milestone> milestones = new ArrayList<>();

        for (SubSystem subSystem : cont.getAllSubSystems()) {
            milestones.addAll(subSystem.getCurrentSubsystemMilestones());
        }

        if (milestones.isEmpty()) return true;

        for (Milestone milestone : milestones) {
            if (ms.getIDvalue() > max) {
                max = milestone.getIDvalue();
            }
        }

        return ms.getIDvalue() <= max;
    }

    /**
     * Check that the given milestone does not exceed the milestones of the bugreports.
     *
     * @param cont The class containing the milestones.
     * @param ms   The new milestone.
     * @return True if the given milestone does not exced the milestones of the bugreports.
     */
    public static boolean milestoneDoesNotExceedBugReportMilestone(MilestoneContainer cont, Milestone ms) {
        double max = 0.0;
        List<BugReport> bugReports = cont.getAllBugReports();

        if (bugReports.isEmpty()) return true;
        for (BugReport br : bugReports) {
            if (!br.getTag().isFinal() && br.getTargetMilestone() != null && br.getTargetMilestone().getIDvalue() > max) {
                max = br.getTargetMilestone().getIDvalue();
            }
        }

        return ms.getIDvalue() <= max;
    }
}
