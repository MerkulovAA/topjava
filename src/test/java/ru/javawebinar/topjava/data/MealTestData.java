package ru.javawebinar.topjava.data;

import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final Integer MEAL_ID_USER = START_SEQ + 2;
    public static final Integer MEAL_ID_ADMIN = START_SEQ + 6;

    public static final LocalDateTime START_DATE_TIME = LocalDateTime.of(2015, Month.MAY, 30, 13, 0);
    public static final LocalDateTime END_DATE_TIME = LocalDateTime.of(2015, Month.JUNE, 30, 10, 0);

    public static final List<Meal> MEALS_USER = Arrays.asList(
            new Meal(MEAL_ID_USER, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 1000),
            new Meal(MEAL_ID_USER + 1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(MEAL_ID_USER + 2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(MEAL_ID_USER + 3, LocalDateTime.of(2015, Month.JUNE, 30, 10, 0), "Завтрак", 850));

    public static final List<Meal> MEALS_ADMIN = Arrays.asList(
            new Meal(MEAL_ID_ADMIN, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 750),
            new Meal(MEAL_ID_ADMIN + 1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 700),
            new Meal(MEAL_ID_ADMIN + 2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

    public static List<Meal> getMealsUserAndSort(List<Meal> meals) {
        return getAllFiltered(meal -> true, meals);
    }

    public static List<Meal> getBetweenDateTimesUserMeals(LocalDateTime start, LocalDateTime end, List<Meal> meals) {
        return getAllFiltered(meal ->  DateTimeUtil.isBetween(meal.getDateTime(), start, end), meals);
    }

    private static List<Meal> getAllFiltered(Predicate<Meal> filter, List<Meal> meals) {
        return CollectionUtils.isEmpty(meals) ? Collections.emptyList() :
                meals.stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
    }
}


