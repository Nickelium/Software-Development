package Model.Tags.TagTypes;

import Model.Tags.Tag;

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
    public boolean isPermanent() {
        return false;
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
