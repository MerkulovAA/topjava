package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import static org.slf4j.LoggerFactory.getLogger;

public class MealMemoryDao implements Dao<Integer, Meal> {

    private static final Logger log = getLogger(MealMemoryDao.class);

    private final Map<Integer, Meal> meals;

    private static final AtomicInteger id = new AtomicInteger(0);

    private volatile static MealMemoryDao instance;

    private MealMemoryDao() {
        this.meals = new ConcurrentHashMap<>();
        create(new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        create(new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        create(new Meal(3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        create(new Meal(4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        create(new Meal(5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        create(new Meal(6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
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
    public synchronized Collection<Meal> getAll() {
        log.info("get all meals");
        return meals.values();
    }

    @Override
    public synchronized Meal create(Meal object) {
        log.info("create object meal");
        Integer id = getGenerateId();
        Meal meal = new Meal(id, object.getDateTime(), object.getDescription(), object.getCalories());
        meals.put(id, meal);
        return meal;
    }

    @Override
    public synchronized void update(Meal object) {
        log.info("update object meal");
        meals.replace(object.getId(), new Meal(object.getId(), object.getDateTime(),
                object.getDescription(), object.getCalories()));
    }

    @Override
    public synchronized void delete(Meal object) {
        log.info("delete object meal");
        meals.remove(object.getId(), object);
    }

    @Override
    public synchronized Meal getById(Integer id) {
        log.info("get by id object meal");
        return meals.get(id);
    }

    private synchronized int getGenerateId() {
        log.info("generate id for object");
        return id.incrementAndGet();
    }
}
