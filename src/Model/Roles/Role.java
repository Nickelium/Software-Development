package Model.Roles;

import Model.Tags.Tag;
import Model.Tags.UnderReview;
import Model.User.Developer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tom on 19/02/16.
 */
public abstract class Role {
    private Developer developer;
    private double participation;

    protected List<Tag> tagPermissions;

    /**
     * Basic constructor for all role-like objects.
     *
     * @param developer The developer assigned the role.
     */
    public Role(Developer developer){
        this.developer = developer;
        this.tagPermissions = Arrays.asList(new UnderReview());
    }

    /**
     * Getter to request the name of the role.
     *
     * @return The role name;
     */
    public abstract String toString();

    public Developer getDeveloper(){
        return this.developer;
    }

    /**
     * Getter to request the participation of the role.
     *
     * @return The participation of the role.
     */
    public double getParticipation(){
        return participation;
    }

    /**
     * Setter to set the participation of the role.
     *
     * @param participation The participation of the role.
     */
    public void setParticipation(double participation){
        this.participation = participation;
    }

    /**
     * Method to check if user has permission to use the selected tag.
     *
     * @param tag The tag to check
     *
     * @return True if the user has permission to use te selected tag.
     */
    public boolean canAssignTag(Tag tag){
        return this.tagPermissions.contains(tag);
    }

}
