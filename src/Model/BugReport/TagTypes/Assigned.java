package Model.BugReport.TagTypes;

import Model.BugReport.Tag;

import java.util.Arrays;

/**
 * Created by Tom on 19/02/16.
 */
public class Assigned extends Tag {

    /**
     * Default constructor for an Assigned tag.
     */
    public Assigned(){
        this.acceptedTags = Arrays.asList(UnderReview.class, NotABug.class);
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
