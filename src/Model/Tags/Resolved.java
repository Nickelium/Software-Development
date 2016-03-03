package Model.Tags;

import java.util.Arrays;

/**
 * Created by Tom on 19/02/16.
 */
public class Resolved extends Tag {

    /**
     * Default constructor for the Resolved tag.
     */
    public Resolved(){
        this.acceptedTags = Arrays.asList();
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


