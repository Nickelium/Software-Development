package Model.User;

import CustomExceptions.ReportErrorToUserException;
import Model.Mail.Mailbox;


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
    
    private Mailbox mailbox;

    /**
     * Default constructor for a user.
     *
     * @param firstName The first name of the user.
     * @param middleName The middle name of the user.
     * @param lastName The last name of the user.
     * @param userName The user name of the user.
     *
     * @throws ReportErrorToUserException Username is empty.
     * @throws IllegalArgumentException One of the name arguments is null. (use empty string instead)
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

    //TODO doc
    public Mailbox getMailbox()
    {
    	return mailbox;
    }

    //endregion

    //region Setters

    //TODO doc
    private void setFirstName(String firstName) throws ReportErrorToUserException {
        if (firstName == null) throw new IllegalArgumentException("First name is null, use empty string");
        this.firstName = firstName;
    }

    //TODO doc
    private void setMiddleName(String middleName) throws ReportErrorToUserException {
        if (middleName == null) throw new IllegalArgumentException("Middle name is null, use empty string");
        this.middleName = middleName;
    }

    //TODO doc
    private void setLastName(String lastName) throws ReportErrorToUserException {
        if (lastName == null) throw new IllegalArgumentException("Last name is null, use empty string");
        this.lastName = lastName;
    }

    //TODO doc
    private void setUserName(String userName) throws ReportErrorToUserException {
        if (!isValidUserName(userName)) throw new ReportErrorToUserException("Username is empty");
        this.userName = userName;
    }

    //endregion

    /**
     * Checker to check the username of the user.
     *
     * @param userName The username to check.
     *
     * @return True if the username is not empty.
     */
    public boolean isValidUserName(String userName){
        if (userName == null) return false;
        if (userName.equals("")) return false;
        else return true;
    }

    @Override
    public String toString(){
        if (getMiddleName().equals("")){
            return getUserName() + " (" + getFirstName() + " " + getLastName() + ")";
        }else{
            return getUserName() + " (" + getFirstName() + " " + getMiddleName() + " " + getLastName() + ")";
        }
    }

}
