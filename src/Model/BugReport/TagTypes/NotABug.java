package Model.BugReport.TagTypes;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.Tag;
import Model.User.Developer;

import java.util.Arrays;

/**
 * Created by Tom on 19/02/16.
 */
public class NotABug extends Tag {

    /**
     * Default constructor for the notABug tag
     */
    public NotABug(){
        this.acceptedTags = Arrays.asList();
    }

    @Override
    protected void assignDeveloper(BugReport bugReport, Developer developer) throws ReportErrorToUserException {
        throw new ReportErrorToUserException("Tag is NotABug! Bugreport is permanent and cannot be changed.");
    }

    @Override
    public String toString() {
        return "NotABug";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NotABug) return true;
        else return false;
    }
}
