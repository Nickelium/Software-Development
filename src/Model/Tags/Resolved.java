package Model.Tags;

/**
 * Created by Tom on 19/02/16.
 */
public class Resolved extends Tag {
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


