package Model.BugReport;

import CustomExceptions.ReportErrorToUserException;
import Model.User.Developer;

import java.util.List;

/**
 * Created by Tom on 19/02/16.
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
     * Protected method for assigning a developer. Method is different for each tag.
     *
     * @param bugReport The bugreport to assign the developer to.
     * @param developer The developer to assign
     * @throws ReportErrorToUserException Assigning a developer is not possible.
     */
    protected void assignDeveloper(BugReport bugReport, Developer developer) throws ReportErrorToUserException {
        bugReport.assignees.add(developer);
    }

    protected void changeTag(BugReport bugReport, Tag tag) {
        bugReport.setTag(tag);
    }

    public abstract boolean equals(Object obj);

    public abstract String toString();
}
