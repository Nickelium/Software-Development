package Model.Tags;

import java.util.Arrays;

/**
 * Created by Tom on 19/02/16.
 */
public class UnderReview extends Tag {

    /**
     * Default constructor for the underReviewTag();
     */
    public UnderReview(){
        this.acceptedTags = Arrays.asList(Resolved.class, Assigned.class);
    }

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
