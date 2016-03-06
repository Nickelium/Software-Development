package Model.User;

import CustomExceptions.ModelException;



import java.util.List;


/**
 * Created by Karina on 19.02.2016.
 */
public abstract class User {

    /**
     * Abstract list of rights.
     */
    private String firstName;
    private String middleName;
    private String lastName;
    private String userName;

    /**
     * Default constructor for a user.
     *
     * @param firstName The first name of the user.
     * @param middleName The middle name of the user.
     * @param lastName The last name of the user.
     * @param userName The user name of the user.
     *
     * @throws ModelException Username is empty.
     * @throws IllegalArgumentException One of the name arguments is null. (use empty string instead)
     */
    User(String firstName, String middleName, String lastName, String userName) throws ModelException{
        if (!isValidUserName(userName)) throw new ModelException("Username is empty");
        if (firstName == null) throw new IllegalArgumentException("First name is null, use empty string");
        if (middleName == null) throw new IllegalArgumentException("Middle name is null, use empty string");
        if (lastName == null) throw new IllegalArgumentException("Last name is null, use empty string");

        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.userName = userName;
    }

    // Getters

    /**
     * Getter to request the first name of the user.
     *
     * @return The first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter to request the midle name of the user.
     *
     * @return The middle name.
     */
    public String getMiddleName(){
        return middleName;
    }

    /**
     * Getter to request the last name of the user.
     *
     * @return The last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * Getter to request the unique username of the user.
     * @return The unique user name.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Checker to check the username of the user.
     *
     * @param userName The username to check.
     *
     * @return True if the username is not empty.
     */
    boolean isValidUserName(String userName){
        if (userName == null) return false;
        if (userName.equals("")) return false;
        else return true;
    }

    @Override
    public String toString(){
        return getUserName() + " : " + getFirstName() + " " + getMiddleName() + " " + getLastName();
    }

}
