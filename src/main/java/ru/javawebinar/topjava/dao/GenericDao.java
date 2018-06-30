package ru.javawebinar.topjava.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Interface for defining data storage functions.
 * @param <T> - type of persistence object
 */

public interface GenericDao<T extends Serializable> {

    /**
     * Method for get objects
     * @return all list objects
     */
    List<T> getAll();
}
