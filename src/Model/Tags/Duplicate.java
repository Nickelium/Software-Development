package Model.Tags;

import Model.BugReport.BugReport;

import java.util.Arrays;

/**
 * Created by Tom on 19/02/16.
 */
public class Duplicate extends Tag {

    private BugReport duplicateBugReport;

    /**
     * Default constructor for the duplicate tag
     */
    public Duplicate(){
        this.acceptedTags = Arrays.asList();
    }


    /**
     * Constructor for the default tag which takes the duplicate
     * @param duplicateBugReport
     */
    public Duplicate(BugReport duplicateBugReport){
        this();
        this.duplicateBugReport = duplicateBugReport;
    }

    public BugReport getDuplicateBugReport(){
        return this.duplicateBugReport;
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
