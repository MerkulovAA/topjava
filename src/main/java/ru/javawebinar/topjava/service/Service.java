package ru.javawebinar.topjava.service;

import java.io.Serializable;
import java.util.Collection;


/**
 * Interface for defining service functions.
 * @param <K> - id
 * @param <V> - object meal
 */
public interface Service<K, V extends Serializable> {

    /**
     * Method for get objects
     * @return all list objects
     */
    Collection<V> getAll();

    /**
     * Method for storing a new object in the repository
     * @param object saved object
     */
    V create(V object);

    /**
     * Method for updating an object in the repository
     * @param object updated object
     */
    V update(V object);

    /**
     * Method for removing an object from the storage
     * @param id - id by object to be deleted
     */
    void delete(Integer id);

    /**
     * Method for obtaining an object by id
     * @param id id of the object
     * @return object return
     */
    V getById(Integer id);

}
