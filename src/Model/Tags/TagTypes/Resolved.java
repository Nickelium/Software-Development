package Model.Tags.TagTypes;

import Model.Tags.Tag;

import java.util.Collections;

/**
 * Created by Tom on 19/02/16.
 */
public class Resolved extends Tag {

    /**
     * Default constructor for the Resolved tag.
     */
    public Resolved(){
        this.acceptedTags = Collections.singletonList(Closed.class);
    }

    @Override
    public boolean isPermanent() {
        return false;
    }

    @Override
    public String toString() {
        return "Resolved";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Resolved) return true;
        else return false;
    }
}


