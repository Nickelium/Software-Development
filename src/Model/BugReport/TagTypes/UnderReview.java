package Model.BugReport.TagTypes;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.Tag;
import Model.BugReport.Test;

import java.util.Arrays;

/**
 * Class inheriting from the Tag Class.
 * The 'UnderReview' tag denotes that a bug is under review.
 */
public class UnderReview extends Tag {

    /**
     * Default constructor for the underReview tag.
     */
    public UnderReview(){
        setManuallyAcceptedTags(Arrays.asList(Assigned.class, Resolved.class, Closed.class, NotABug.class, Duplicate.class));
    }

    @Override
    public String toString() {
        return "UnderReview";
    }

    /**
     * Adding a test to a bug report containing the 'UnderReview' tag, will result into an error.
     *
     * @param bugReport The bug report to assign the test to.
     * @param test      The test to assign.
     * @throws ReportErrorToUserException because a test only can be added to bug reports with
     *         the 'Assigned' tag.
     */
    @Override
    protected void addTest(BugReport bugReport, Test test) throws ReportErrorToUserException {
        throw new ReportErrorToUserException("The Bug Report doesn't has the tag Assigned, so no test can be added!");
    }

    @Override
    public boolean isFinal() {
        return false;
    }
}
