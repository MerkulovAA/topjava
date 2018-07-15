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

import static ru.javawebinar.topjava.data.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-jdbc.xml",
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
        Meal meal = service.get(MEAL_USER_TWO.getId(), USER_ID);
        assertMatch(meal, MEAL_USER_TWO);
    }

    @Test(expected = NotFoundException.class)
    public void getAnotherUserMeal() {
        service.get(MEAL_ADMIN_ONE.getId(), USER_ID);
    }

    @Test
    public void delete() {
        service.delete(MEAL_USER_FOUR.getId(), USER_ID);
        assertMatch(service.getAll(USER_ID), MEALS_USER_AFTER_DELETE);
    }

    @Test(expected = NotFoundException.class)
    public void deleteAnotherUserMeal() {
        service.delete(MEAL_USER_ONE.getId(), ADMIN_ID);
    }

    @Test
    public void getBetweenDateTimes() {
        assertMatch(service.getBetweenDateTimes(START_DATE_TIME, END_DATE_TIME, USER_ID), MEALS_USER_GET_BETWEEN);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), MEALS_USER_SORT);
    }

    @Test
    public void update() {
        Meal updated = new Meal(MEAL_USER_FOUR);
        updated.setDateTime(LocalDateTime.of(2015, Month.APRIL, 26, 10, 0));
        updated.setDescription("Завтрак");
        updated.setCalories(750);
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_USER_FOUR.getId(), USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateAnotherUserMeal() {
        Meal updated = new Meal(MEAL_ADMIN_ONE);
        updated.setDateTime(LocalDateTime.of(2015, Month.AUGUST, 28, 13, 0));
        updated.setDescription("Обед");
        updated.setCalories(1258);
        service.update(updated, USER_ID);
    }

    @Test
    public void create() {
        service.create(MEAL_CREATE, USER_ID);
        assertMatch(service.getAll(USER_ID), MEALS_USER_AFTER_CREATE);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(1, USER_ID);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateDateTimeAndUserIdCreate() {
        service.create(MEAL_DUBLICATE, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(1, USER_ID);
    }
}