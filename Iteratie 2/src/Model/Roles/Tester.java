package Model.Roles;

import Model.BugReport.TagTypes.UnderReview;
import Model.User.Developer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Class extending the Role class, representing a Tester role object.
 *
 * A tester reviews the code of the projects he is assigned to.
 */
public class Tester extends Role {

    /**
     * Constructor for the tester role.
     *
     * @param developer The developer assigned to the tester role.
     */
    public Tester(Developer developer){
        super(developer);
        this.assignmentPermission = new ArrayList<>(Arrays.asList(Permission.assignDevelopersToBugReport));
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
