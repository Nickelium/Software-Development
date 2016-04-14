package Model.User;

import CustomExceptions.ReportErrorToUserException;
import Model.Mail.Mailbox;


/**
 * Abstract class representing a user object.
 *
 * A user object has attributes for its user name, first name, middle name and last name.
 * A user object also holds a reference to his personal mailbox, where
 * he will receive notifications.
 */
public abstract class User {

    private String firstName;
    private String middleName;
    private String lastName;
    private String userName;
    
    private Mailbox mailbox;

    /**
     * Default constructor for a user.
     *
     * @param firstName The first name of the user.
     * @param middleName The middle name of the user.
     * @param lastName The last name of the user.
     * @param userName The user name of the user.
     *
     * @throws ReportErrorToUserException is thrown when username is empty.
     * @throws IllegalArgumentException is thrown when one of the name arguments is null. (use empty string instead)
     */
    User(String firstName, String middleName, String lastName, String userName) throws ReportErrorToUserException {
        setFirstName(firstName);
        setMiddleName(middleName);
        setLastName(lastName);
        setUserName(userName);
        
        this.mailbox = new Mailbox();
    }

    // region Getters

    /**
     * Getter to request the first name of the user.
     *
     * @return The first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter to request the middle name of the user.
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
     * Getter to request the unique username of the user.
     *
     * @return The unique user name.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the mailbox object of a user.
     *
     * @return the mailbox of a user.
     */
    public Mailbox getMailbox()
    {
    	return mailbox;
    }

    //endregion

    //region Setters

    /**
     * Method to set the first name of a user
     *
     * @param firstName the first name to be set
     * @throws ReportErrorToUserException is thrown if the first name is null.
     */
    private void setFirstName(String firstName) throws ReportErrorToUserException {
        if (firstName == null) throw new IllegalArgumentException("First name is null, use empty string");
        this.firstName = firstName;
    }

    /**
     * Method to set the middle name of a user
     *
     * @param middleName the middle name to be set
     * @throws ReportErrorToUserException is thrown if the middle name is null.
     */
    private void setMiddleName(String middleName) throws ReportErrorToUserException {
        if (middleName == null) throw new IllegalArgumentException("Middle name is null, use empty string");
        this.middleName = middleName;
    }

    /**
     * Method to set the last name of a user
     *
     * @param lastName the last name to be set
     * @throws ReportErrorToUserException is thrown if the last name is null.
     */
    private void setLastName(String lastName) throws ReportErrorToUserException {
        if (lastName == null) throw new IllegalArgumentException("Last name is null, use empty string");
        this.lastName = lastName;
    }

    /**
     * Method to set the user name of a user
     *
     * @param userName the user name to be set
     * @throws ReportErrorToUserException is thrown if the user name is not valid (null or empty).
     */
    private void setUserName(String userName) throws ReportErrorToUserException {
        if (!isValidUserName(userName)) throw new ReportErrorToUserException("Username is empty");
        this.userName = userName;
    }

    //endregion

    /**
     * Checker to check whether the username of the user is not null or empty.
     *
     * @param userName The username to check.
     *
     * @return True if the username is not empty and not null,
     *         Else return false
     */
    public boolean isValidUserName(String userName){
        if (userName == null) return false;
        if (userName.equals("")) return false;
        else return true;
    }

    /**
     * Method to represent a user object in an easy to read string object.
     *
     * @return a string consisting of all fields of the user object.
     */
    @Override
    public String toString(){
        if (getMiddleName().equals("")){
            return getUserName() + " (" + getFirstName() + " " + getLastName() + ")";
        }else{
            return getUserName() + " (" + getFirstName() + " " + getMiddleName() + " " + getLastName() + ")";
        }
    }

}
