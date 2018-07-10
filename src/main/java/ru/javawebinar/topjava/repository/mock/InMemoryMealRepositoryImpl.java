package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {

    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);

    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();

    private AtomicInteger counter = new AtomicInteger(0);

    public InMemoryMealRepositoryImpl() {
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500), 1);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000), 1);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500), 2);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000), 3);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500), 1);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510), 2);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            if(repository.get(userId) == null) {
                Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();
                mealMap.put(meal.getId(), meal);
                repository.put(userId, mealMap);
            } else {
                repository.get(userId).put(meal.getId(), meal);
            }
            return meal;
        } else if(repository.get(userId) != null && repository.get(userId).get(meal.getId()) != null) {
            return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {}", id);
        return repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {}", id);
        return repository.get(userId).get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll");
        return getSorted(userId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getFilterByDate(LocalDate start, LocalDate end, int userId) {
        return getSorted(userId)
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), start, end))
                .collect(Collectors.toList());
    }

    private Stream<Meal> getSorted(int userId) {
        return repository.get(userId).values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed());
    }
}

