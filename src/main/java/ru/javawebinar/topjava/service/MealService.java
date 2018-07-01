package ru.javawebinar.topjava.service;


import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.Dao;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealService implements Service<Meal>{

    private static final Logger log = getLogger(MealService.class);

    private Dao<Meal> dao;

    public MealService(Dao<Meal> dao) {
        this.dao = dao;
    }

    @Override
    public List<Meal> getAll() {
        log.info("getAll in service");
        return dao.getAll();
    }

    @Override
    public void create(Meal meal) {
        log.info("create in service");
        dao.create(meal);
    }

    @Override
    public void update(Meal meal) {
        log.info("update in service");
        dao.update(meal);
    }

    @Override
    public void delete(Meal meal) {
        log.info("delete in service");
        dao.delete(meal);
    }

    @Override
    public Meal getById(int id) {
        log.info("getById in service");
        return dao.getById(id);
    }
}
