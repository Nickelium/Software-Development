package Model.BugReport.TagTypes;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.Patch;
import Model.BugReport.Tag;
import Model.BugReport.Test;

import java.util.Arrays;

/**
 * Class inheriting from the Tag Class.
 * The 'Resolved' tag denotes that a bug is resolved.
 */
public class Resolved extends Tag {

    private Patch selectedPatch;

    /**
     * Default constructor for the Resolved tag.
     * @param patch The patch selected as best solution in the bugreport this tag is assigned to.
     */
    public Resolved(Patch patch) {
        setManuallyAcceptedTags(Arrays.asList(NotABug.class, Closed.class, Duplicate.class));
        setSelectedPatch(patch);
    }

    @Override
    public String toString() {
        return "Resolved";
    }

    /**
     * Adding a test to a bug report containing the 'Resolved' tag, will result into an error.
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
     * Adding a patch to a bug report containing the 'Resolved' tag, will result into an error.
     *
     * @param bugReport The bug report to assign the patch to.
     * @param patch     The patch to assign to a bug report.
     * @throws ReportErrorToUserException because a patch cannot be added to a bug report with the 'Resolved' tag.
     */
    @Override
    protected void addPatch(BugReport bugReport, Patch patch) throws ReportErrorToUserException {
        throw new ReportErrorToUserException("No patches can be submitted because the bug report doesn't have the proper tag.");
    }

    /**
     * When a tag is changed to another type, specific fields of the related bug report are updated.
     * In the case of the Resolved tag, the selected patch is updated.
     *
     * @param bugReport The bug report of which to update the fields.
     * @throws ReportErrorToUserException is thrown if the new selected patch index is invalid.
     *
     * @throws IllegalArgumentException Bugreport is null.
     */
    @Override
    protected void updateTagSpecificFields(BugReport bugReport) throws ReportErrorToUserException {
        if (bugReport == null) throw new IllegalArgumentException("Bugreport is null");
        this.setSelectedPatch(bugReport, getSelectedPatch());
    }

    /**
     * Method returning the multiplier value, needed for the calculation of bug impact.
     * @return the multiplier of the specific tag
     */
    @Override
    protected double getMultiplier() {
        return 0.1;
    }

    @Override
    public boolean isFinal() {
        return false;
    }

    private Patch getSelectedPatch() {
        return selectedPatch;
    }

    private void setSelectedPatch(Patch patch) {
        if (patch == null) throw new IllegalArgumentException("Patch is null");
        this.selectedPatch = patch;
    }
}


