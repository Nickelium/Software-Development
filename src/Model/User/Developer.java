package Model.User;

/**
 * Created by Karina on 19.02.2016.
 */
public class Developer extends Issuer {

    /**
     * Creates a new Developer instance using the given strings.
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param userName The user name of the user.
     * @param middleName The middle name of the user.
     */
    public Developer(String firstName, String lastName, String userName, String middleName){
        super(firstName,lastName,userName,middleName);
    }

    /**
     * Creates a new Developer instance using the given strings. The middle name is initialized as the empty string.
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param userName The user name of the user.
     */
    public Developer(String firstName, String lastName, String userName){
        super(firstName,lastName,userName);
    }
}
