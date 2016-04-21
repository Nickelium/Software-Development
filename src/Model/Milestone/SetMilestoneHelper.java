package Model.Milestone;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.Project.SubSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
     * Check that the give milestone does not exceed the milestones of the subsystems.
     *
     * @param cont The class containing the milestones.
     * @param ms   The new milestone.
     * @return True if the given milestone does not exceed the milestones of the subsystems.
     *
     * @throws ReportErrorToUserException TODO
     */
    public static boolean milestoneDoesNotExceedSubsystemMilestone(MilestoneContainer cont, Milestone ms) throws ReportErrorToUserException {
        Milestone minimalSubsystemMS = new Milestone("M" + Integer.MAX_VALUE);
        List<Milestone> milestones = new ArrayList<>();

        for (SubSystem subSystem : cont.getAllSubSystems()) {
            milestones.addAll(subSystem.getCurrentSubsystemMilestones());
        }

        if (milestones.isEmpty()) return true;

        for (Milestone milestone : milestones) {
            if (milestone.compareTo(minimalSubsystemMS) < 0) {
                minimalSubsystemMS = milestone;
            }
        }

        if(ms.compareTo(minimalSubsystemMS) > 0)
            return false;
        else
            return true; // new ms is <= the highest allowed milestone
    }

    /**
     * Check that the given milestone does exceed the milestones of the subsystems.
     *
     * @param cont The class containing the milestones.
     * @param ms   The new milestone.
     * @return True if the given milestone does exceed the milestones of the subsystems.
     *
     * //TODO wat is hier het nut van? is dit niet gewoon !milestoneDoesNotExceedSubsystemMilestone?
     */
    public static boolean milestoneDoesExceedSubsystemMilestone(MilestoneContainer cont, Milestone ms) throws ReportErrorToUserException {
        Milestone minimalSubsystemMS = new Milestone("M" + Integer.MAX_VALUE);
        List<Milestone> milestones = cont.getAllMilestones();

        if (milestones.isEmpty()) return true;

        for (Milestone milestone : milestones) {
            if (milestone.compareTo(minimalSubsystemMS) < 0) {
                // milestone is smaller than the smallest subsystem milestone up to now
                minimalSubsystemMS = milestone;
            }
        }

        if(ms.compareTo(minimalSubsystemMS) > 0)
            return true;
        else
            return false; // new ms is >= the highest milestone of any subsystem
    }

    /**
     * Check that the given milestone is bigger than the latest milestone.
     *
     * @param cont The class containing the milestone.
     * @param ms   The milestone to assign to the class.
     * @return True if the given milestone is bigger than the current milestone.
     */
    public static boolean mileStoneIsBiggerThanCurrent(MilestoneContainer cont, Milestone ms) {
        return ms.compareTo(cont.getLatestAchievedMilestone()) > 0;
    }

    /**
     * Check that the given milestone does not exceed the milestones of the bug reports.
     *
     * @param cont The class containing the milestones.
     * @param ms   The new milestone.
     * @return True if the given milestone does not exceed the milestones of the bug reports.
     */
    public static boolean milestoneDoesNotExceedBugReportMilestone(MilestoneContainer cont, Milestone ms) throws ReportErrorToUserException {
        Milestone minimalTargetMS = new Milestone("M" + Integer.MAX_VALUE);
        List<BugReport> bugReports = cont.getAllBugReports().stream().filter(x -> !x.getTag().isFinal() && x.getTargetMilestone() != null).collect(Collectors.toList());

        if (bugReports.isEmpty()) return true;
        for (BugReport br : bugReports) {
            if (br.getTargetMilestone().compareTo(minimalTargetMS) < 0) {
                minimalTargetMS = br.getTargetMilestone();
            }
        }

        if(ms.compareTo(minimalTargetMS) > 0)
            return false;
        else
            return true; // new ms is <= the highest allowed milestone
    }
}
