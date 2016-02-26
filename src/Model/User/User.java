package Model.User;

import java.util.List;

/**
 * Created by Karina on 19.02.2016.
 */
public abstract class User {

    public enum Right{
        createProject,
        makeComment
    }

    /**
     * Abstract list of rights.
     */
    private List<Right> rights;

    private String firstName;
    private String middleName;
    private String lastName;
    private String userName;

    /**
     * Creates a new User instance using the given strings.
     * @param firstName The first name of the user.
     * @param middleName The middle name of the user.
     * @param lastName The last name of the user.
     * @param userName The user name of the user.
     */
    User(String firstName, String middleName, String lastName, String userName){
        setFirstName(firstName);
        setMiddleName(middleName);
        setLastName(lastName);
        setUserName(userName);
    }

    // Getters

    /**
     * Returns the first name of the user.
     * @return The first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the middle name of the user.
     * @return The middle name.
     */
    public String getMiddleName(){
        return middleName;
    }

    /**
     * Returns the last name of the user.
     * @return The last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the (unique) user name of the user.
     * @return The unique user name.
     */
    public String getUserName() {
        return userName;
    }

    // Setters

    /**
     * Sets the first name of the user.
     * @param firstName A string representing the new first name of the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the middle name of the user.
     * @param middleName A string representing the new middle name of the user.
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Sets the last name of the user.
     * @param lastName A string representing the new last name of the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the user name of the user.
     * @param userName A string representing the new user name of the user.
     */
    private void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Returns whether the user has a specific right.
     * @param right The right to be checked.
     * @return True when the user has the right.
     */
    public boolean hasRight(Right right){
        return rights.contains(right);
    }
}
