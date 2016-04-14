package Model.Roles;

import Model.BugReport.Tag;
import Model.User.Developer;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a Role object.
 * This class has to be implemented by each specific role.
 *
 * A role can be assigned to developers only.
 * A developer that participates in a project always has at least 1 role.
 */
public abstract class Role {
    private Developer developer;
    private double participation;
    protected List<Permission> assignmentPermission;
    protected List<Class<? extends Tag>> tagPermissions;

    /**
     * Basic constructor for all role-like objects.
     *
     * @param developer The developer assigned the role.
     */
    public Role(Developer developer){
        this.developer = developer;
        this.assignmentPermission = new ArrayList<>();
    }

    /**
     * Getter to request the name of the role.
     *
     * @return The role name;
     */
    public abstract String toString();

    /**
     * Getter to request the developer object to which the role is assigned.
     *
     * @return the developer object to which the role is assigned
     */
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
    public boolean canAssignTag(Class<? extends Tag> tag) {
        return this.tagPermissions.contains(tag);
    }


    /**
     * Method to check whether a specific role has the permission to assign a developer to a bug report.
     *
     * @param permission the permission that a role needs to have
     * @return true if the role has permission to do the operation
     *         false if not
     */
    public boolean hasValidAssignmentPermission(Permission permission){
        return assignmentPermission.contains(permission);
    }

    /**
     * Method to copy this role object.
     *
     * @return The copied role.
     */
    public abstract Role copy();
}

