package Model.Tags.TagTypes;

import Model.BugReport.BugReport;
import Model.Tags.Tag;

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
     *
     * @param duplicateBugReport The other bugreport not containing this tag.
     */
    public Duplicate(BugReport duplicateBugReport){
        this();
        this.duplicateBugReport = duplicateBugReport;
    }

    /**
     * Getter to request the duplicate bugreport.
     *
     * @return The duplicate bugreport.
     */
    public BugReport getDuplicateBugReport(){
        return this.duplicateBugReport;
    }

    @Override
    public boolean isPermanent() {
        return true;
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
