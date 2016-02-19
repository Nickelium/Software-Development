package Model.Roles;

import Model.Developer;

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
    }

    /**
     * Getter to request the name of the role.
     *
     * @return The the name of the role.
     */
    @Override
    public String getName() {
        return "Programmer";
    }
}
