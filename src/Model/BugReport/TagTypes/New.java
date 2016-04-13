package Model.BugReport.TagTypes;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.Patch;
import Model.BugReport.Tag;
import Model.User.Developer;

import java.util.Arrays;

/**
 * Created by Tom on 19/02/16.
 */
public class New extends Tag {

    /**
     * Default constructor of the New tag.
     */
    public New(){
        this.manuallyAcceptedTags = Arrays.asList(NotABug.class);
    }

    @Override
    protected void assignDeveloper(BugReport bugReport, Developer developer) throws ReportErrorToUserException {
        super.assignDeveloper(bugReport, developer);
        this.changeTag(bugReport, new Assigned());
    }

    @Override
    protected void addPatch(BugReport bugReport, Patch patch) throws ReportErrorToUserException {
        throw new ReportErrorToUserException("No patches can be submitted because the bugreport doesn't have the propper tag.");
    }

    @Override
    protected void changeTag(BugReport bugReport, Tag tag) throws ReportErrorToUserException {
        super.changeTag(bugReport, tag);
    }

    @Override
    public String toString() {
        return "New";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof New) return true;
        else return false;
    }
}
