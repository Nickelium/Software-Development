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
public abstract class SetMilestoneHelper {

    /**
     * Check that the give milestone does not exceed the milestones of the subsystems.
     *
     * @param cont The class containing the milestones.
     * @param ms   The new milestone.
     * @return True if the given milestone does not exceed the milestones of the subsystems.
     *
     * @throws IllegalArgumentException is thrown if milestone container or milestone is null.
     */
    public static boolean milestoneDoesNotExceedSubsystemMilestone(MilestoneContainer cont, Milestone ms)
    {
        if (cont == null) throw new IllegalArgumentException("MilestoneContainer is null");
        if (ms == null) throw new IllegalArgumentException("Milestone is null");
        
        Milestone minimalSubsystemMS;
		try
		{
			minimalSubsystemMS = new Milestone("M" + Integer.MAX_VALUE);
		}
		catch (ReportErrorToUserException e)
		{
			throw new AssertionError(e.getMessage() + " : fail to build maxvalue milestone");
		}
		
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
     * @throws IllegalArgumentException MilestoneContainer or milestone is null.
     */
    public static boolean milestoneDoesExceedSubsystemMilestone(MilestoneContainer cont, Milestone ms) 
    {
        if (cont == null) throw new IllegalArgumentException("MilestoneContainer is null");
        if (ms == null) throw new IllegalArgumentException("Milestone is null");

        Milestone minimalSubsystemMS;
    	try
    	{
    		minimalSubsystemMS = new Milestone("M" + Integer.MAX_VALUE);
    	}
    	catch (ReportErrorToUserException e)
    	{
    		throw new AssertionError(e.getMessage() + " : fail to build maxvalue milestone");
    	}
        
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
     *
     * @throws IllegalArgumentException Milestonecontainer or milestone is null.
     */
    public static boolean milestoneDoesNotExceedBugReportMilestone(MilestoneContainer cont, Milestone ms)  
    {
        if (cont == null) throw new IllegalArgumentException("MilestoneContainer is null");
        if (ms == null) throw new IllegalArgumentException("Milestone is null");

        Milestone minimalTargetMS;
    	try
    	{
    		minimalTargetMS = new Milestone("M" + Integer.MAX_VALUE);
    	}
    	catch (ReportErrorToUserException e)
    	{
    		throw new AssertionError(e.getMessage() + " : fail to build maxvalue milestone");
    	}
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
