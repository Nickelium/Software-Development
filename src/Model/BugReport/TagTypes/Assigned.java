package Model.BugReport.TagTypes;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.Patch;
import Model.BugReport.Tag;
import Model.BugReport.Test;

import java.util.Arrays;

/**
 * Created by Tom on 19/02/16.
 */
public class Assigned extends Tag {

    /**
     * Default constructor for an Assigned tag.
     */
    public Assigned(){
        this.manuallyAcceptedTags = Arrays.asList(NotABug.class, Duplicate.class);
    }

    @Override
    protected void addTest(BugReport bugReport, Test test) throws ReportErrorToUserException {
        super.addTest(bugReport, test);
    }

    @Override
    protected void addPatch(BugReport bugReport, Patch patch) throws ReportErrorToUserException {
        if (bugReport.getTests().isEmpty())
            throw new ReportErrorToUserException("No tests are submitted yet, so no patches can be added.");
        super.addPatch(bugReport, patch);
        this.changeTag(bugReport, new UnderReview());
    }

    @Override
    protected void changeTag(BugReport bugReport, Tag tag) throws ReportErrorToUserException {
        if (!bugReport.getDependencies().isEmpty())
            throw new ReportErrorToUserException("The bugreport has dependencies, until all are resolved, the tag cannot change.");
        super.changeTag(bugReport, tag);
    }

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
