package Model.Roles;

import Model.Tags.UnderReview;
import Model.User.Developer;

import java.util.Arrays;

/**
 * Created by Tom on 19/02/16.
 */
public class Programmer extends Role {

    /**
     * Constructor for a programmer.
     *
     * @param developer The developer assigned the programmer role.
     */
    public Programmer(Developer developer){

        super(developer);
        this.tagPermissions = Arrays.asList(UnderReview.class);
    }

    /**
     * Getter to request the name of the role.
     *
     * @return The the name of the role.
     */
    @Override
    public String toString() {
        return "Programmer";
    }
}
