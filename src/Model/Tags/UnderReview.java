package Model.Tags;

/**
 * Created by Tom on 19/02/16.
 */
public class UnderReview extends Tag {
    @Override
    public String toString() {
        return "UnderReview";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UnderReview) return true;
        else return false;
    }
}
