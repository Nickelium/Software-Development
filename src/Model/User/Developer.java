package Model.User;

import CustomExceptions.ModelException;

/**
 * Created by Karina on 19.02.2016.
 */
public class Developer extends Issuer {

    /**
     * Default constructor for a developer.
     *
     * @param firstName The first name of the user.
     * @param middleName The middle name of the user.
     * @param lastName The last name of the user.
     * @param userName The user name of the user.
     *
     * @throws ModelException Username is empty.
     * @throws IllegalArgumentException One of the name arguments is null. (use empty string instead)
     */
    Developer(String firstName, String middleName, String lastName, String userName) throws ModelException{
        super(firstName,middleName,lastName,userName);
    }

}
