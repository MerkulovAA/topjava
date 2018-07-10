package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<MealWithExceed> getAll() {
        log.info("getAll");
        return service.getAll(getAuthUserId());

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

    public List<MealWithExceed> getFilterByDateAndTime(LocalDate startDate, LocalDate endDate,
                                                       LocalTime startTime, LocalTime endTime) {
        LocalDate startLocalDate = startDate == null ? LocalDate.MIN : startDate;
        LocalDate endLocalDate = endDate == null ? LocalDate.MAX : endDate;
        LocalTime startLocalTime = startTime == null ? LocalTime.MIN : startTime;
        LocalTime endLocalTime = endTime == null ? LocalTime.MAX : endTime;
        return service.getFilterDate(startLocalDate, endLocalDate, getAuthUserId()).stream()
                .filter(mealWithExceed -> DateTimeUtil.isBetween(mealWithExceed.getDateTime().toLocalTime(),
                        startLocalTime, endLocalTime))
                .sorted(Comparator.comparing(MealWithExceed::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}