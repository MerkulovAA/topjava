package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MealMemoryDao implements Dao<Meal> {

    private static final Logger log = getLogger(MealMemoryDao.class);

    private final List<Meal> meals;

    private static final AtomicInteger id = new AtomicInteger(0);

    private volatile static MealMemoryDao instance;

    private MealMemoryDao() {
        this.meals = new CopyOnWriteArrayList<>(Arrays.asList(
                new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        ));
    }

    public static MealMemoryDao getInstance() {
        if (instance == null) {
            synchronized (MealMemoryDao.class) {
                if (instance == null) {
                    instance = new MealMemoryDao();
                }
            }
        }
        return instance;
    }

    @Override
    public List<Meal> getAll() {
        log.info("get all meals");
        return meals;
    }

    @Override
    public void create(Meal object) {
        log.info("create object meal");
        object.setId(getGenerateID());
        meals.add(object);
    }

    @Override
    public void update(Meal object) {
        log.info("update object meal");
        Meal oldMeal = getById(object.getId());
        if (oldMeal != null) {
            oldMeal.setDescription(object.getDescription());
            oldMeal.setDateTime(object.getDateTime());
            oldMeal.setCalories(object.getCalories());
        }

    }

    @Override
    public void delete(Meal object) {
        log.info("delete object meal");
        meals.remove(object);
    }

    @Override
    public Meal getById(int id) {
        log.info("get by id object meal");
        for (Meal meal : meals) {
            if (meal.getId() == id) {
                return meal;
            }
        }
        return null;
    }

    private int getGenerateID() {
        log.info("generate id for object");
        if (id.get() == 0) {
            id.set(meals.stream().map(Meal::getId).max(Integer::compareTo).orElse(0));
        }
        return id.incrementAndGet();
    }
}
