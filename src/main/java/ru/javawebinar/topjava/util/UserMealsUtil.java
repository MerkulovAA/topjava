package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2500));
        System.out.println(getFilteredWithExceededByStream(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(getFilteredWithExceededByStream(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2500));
    }

    private static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime,
                                                                    LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dateToCalories = new HashMap<>();
        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();
        mealList.forEach(userMeal ->
            dateToCalories.merge(userMeal.getLocalDate(), userMeal.getCalories(), (Integer::sum)));
        mealList.forEach(userMeal -> {
            if (TimeUtil.isBetween(userMeal.getLocalTime(), startTime, endTime)) {
                userMealWithExceeds.add(new UserMealWithExceed(userMeal.getDateTime(),
                        userMeal.getDescription(), userMeal.getCalories(),
                        dateToCalories.get(userMeal.getLocalDate()) > caloriesPerDay));
            }
        });
        return userMealWithExceeds;
    }


    private static List<UserMealWithExceed> getFilteredWithExceededByStream(List<UserMeal> mealList, LocalTime startTime,
                                                                            LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> map = mealList.stream()
                .collect(Collectors.groupingBy(UserMeal::getLocalDate, Collectors.summingInt(UserMeal::getCalories)));
        return mealList.stream()
                .filter(userMeal -> TimeUtil.isBetween(userMeal.getLocalTime(), startTime, endTime))
                .map(userMeal -> new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                        map.get(userMeal.getLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
