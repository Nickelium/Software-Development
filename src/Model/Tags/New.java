package Model.Tags;

/**
 * Created by Tom on 19/02/16.
 */
public class New extends Tag {
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
