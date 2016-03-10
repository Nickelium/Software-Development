package Model.Tags.TagTypes;

import Model.Tags.Tag;

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
    public String toString() {
        return "Closed";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Closed) return true;
        else return false;
    }
}
