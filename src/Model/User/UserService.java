package Model.User;
import java.util.Collections;
import java.util.List;
import DAL.IRepository;

/**
 * Created by Karina on 19.02.2016.
 */
public class UserService {

    private IRepository<User> repository;

    public UserService(IRepository<User> userRepository){
        setUserRepository(userRepository);
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

    public List<User> getAdministrators(){
        return Collections.unmodifiableList(repository.getAllMatching((s)->s instanceof Admin));
    }

    public List<User> getIssuers(){
        return Collections.unmodifiableList(repository.getAllMatching((s)->s instanceof Issuer));
    }

    public List<User> getDeveloper(){
        return Collections.unmodifiableList(repository.getAllMatching((s)->s instanceof Developer));
    }


    // is de user name uniek?
    public void addUser(User user){
        String userName =  user.getUserName();
        if (!repository.getAllMatching((s)->s.getUserName() == userName).isEmpty()){
            throw new IllegalArgumentException("This user already exists. Choose another user name.");
        } else {
            repository.insert(user);
        }
    }

    public void updateUser(User user){
        repository.update(((s)->s.getUserName() == user.getUserName()), user);
    }

    public void deleteUser(User user){
        repository.delete(user);
    }

    public User getUser(String userName){
        return repository.getOne((s)->s.getUserName()==userName);
    }
}
