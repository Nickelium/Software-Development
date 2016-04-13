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
public class UnderReview extends Tag {

    /**
     * Default constructor for the underReview tag.
     */
    public UnderReview(){
        this.manuallyAcceptedTags = Arrays.asList(Assigned.class, Closed.class);
    }

    @Override
    public String toString() {
        return "UnderReview";
    }

    @Override
    protected void addTest(BugReport bugReport, Test test) throws ReportErrorToUserException {
        throw new ReportErrorToUserException("The Bug Report doesn't has the tag Assigned, so no test can be added!");
    }

    @Override
    protected void addPatch(BugReport bugReport, Patch patch) throws ReportErrorToUserException {
        super.addPatch(bugReport, patch);
    }

    @Override
    protected void changeTag(BugReport bugReport, Tag tag) throws ReportErrorToUserException {
        super.changeTag(bugReport, tag);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UnderReview) return true;
        else return false;
    }
}
