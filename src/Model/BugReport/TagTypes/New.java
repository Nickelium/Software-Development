package Model.BugReport.TagTypes;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
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
        this.acceptedTags = Arrays.asList(Assigned.class, NotABug.class);
    }

    @Override
    protected void assignDeveloper(BugReport bugReport, Developer developer) throws ReportErrorToUserException {
        super.assignDeveloper(bugReport, developer);
        super.changeTag(bugReport, new Assigned());
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
