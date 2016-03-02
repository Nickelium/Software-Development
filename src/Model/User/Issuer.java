package Model.User;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Karina on 19.02.2016.
 */
public class Issuer extends User {

    /**
     * Creates a new Issuer instance using the given strings.
     * @param firstName The first name of the user.
     * @param middleName The middle name of the user.
     * @param lastName The last name of the user.
     * @param userName The user name of the user.
     */
    Issuer(String firstName, String middleName, String lastName, String userName){
        super(firstName,middleName,lastName,userName);
    }
}
