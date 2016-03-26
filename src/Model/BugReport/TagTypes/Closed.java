package Model.BugReport.TagTypes;

import CustomExceptions.ModelException;
import Model.BugReport.BugReport;
import Model.BugReport.Tag;
import Model.User.Developer;

import java.util.Arrays;

/**
 * Created by Tom on 19/02/16.
 */
public class Closed extends Tag {

    /**
     * Default constructor for the closed tag.
     */
    public Closed(){
        this.acceptedTags = Arrays.asList();
    }

    @Override
    protected void assignDeveloper(BugReport bugReport, Developer developer) throws ModelException {
        throw new ModelException("Tag is closed! Bugreport is permanent and cannot be changed.");
    }

    @Override
    public String toString() {
        return "Closed";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Closed) return true;
        else return false;
    }
}
