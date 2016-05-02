package Model.BugReport.TagTypes;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.Patch;
import Model.BugReport.Tag;
import Model.BugReport.Test;

import java.util.ArrayList;

/**
 * Class inheriting from the Tag Class.
 * The 'NotABug' tag denotes that a bug is a not actually a bug.
 */
public class NotABug extends Tag {

    /**
     * Default constructor for the notABug tag
     */
    public NotABug(){
        setManuallyAcceptedTags(new ArrayList<>());
    }

    /**
     * Adding a test to a bug report containing the 'NotABug' tag, will result into an error.
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
     * Adding a patch to a bug report containing the 'NotABug' tag, will result into an error.
     *
     * @param bugReport The bug report to assign the patch to.
     * @param patch     The patch to assign to a bug report.
     * @throws ReportErrorToUserException because a patch cannot be added to a bug report with the 'NotABug' tag.
     */
    @Override
    protected void addPatch(BugReport bugReport, Patch patch) throws ReportErrorToUserException {
        throw new ReportErrorToUserException("No patches can be submitted because the bug report doesn't have the proper tag.");
    }

    //TODO: Documentation
    @Override
    protected double getMultiplier() {
        return 0;
    }

    @Override
    public String toString() {
        return "NotABug";
    }

    @Override
    public boolean isFinal() {
        return true;
    }
}
