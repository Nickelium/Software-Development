package Model.BugReport;

import CustomExceptions.ReportErrorToUserException;
import Model.User.Developer;

import java.util.List;

/**
 * Abstract class containing base functionality for tags.
 * This class needs to be implemented by all tags.
 */
public abstract class Tag{
    protected List<Class<? extends Tag>> acceptedTags;

    /**
     * Method for checking if from this tag can be switched to the given one.
     *
     * @param tag The tag that the user wants to change to.
     *
     * @return True if the tag can be changed from this tag to te given one.
     */
    public boolean canChangeToTag(Tag tag){
        return acceptedTags.contains(tag.getClass());
    }

    /**
     * Protected method for assigning a patch, Method is different for each tag.
     *
     * @param bugReport The bugreport to assign the patch to.
     * @param patch     The patch to assign.
     * @throws ReportErrorToUserException Assigning the patch is not possible.
     */
    protected void addPatch(BugReport bugReport, Patch patch) throws ReportErrorToUserException {
        if (bugReport.getTests().isEmpty())
            throw new ReportErrorToUserException("Unable to submit patch because no patches are submitted.");
        bugReport.patches.add(patch);
    }

    /**
     * Protected method for assigning a test, Method is different for each tag.
     *
     * @param bugReport The bugreport to assign the test to.
     * @param test      The test to assign.
     * @throws ReportErrorToUserException Assigning the test is not possible.
     */
    protected void addTest(BugReport bugReport, Test test) throws ReportErrorToUserException {
        throw new ReportErrorToUserException("Tests can only be added when tag is Assigned");
    }

    /**
     * Protected method for assigning a developer. Method is different for each tag.
     *
     * @param bugReport The bugreport to assign the developer to.
     * @param developer The developer to assign
     * @throws ReportErrorToUserException Assigning a developer is not possible.
     */
    protected void assignDeveloper(BugReport bugReport, Developer developer) throws ReportErrorToUserException {
        bugReport.assignees.add(developer);
    }

    protected void changeTag(BugReport bugReport, Tag tag) throws ReportErrorToUserException {
        bugReport.tag = tag;
    }

    protected void addTestToTests(BugReport bugReport, Test test) {
        bugReport.tests.add(test);
    }


    public abstract boolean equals(Object obj);

    public abstract String toString();
}
