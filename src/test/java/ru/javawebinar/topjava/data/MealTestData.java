package ru.javawebinar.topjava.data;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final Integer MEAL_ID_USER = START_SEQ + 2;
    public static final Integer MEAL_ID_ADMIN = START_SEQ + 6;

    public static final LocalDateTime START_DATE_TIME = LocalDateTime.of(2015, Month.MAY, 30, 13, 0);
    public static final LocalDateTime END_DATE_TIME = LocalDateTime.of(2015, Month.JUNE, 30, 10, 0);

    public static final Meal  MEAL_USER_ONE = new Meal(MEAL_ID_USER, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 1000);
    public static final Meal  MEAL_USER_TWO = new Meal(MEAL_ID_USER + 1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal  MEAL_USER_THREE = new Meal(MEAL_ID_USER + 2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal  MEAL_USER_FOUR = new Meal(MEAL_ID_USER + 3, LocalDateTime.of(2015, Month.JUNE, 30, 10, 0), "Завтрак", 850);

    public static final Meal MEAL_ADMIN_ONE = new Meal(MEAL_ID_ADMIN, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Breakfast", 750);
    public static final Meal MEAL_ADMIN_TWO = new Meal(MEAL_ID_ADMIN + 1, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Lunch", 700);
    public static final Meal MEAL_ADMIN_THREE = new Meal(MEAL_ID_ADMIN + 2, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Dinner", 500);

    public static final Meal MEAL_AFTER_CREATE =
            new Meal(100009, LocalDateTime.of(2016, Month.MAY, 30, 10, 0), "Завтрак", 1250);

    public static final List<Meal> MEALS_USER_SORT = Arrays.asList(MEAL_USER_FOUR, MEAL_USER_THREE, MEAL_USER_TWO, MEAL_USER_ONE);

    public static final List<Meal> MEALS_USER_AFTER_DELETE = Arrays.asList(MEAL_USER_THREE, MEAL_USER_TWO, MEAL_USER_ONE);

    public static final List<Meal> MEALS_USER_GET_BETWEEN = Arrays.asList(MEAL_USER_FOUR, MEAL_USER_THREE, MEAL_USER_TWO);

    public static final List<Meal> MEALS_USER_AFTER_CREATE = Arrays.asList(
            MEAL_AFTER_CREATE, MEAL_USER_FOUR, MEAL_USER_THREE, MEAL_USER_TWO, MEAL_USER_ONE);

    public static final List<Meal> MEALS_ADMIN_SORT = Arrays.asList(MEAL_ADMIN_THREE, MEAL_ADMIN_TWO, MEAL_ADMIN_ONE);

    public static final Meal MEAL_DUBLICATE =
            new Meal(null, LocalDateTime.of(2015, Month.JUNE, 30, 10, 0), "Завтрак", 850);

    public static final Meal MEAL_CREATE =
            new Meal(null, LocalDateTime.of(2016, Month.MAY, 30, 10, 0), "Завтрак", 1250);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}


