package Model;

import Handler.UserHandler;

/**
 * Created by Karina on 19.02.2016.
 */
public class Issuer extends User {

    /**
     * Creates a new Issuer instance using the given strings.
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param userName The user name of the user.
     * @param handler The user handler.
     * @param middleName The middle name of the user.
     */
    public Issuer(String firstName, String lastName, String userName, UserHandler handler, String middleName){
        super(firstName,lastName,userName,handler,middleName);
    }

    /**
     * Creates a new Issuer instance using the given strings. The middle name is initialized as the empty string.
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param userName The user name of the user.
     * @param handler The user handler.
     */
    public Issuer(String firstName, String lastName, String userName, UserHandler handler){
        super(firstName,lastName,userName,handler);
    }
}
