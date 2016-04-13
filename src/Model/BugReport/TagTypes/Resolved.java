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
public class Resolved extends Tag {

    private int selectedPatchIndex;

    /**
     * Default constructor for the Resolved tag.
     */
    public Resolved(int selectedPatchIndex) {
        this.manuallyAcceptedTags = Arrays.asList(NotABug.class, Closed.class, Duplicate.class);
        this.selectedPatchIndex = selectedPatchIndex;
    }

    @Override
    public String toString() {
        return "Resolved";
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
    protected void updateTagSpecificFields(BugReport bugReport) throws ReportErrorToUserException {
        this.setSelectedPatch(bugReport, selectedPatchIndex);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Resolved) return true;
        else return false;
    }

    @Override
    public boolean isFinal() {
        return false;
    }
}


