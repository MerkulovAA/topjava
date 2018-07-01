package ru.javawebinar.topjava.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Interface for defining data storage functions.
 * @param <T> - type of persistence object
 */

public interface Dao<T extends Serializable> {

    /**
     * Method for get objects
     * @return all list objects
     */
    List<T> getAll();

    /**
     * Method for storing a new object in the repository
     * @param object saved object
     */
    void create(T object);

    /**
     * Method for updating an object in the repository
     * @param object updated object
     */
    void update(T object);

    /**
     * Method for removing an object from the storage
     * @param object an object to be deleted
     */
    void delete(T object);

    /**
     * Method for obtaining an object by id
     * @param id id of the object
     * @return object return
     */
    T getById(int id);

}
