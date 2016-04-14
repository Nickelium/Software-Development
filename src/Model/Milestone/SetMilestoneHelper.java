package Model.Milestone;

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
     * Check that the give milestone does exceed the milestones of the subsystems.
     *
     * @param cont The class containing the milestones.
     * @param ms   The new milestone.
     * @return True if the given milestone does exceed the milestones of the subsystems.
     */
    public static boolean milestoneDoesExceedSubsystemMilestone(MilestoneContainer cont, Milestone ms) {
        double max = 0.0;
        List<Milestone> milestones = cont.getAllMilestones();

        if (milestones.isEmpty()) return true;

        for (Milestone milestone : milestones) {
            if (ms.getIDvalue() > max) {
                max = milestone.getIDvalue();
            }
        }

        return ms.getIDvalue() > max;
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
        List<BugReport> bugReports = cont.getAllBugReports().stream().filter(x -> !x.getTag().isFinal() && x.getTargetMilestone() != null).collect(Collectors.toList());

        if (bugReports.isEmpty()) return true;
        for (BugReport br : bugReports) {
            if (br.getTargetMilestone().getIDvalue() > max) {
                max = br.getTargetMilestone().getIDvalue();
            }
        }

        return ms.getIDvalue() <= max;
    }
}
