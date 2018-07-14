package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.data.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;


    @Test
    public void get() {
        Meal meal = service.get(MEALS_USER.get(1).getId(), USER_ID);
        assertMatch(meal, MEALS_USER.get(1));
    }

    @Test(expected = NotFoundException.class)
    public void getAnotherUserMeal() {
        Meal meal = service.get(MEALS_ADMIN.get(1).getId(), USER_ID);
    }

    @Test
    public void delete() {
        List<Meal> mealsUser = getMealsUserAndSort(MEALS_USER);
        service.delete(mealsUser.get(0).getId(), USER_ID);
        mealsUser.remove(0);
        assertMatch(service.getAll(USER_ID), getMealsUserAndSort(mealsUser));
    }

    @Test(expected = NotFoundException.class)
    public void deleteAnotherUserMeal() {
        service.delete(MEALS_USER.get(1).getId(), ADMIN_ID);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> mealsUser = getBetweenDateTimesUserMeals(START_DATE_TIME, END_DATE_TIME, MEALS_USER);
        assertMatch(service.getBetweenDateTimes(START_DATE_TIME, END_DATE_TIME, USER_ID), mealsUser);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), getMealsUserAndSort(MEALS_USER));
    }

    @Test
    public void update() {
        Meal updated = new Meal(MEALS_USER.get(0));
        updated.setDateTime(LocalDateTime.of(2015, Month.APRIL, 26, 10, 0));
        updated.setDescription("Завтрак");
        updated.setCalories(750);
        service.update(updated, USER_ID);
        assertMatch(service.get(MEALS_USER.get(0).getId(), USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateAnotherUserMeal() {
        Meal updated = new Meal(MEALS_ADMIN.get(0));
        updated.setDateTime(LocalDateTime.of(2015, Month.AUGUST, 28, 13, 0));
        updated.setDescription("Обед");
        updated.setCalories(1258);
        service.update(updated, USER_ID);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2016, Month.MAY, 30, 10, 0),
                "Завтрак", 1250);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        List<Meal> mealsUser = getMealsUserAndSort(MEALS_USER);
        mealsUser.add(newMeal);
        assertMatch(service.getAll(USER_ID), getMealsUserAndSort(mealsUser));
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() {
        service.delete(1, USER_ID);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateDateTimeAndUserIdCreate() {
        service.create(new Meal(null, LocalDateTime.of(2015, Month.MAY, 30, 10, 0),
                "newMeal", 1500), USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(1, USER_ID);
    }
}