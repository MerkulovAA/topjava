package ru.javawebinar.topjava.service;


import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.GenericDao;
import ru.javawebinar.topjava.model.Meal;
import java.util.List;
import static org.slf4j.LoggerFactory.getLogger;

public class MealService {

    private static final Logger log = getLogger(MealService.class);

    private GenericDao<Meal> dao;

    public MealService(GenericDao<Meal> dao) {
        this.dao = dao;
    }

    public List<Meal> getAll() {
        log.info("getAll in service");
        return dao.getAll();
    }


}
