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
public class Duplicate extends Tag {

    private BugReport duplicateBugReport;
    /**
     * Constructor for the default tag which takes the duplicate
     *
     * @param duplicateBugReport The other bugreport not containing this tag.
     */
    public Duplicate(BugReport duplicateBugReport){
        this.manuallyAcceptedTags = Arrays.asList();
        this.duplicateBugReport = duplicateBugReport;
    }

    @Override
    protected void addTest(BugReport bugReport, Test test) throws ReportErrorToUserException {
        throw new ReportErrorToUserException("The Bug Report doesn't has the tag Assigned, so no test can be added!");
    }

    @Override
    protected void addPatch(BugReport bugReport, Patch patch) throws ReportErrorToUserException {
        throw new ReportErrorToUserException("No patches can be submitted because the bugreport doesn't have the propper tag.");
    }

    @Override
    public String toString() {
        return "Duplicate";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Duplicate)return true;
        else return false;
    }
}
