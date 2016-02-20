package Model.Tags;

/**
 * Created by Tom on 19/02/16.
 */
public class Closed extends Tag {

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
