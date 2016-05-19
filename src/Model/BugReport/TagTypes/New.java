package Model.BugReport.TagTypes;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.Patch;
import Model.BugReport.Tag;
import Model.BugReport.Test;
import Model.User.Developer;

import java.util.Arrays;

/**
 * Class inheriting from the Tag Class.
 * The 'New' tag denotes that a bug is a new bug.
 */
public class New extends Tag {

    /**
     * Default constructor of the New tag.
     */
    public New(){
        setManuallyAcceptedTags(Arrays.asList(NotABug.class, Duplicate.class));
    }

    /**
     * Assigning a developer to a bug report with the 'New' tag, will result in a change of tag
     * of the bug report from 'New' to 'Assigned'.
     *
     * @param bugReport The bug report to assign the developer to.
     * @param developer The developer to assign
     * @throws ReportErrorToUserException is thrown when some of the arguments are invalid.
     * @throws IllegalArgumentException Bugreport or developer is null.
     */
    @Override
    protected void assignDeveloper(BugReport bugReport, Developer developer) throws ReportErrorToUserException {
        if (bugReport == null) throw new IllegalArgumentException("Bugreport is null");
        if (developer == null) throw new IllegalArgumentException("Developer is null");
        super.assignDeveloper(bugReport, developer);
        this.changeTag(bugReport, new Assigned());
    }

    /**
     * Adding a patch to a bug report containing the 'New' tag, will result into an error.
     *
     * @param bugReport The bug report to assign the patch to.
     * @param patch     The patch to assign to a bug report.
     * @throws ReportErrorToUserException because a patch cannot be added to a bug report with the 'New' tag.
     */
    @Override
    protected void addPatch(BugReport bugReport, Patch patch) throws ReportErrorToUserException {
        throw new ReportErrorToUserException("No patches can be submitted because the bug report doesn't have the proper tag.");
    }

    /**
     * Adding a patch to a bug report containing the 'New' tag, will result into an error.
     *
     * @param bugReport The bug report to assign the patch to.
     * @param test      The patch to assign to a bug report.
     * @throws ReportErrorToUserException because a patch cannot be added to a bug report with the 'New' tag.
     */
    @Override
    protected void addTest(BugReport bugReport, Test test) throws ReportErrorToUserException {
        throw new ReportErrorToUserException("No tests can be submitted because the bug report doesn't have the proper tag.");
    }

    /**
     * Method returning the multiplier value, needed for the calculation of bug impact.
     * @return the multiplier of the specific tag
     */
    @Override
    protected double getMultiplier() {
        return 3;
    }

    @Override
    public String toString() {
        return "New";
    }

    @Override
    public boolean isFinal() {
        return false;
    }
}
