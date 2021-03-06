package Model.Roles;

import Model.BugReport.TagTypes.*;
import Model.User.Developer;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class extending the Role class, representing a Lead role object.
 *
 * The lead role grants permission to assign bug reports to developers.
 */
public class Lead extends Role {

    /**
     * Constructor for a Lead.
     *
     * @param developer The developer assigned the lead role.
     */
    public Lead(Developer developer){
        super(developer);
        this.assignmentPermission = new ArrayList<>(Arrays.asList(Permission.assignDevelopersToBugReport));
        this.tagPermissions = Arrays.asList(UnderReview.class, Duplicate.class, NotABug.class, Resolved.class, Closed.class);
    }

    /**
     * Getter to return the name of the role.
     *
     * @return The name of the role and developer
     */
    @Override
    public String toString() {
        return "Lead:" + getDeveloper().toString();
    }

    /**
     * Method to copy this role object.
     *
     * @return The copied role.
     */
	@Override
	public Role copy() {
		return new Lead(getDeveloper());
	}
}
