package Model.BugReport.TagTypes;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.Patch;
import Model.BugReport.Tag;
import Model.BugReport.Test;

import java.util.ArrayList;

/**
 * Class inheriting from the Tag Class.
 * The 'Closed' tag denotes that a bug report has been closed.
 */
public class Closed extends Tag {
    private int score;

    /**
     * Default constructor for the closed tag.
     * @param score The score given to the solution of the bugreport the close tag will be assigned to.
     */
    public Closed(int score) {
        setManuallyAcceptedTags(new ArrayList<>());
        this.score = score;
    }

    /**
     * Adding a test to a bug report containing the 'Closed' tag, will result into an error.
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

    /**
     * Adding a patch to a bug report containing the 'Closed' tag, will result into an error.
     *
     * @param bugReport The bug report to assign the patch to.
     * @param patch     The patch to assign to a bug report.
     * @throws ReportErrorToUserException because a patch cannot be added to a bug report with the 'Closed' tag.
     */
    @Override
    protected void addPatch(BugReport bugReport, Patch patch) throws ReportErrorToUserException {
        throw new ReportErrorToUserException("No patches can be submitted because the bug report doesn't have the proper tag.");
    }

    /**
     * When a tag is changed to another type, specific fields of the related bug report are updated.
     * In the case of the Closed tag, the solution score of a bug report will be set to the value
     * as specified in the score field of the 'Closed'-tag.
     *
     * @param bugReport The bug report of which to update the fields.
     * @throws ReportErrorToUserException is thrown if the specified score is not a valid score.
     * @throws IllegalArgumentException Bugreport is null.
     *
     */
    @Override
    protected void updateTagSpecificFields(BugReport bugReport) throws ReportErrorToUserException {
        if (bugReport == null) throw new IllegalArgumentException("Bugreport is null");
        this.setSolutionScore(bugReport, this.score);
    }

    @Override
    public String toString() {
        return "Closed";
    }


    @Override
    public boolean isFinal() {
        return true;
    }
}
