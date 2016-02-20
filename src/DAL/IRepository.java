package DAL;

import java.util.List;
import java.util.function.Predicate;

/**
 * Interface for data access
 */
public interface IRepository<T> {

    /**
     * Method for retrieving one object in the collection matching the given criteria.
     *
     * @param criteria The criteria on which to filter the objects.
     * @return The first or default object matching the criteria.
     */
    T getOne(Predicate<T> criteria);

    /**
     * Method for retrieving all objects in the collection matching the given criteria.
     *
     * @param criteria The criteria on which to filter the objects.
     * @return A list of all objects matching the criteria.
     */
    List<T> getAllMatching(Predicate<T> criteria);

    /**
     * Method for retrieving all objects in the collection.
     *
     * @return A list of all the objects in the collection.
     */
    List<T> getAll();

    /**
     * Method for inserting an object in the collection.
     *
     * @param object The object to insert in the collection.
     * @throws RuntimeException The insertion of the element failed.
     */
    void insert(T object) throws RuntimeException;

    /**
     * Method for updating an object in the collection.
     *
     * @param criteria The criteria on which to select te object to update.
     * @param object The object to update
     * @throws RuntimeException The updating of the element failed.
     */
    void update(Predicate<T> criteria, T object) throws RuntimeException;

    /**
     * Method for deleting an object in the collection.
     *
     * @param object The object to be deleted.
     * @throws RuntimeException The deletion of the element failed.
     */
    void delete(T object) throws RuntimeException;

    /**
     * Method for deleting all the objects in the collection.
     *
     * @throws RuntimeException The deletion of all the elements failed.
     */
    void deleteAll() throws RuntimeException;
}