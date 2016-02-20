package Model.Tags;

/**
 * Created by Tom on 19/02/16.
 */
public class Duplicate extends Tag {
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
