package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface MealRepository {
    Meal save(Meal meal, int userId) throws NotFoundException;

    boolean delete(int id, int userId);

    Meal get(int id, int userId);

    Collection<Meal> getAll(int userId);

    List<Meal> getFilterByTime(LocalTime start, LocalTime end, int userId);

    List<Meal> getFilterByDate(LocalDate start, LocalDate end, int userId);
}
