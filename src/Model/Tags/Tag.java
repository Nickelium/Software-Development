package Model.Tags;

/**
 * Created by Tom on 19/02/16.
 */
public abstract class Tag{

    public abstract String toString();

    public boolean canChangeToTag(Tag tag){
        return true;
    }

    public abstract boolean equals(Object obj);
}
