package Model.Tags;

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
    public String toString() {
        return "NotABug";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NotABug) return true;
        else return false;
    }
}
