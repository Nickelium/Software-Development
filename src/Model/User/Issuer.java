package Model.User;

import CustomExceptions.ModelException;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Karina on 19.02.2016.
 */
public class Issuer extends User {

    /**
     * Default constructor for an issuer.
     *
     * @param firstName The first name of the user.
     * @param middleName The middle name of the user.
     * @param lastName The last name of the user.
     * @param userName The user name of the user.
     *
     * @throws ModelException Username is empty.
     * @throws IllegalArgumentException One of the name arguments is null. (use empty string instead)
     */
    Issuer(String firstName, String middleName, String lastName, String userName) throws ModelException{
        super(firstName,middleName,lastName,userName);
    }

}
