package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface MealService {

    Meal create(Meal meal, int userId) throws NotFoundException;

    void delete(int id, int userId) throws NotFoundException;

    Meal get(int id, int userId) throws NotFoundException;

    void update(Meal meal, int userId);

    Collection<Meal> getAll(int userId);

    List<Meal> getFilterTime(LocalTime start, LocalTime end, int userId);

    List<Meal> getFilterDate(LocalDate start, LocalDate end, int userId);
}