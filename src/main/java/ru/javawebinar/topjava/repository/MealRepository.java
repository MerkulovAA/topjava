package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MealRepository {
    Meal save(Meal meal, int userId) throws NotFoundException;

    boolean delete(int id, int userId);

    Meal get(int id, int userId);

    List<Meal> getAll(int userId);

    List<Meal> getFilterByDate(LocalDate start, LocalDate end, int userId);

    List<Meal> getFilterByTime(LocalDate startDate, LocalDate endDate, LocalTime start, LocalTime end, int userId);
}
