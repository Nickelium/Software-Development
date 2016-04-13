package Model.BugReport.TagTypes;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.Patch;
import Model.BugReport.Tag;

import java.util.Arrays;

/**
 * Class inheriting from the Tag Class.
 * The 'Assigned' tag denotes that a bug report has been assigned to a user.
 */
public class Assigned extends Tag {

    /**
     * Default constructor for an Assigned tag.
     */
    public Assigned(){
        this.manuallyAcceptedTags = Arrays.asList(NotABug.class, Duplicate.class);
    }

    /**
     * Method to add a patch to a bug report.
     *
     * @param bugReport The bug report to assign the patch to.
     * @param patch     The patch to assign to the bug report.
     * @throws ReportErrorToUserException is thrown if the specified bug report doesn't have any tests.
     */
    @Override
    protected void addPatch(BugReport bugReport, Patch patch) throws ReportErrorToUserException {
        if (bugReport.getTests().isEmpty())
            throw new ReportErrorToUserException("No tests are submitted yet, so no patches can be added.");
        super.addPatch(bugReport, patch);
        this.changeTag(bugReport, new UnderReview());
    }

    /**
     * Changes the "Assigned" tag of a bug report to a new tag.
     *
     * @param bugReport the bug report of which the tag has to be changed
     * @param tag the new tag for the specified bug report
     * @throws ReportErrorToUserException is thrown if the bug report has dependencies.
     *         The Assigned tag cannot be changed until all dependencies are resolved.
     */
    @Override
    protected void changeTag(BugReport bugReport, Tag tag) throws ReportErrorToUserException {
        if (!bugReport.getDependencies().isEmpty())
            throw new ReportErrorToUserException("The bugreport has dependencies, until all are resolved, the tag cannot change.");
        super.changeTag(bugReport, tag);
    }

    /**
     * When a tag is changed to another type, specific fields of the related bug report are updated.
     * In the case of the Assign tag, all patches and tests are removed.
     *
     * @param bugReport The bug report of which to update the fields.
     * @throws ReportErrorToUserException
     */
    @Override
    protected void updateTagSpecificFields(BugReport bugReport) throws ReportErrorToUserException {
        this.removeAllPatches(bugReport);
        this.removeAllTest(bugReport);
    }

    @Override
    public String toString() {
        return "Assigned";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  Assigned) return true;
        else return false;
    }
}
