package Model.Milestone;

import CustomExceptions.ReportErrorToUserException;

/**
 * This class represents the Target Milestone object.
 *
 * Target milestones indicate that the problem of the bug report should be resolved
 * before the subsystem can achieve that milestone.
 *
 * Target milestones are different from regular milestones in the way that they are
 * optional; not every bug report has a target milestone. That's why there is no
 * constructor for a target milestone without a milestone ID field.
 */
public class TargetMilestone extends Milestone {

    private String targetMilestoneID;

    /**
     * Creates a target milestone object. The validity of a target milestone ID
     * is determined by the same policy as in the milestone class.
     *
     * @param targetMilestoneID the target milestone ID
     * @throws ReportErrorToUserException is thrown if the target milestone ID is invalid.
     */
    public TargetMilestone(String targetMilestoneID) throws ReportErrorToUserException {
        setMilestoneID(targetMilestoneID);
    }
}
