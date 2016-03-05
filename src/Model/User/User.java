package Model.User;

import CustomExceptions.ModelException;
import com.sun.tools.internal.ws.processor.model.Model;
import com.sun.tools.internal.xjc.reader.RawTypeSet;

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
     * Defalut constructor for a user.
     *
     * @param firstName The first name of the user.
     * @param middleName The middle name of the user.
     * @param lastName The last name of the user.
     * @param userName The user name of the user.
     *
     * @throws ModelException One of the arguments is empty.
     */
    User(String firstName, String middleName, String lastName, String userName) throws ModelException{
        if (!isValidFirstName(firstName)) throw new ModelException("First name is empty");
        if (!isValidLastName(lastName)) throw new ModelException("Last name is empty");
        if (!isValidUserName(userName)) throw new ModelException("Username is empty");

        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.userName = userName;
    }

    /**
     * Default constructor for user without middle name.
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param userName The username of the user.
     *
     * @throws ModelException One of the arguments is empty.
     */
    User(String firstName, String lastName, String userName) throws ModelException{
        this(firstName, "", lastName, userName);
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
     * Checker to check if the first name is valid.
     *
     * @param firstName The first name to check.
     *
     * @return True if the first name is not empty.
     */
    public boolean isValidFirstName(String firstName){
        if (firstName == null) return false;
        if (firstName.equals("")) return false;
        else return true;
    }

    /**
     * Checker to check if the last name is not empty.
     *
     * @param lastName The last name to check.
     *
     * @return True if the last name is not empty.
     */
    public boolean isValidLastName(String lastName){
        if (lastName == null) return false;
        if (lastName.equals("")) return false;
        else return true;
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
