package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.getAuthUserId;

@Controller
public class MealRestController {

    private static final Logger log = getLogger(MealRestController.class);

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Collection<MealWithExceed> getAll() {
        log.info("getAll");
        return MealsUtil.getWithExceeded(service.getAll(getAuthUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);

    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, getAuthUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal,getAuthUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, getAuthUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, getAuthUserId());
    }

    public List<MealWithExceed> getFilterByTime(LocalTime start, LocalTime end) {
        return MealsUtil.getWithExceeded(service.getFilterTime(start, end, getAuthUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealWithExceed> getFilterByDate(LocalDate start, LocalDate end) {
        return MealsUtil.getWithExceeded(service.getFilterDate(start, end, getAuthUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);

    }
}