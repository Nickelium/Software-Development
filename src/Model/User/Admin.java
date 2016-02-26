package Model.User;

import Model.User.User;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Karina on 19.02.2016.
 */
public class Admin extends User {

    private List<Right> rights = Arrays.asList(
            Right.createProject
    );

    /**
     * Creates a new Admin instance using the given strings.
     * @param firstName The first name of the user.
     * @param middleName The middle name of the user.
     * @param lastName The last name of the user.
     * @param userName The user name of the user.
     */
    public Admin(String firstName, String middleName, String lastName, String userName){
        super(firstName,middleName,lastName,userName);
    }

}
