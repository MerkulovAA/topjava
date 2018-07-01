package ru.javawebinar.topjava.service;


import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.Dao;
import ru.javawebinar.topjava.model.Meal;
import java.util.Collection;
import static org.slf4j.LoggerFactory.getLogger;

public class MealService implements Service<Integer, Meal> {

    private static final Logger log = getLogger(MealService.class);

    private Dao<Integer, Meal> dao;

    public MealService(Dao<Integer, Meal> dao) {
        this.dao = dao;
    }

    @Override
    public Collection<Meal> getAll() {
        log.info("getAll in service");
        return dao.getAll();
    }

    @Override
    public Meal create(Meal meal) {
        log.info("create in service");
        return dao.create(meal);
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
    public Meal getById(Integer id) {
        log.info("getById in service");
        return dao.getById(id);
    }
}
