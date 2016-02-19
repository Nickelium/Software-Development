package Model.Roles;

import Model.Developer;

/**
 * Created by Tom on 19/02/16.
 */
public abstract class Role {
    private Developer developer;
    private double participation;

    /**
     * Basic constructor for all role-like objects.
     *
     * @param developer The developer assigned the role.
     */
    public Role(Developer developer){
        this.developer = developer;
    }

    /**
     * Getter to request the name of the role.
     *
     * @return The role name;
     */
    abstract String getName();

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

}
