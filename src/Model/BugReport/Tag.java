package Model.BugReport;

import CustomExceptions.ReportErrorToUserException;
import Model.User.Developer;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class containing base functionality for tags.
 * All tag types inherit from this class.
 */
public abstract class Tag{
    protected List<Class<? extends Tag>> manuallyAcceptedTags;

    /**
     * Method for checking if from this tag can be switched to the given one.
     *
     * @param tag The tag that the user wants to change to.
     *
     * @return True if the tag can be changed from this tag to te given one.
     */
    public boolean canChangeToTag(Class<? extends Tag> tag) {
        return manuallyAcceptedTags.contains(tag);
    }

    /**
     * Protected method for assigning a patch, Method is different for each tag.
     *
     * @param bugReport The bugreport to assign the patch to.
     * @param patch     The patch to assign.
     * @throws ReportErrorToUserException Assigning the patch is not possible.
     */
    protected void addPatch(BugReport bugReport, Patch patch) throws ReportErrorToUserException {
        bugReport.patches.add(patch);
    }

    /**
     * Protected method for assigning a test, Method is different for each tag.
     *
     * @param bugReport The bug report to assign the test to.
     * @param test      The test to assign.
     * @throws ReportErrorToUserException Assigning the test is not possible.
     */
    protected void addTest(BugReport bugReport, Test test) throws ReportErrorToUserException {
        bugReport.tests.add(test);
    }

    /**
     * Protected method for assigning a developer. Method is different for each tag.
     *
     * @param bugReport The bug report to assign the developer to.
     * @param developer The developer to assign
     * @throws ReportErrorToUserException Assigning a developer is not possible.
     */
    protected void assignDeveloper(BugReport bugReport, Developer developer) throws ReportErrorToUserException {
        bugReport.assignees.add(developer);
    }

    /**
     * Protected method for changing a tag of some bug report.
     *
     * @param bugReport the bug report of which the tag has to be changed
     * @param tag the new tag for the specified bug report
     * @throws ReportErrorToUserException if the operation is not allowed to be executed due to system restrictions.
     */
    protected void changeTag(BugReport bugReport, Tag tag) throws ReportErrorToUserException {
        bugReport.tag = tag;
        tag.updateTagSpecificFields(bugReport);
    }

    protected void updateTagSpecificFields(BugReport bugReport) throws ReportErrorToUserException {
    }

    protected final void removeAllTest(BugReport bugReport) {
        bugReport.tests = new ArrayList<>();
    }

    protected final void removeAllPatches(BugReport bugReport) {
        bugReport.patches = new ArrayList<>();
    }

    protected final void setSelectedPatch(BugReport bugReport, int indexPath) throws ReportErrorToUserException {
        if (indexPath >= bugReport.getPatches().size())
            throw new ReportErrorToUserException("Selected patch does not exist in selected bugreport!");
        bugReport.selectedPatch = bugReport.getPatches().get(indexPath);
    }

    protected final void setSolutionScore(BugReport bugReport, int score) throws ReportErrorToUserException {
        if (score <= 0 || score > 5) throw new ReportErrorToUserException("The given score is not valid");
        bugReport.solutionScore = score;
    }

    public abstract boolean equals(Object obj);

    public abstract String toString();
}
