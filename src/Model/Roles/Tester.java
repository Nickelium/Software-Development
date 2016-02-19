package Model.Roles;

import Model.Developer;

/**
 * Created by Tom on 19/02/16.
 */
public class Tester extends Role {

    /**
     * Constructor for the tester role.
     *
     * @param developer The developer assigned the tester role.
     */
    public Tester(Developer developer){
        super(developer);
    }

    /**
     * Getter to request the name of the role.
     *
     * @return The name of the role.
     */
    @Override
    public String getName() {
        return "Tester";
    }
}
