package Model.Roles;

import Model.User.Developer;

/**
 * Created by Tom on 19/02/16.
 */
public class Lead extends Role {

    /**
     * Constructor for a Lead.
     *
     * @param developer The developer assigned the lead role.
     */
    public Lead(Developer developer){
        super(developer);
    }

    /**
     * Getter to return the name of the role.
     *
     * @return The name of the role.
     */
    @Override
    public String toString() {
        return "Lead";
    }
}
