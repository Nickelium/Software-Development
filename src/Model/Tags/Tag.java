package Model.Tags;

import java.util.List;

/**
 * Created by Tom on 19/02/16.
 */
public abstract class Tag{
    protected List<Class<? extends Tag>> acceptedTags;

    /**
     * Method for checking if from this tag can be switched to the given one.
     *
     * @param tag The tag that the user wants to change to.
     *
     * @return True if the tag can be changed from this tag to te given one.
     */
    public boolean canChangeToTag(Tag tag){
        return acceptedTags.contains(tag.getClass());
    }

    public abstract boolean equals(Object obj);

    public abstract String toString();
}
