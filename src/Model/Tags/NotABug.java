package Model.Tags;

/**
 * Created by Tom on 19/02/16.
 */
public class NotABug extends Tag {
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
