package Model.User;

import CustomExceptions.ModelException;
import Model.User.User;

import java.util.Arrays;
import java.util.List;

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
     * @throws ModelException One of the arguments is empty.
     */
    Admin(String firstName, String middleName, String lastName, String userName) throws ModelException{
        super(firstName,middleName,lastName,userName);
    }

    /**
     * Default constructor for an admin without middle name.
     *
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param userName The user name of the user.
     *
     * @throws ModelException One of the arguments is empty.
     */
    Admin(String firstName, String lastName, String userName) throws ModelException{
        super(firstName,lastName,userName);
    }

}
