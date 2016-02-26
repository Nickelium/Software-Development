package Model.User;
import java.util.Collections;
import java.util.List;

/**
 * Created by Karina on 19.02.2016.
 */
public class UserService {

    private IRepository<User> repository;

    public UserService(){
        // TODO add generic Repository
        // IRepository<User> userRepository = new Repository<User>
        // setUserRepository(userRepository);
    }

    /**
     * Returns an list with all users.
     * @return An list with all users.
     */
    public List<User> getUserList() {
        return Collections.unmodifiableList(repository.getAll());
    }

    /**
     * Sets the IRepository object with all users.
     * @param userRepository The new IRepository object with users.
     */
    private void setUserRepository(IRepository<User> userRepository) {
        this.repository = userRepository;
    }

    /**
     * Prompts a query to the UserRepository returning all users being an instance of Admin
     * @return A list object with all administrators saved in the UserRepository
     */
    public List<User> getAdministrators(){
        return Collections.unmodifiableList(repository.getAllMatching((s)->s instanceof Admin));
    }

    /**
     * Prompts a query to the UserRepository returning all users being an instance of issuer
     * @return A list object with all issuers saved in the UserRepository
     */
    public List<User> getIssuers(){
        return Collections.unmodifiableList(repository.getAllMatching((s)->s instanceof Issuer));
    }

    /**
     * Prompts a query to the UserRepository returning all users being an instance of Developer
     * @return A list object with all developers saved in the UserRepository
     */
    public List<User> getDeveloper(){
        return Collections.unmodifiableList(repository.getAllMatching((s)->s instanceof Developer));
    }


    /**
     * Adds a new user to the user repository if there doesn't exist a user object with
     * the same username in the User Repository yet.
     *
     * @throws IllegalArgumentException
     * Throws an IllegalArgumentException in case there already exists a User object
     * in the User Repository with the same username.
     *
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param userName The user name of the user.
     * @param middleName The middle name of the user.
     *
     * @return The newly created user.
     */
    public User addUser(String firstName, String lastName, String userName, String middleName){
        User user = new User(firstName,lastName,userName,middleName);
        if (!repository.getAllMatching((s)->user.getUserName() == userName).isEmpty()){
            throw new IllegalArgumentException("This user already exists. Choose another user name.");
        } else {
            repository.insert(user);
            return user;
        }
    }


    /**
     * Adds a new user to the user repository if there doesn't exist a user object with
     * the same username in the User Repository yet.
     *
     * @throws IllegalArgumentException
     * Throws an IllegalArgumentException in case there already exists a User object
     * in the User Repository with the same username.
     *
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param userName The user name of the user.
     *
     * @return The newly created user.
     */
    public User addUser(String firstName, String lastName, String userName){
        User user = new User(firstName,lastName,userName);
        if (!repository.getAllMatching((s)->user.getUserName() == userName).isEmpty()){
            throw new IllegalArgumentException("This user already exists. Choose another user name.");
        } else {
            repository.insert(user);
            return user;
        }
    }


    /**
     * Deletes a given user object from the user repository by calling a delete
     * statement of the repository.
     *
     * @param user the user object that needs to be deleted from the user repository.
     */
    public void deleteUser(User user){
        repository.delete(user);
    }

    /**
     * Returns a user object from the user repository defined by the unique user name.
     *
     * @param userName the unique username of which we want to retrieve the user object from the user repository
     * @return the user object from the user repository with given username.
     */
    public User getUser(String userName){
        return repository.getOne((s)->s.getUserName()==userName);
    }
}
