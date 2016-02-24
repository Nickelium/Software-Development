package Model.Tags;

/**
 * Created by Tom on 19/02/16.
 */
public class Assigned extends Tag {
    /**
     * Returns the name of the class
     *
     * @return Assigned as string.
     */
    @Override
    public String toString() {
        return "Assigned";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  Assigned) return true;
        else return false;
    }
}
