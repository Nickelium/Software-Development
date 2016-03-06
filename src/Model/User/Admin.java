package Model.User;

import CustomExceptions.ModelException;

/**
 * Created by Karina on 19.02.2016.
 */
public class Admin extends User {

    /**
     * Default constructor for an admin.
     *
     * @param firstName The first name of the user.
     * @param middleName The middle name of the user.
     * @param lastName The last name of the user.
     * @param userName The user name of the user.
     *
     * @throws ModelException Username is empty.
     * @throws IllegalArgumentException One of the name arguments is null. (use empty string instead)
     */
    Admin(String firstName, String middleName, String lastName, String userName) throws ModelException{
        super(firstName,middleName,lastName,userName);
    }

}
