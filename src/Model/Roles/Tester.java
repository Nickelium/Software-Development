package Model.Roles;

import Model.Tags.UnderReview;
import Model.User.Developer;

import java.util.Collections;

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
        this.assignmentPermission = Permission.assignDevelopersToBugReport;
        this.tagPermissions = Collections.singletonList(UnderReview.class);
    }

    /**
     * Getter to request the name of the role.
     *
     * @return The name of the role and developer.
     */
    @Override
    public String toString() {
        return "Tester: " + getDeveloper().toString();
    }
    
    /**
     * Method to copy this tester object.
     * 
     * @return The copied tester.
     */
	@Override
	public Role copy() {
		return new Tester(getDeveloper());
	}
}
