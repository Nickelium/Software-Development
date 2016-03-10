package Model.Roles;

import Model.Tags.TagTypes.UnderReview;
import Model.User.Developer;

import java.util.Collections;

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
        this.tagPermissions = Collections.singletonList(UnderReview.class);
    }

    /**
     * Getter to request the name of the role.
     *
     * @return The the name of the role and the developer.
     */
    @Override
    public String toString() {
        return "Programmer: " + getDeveloper().toString();
    }

    /**
     * Method to copy this programmer object.
     *
     * @return The copied role.
     */
	@Override
	public Role copy() {
		return new Programmer(getDeveloper());
	}


}
