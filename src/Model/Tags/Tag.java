package Model.Tags;

import java.util.List;

/**
 * Created by Tom on 19/02/16.
 */
public abstract class Tag{
    protected List<Class<? extends Tag>> acceptedTags;

    public abstract String toString();

    public boolean canChangeToTag(Tag tag){
        return acceptedTags.contains(tag.getClass());
    }

    public abstract boolean equals(Object obj);
}
