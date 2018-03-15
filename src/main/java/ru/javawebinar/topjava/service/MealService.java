package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface MealService {

    Meal save(Meal user, int userid);

    void delete(int id, int userid) throws NotFoundException;

    Meal get(int id, int userid) throws NotFoundException;

    void update(Meal meal, int userid);

    List<Meal> getAll(int userid);

    List<Meal> getAllByDateTime(int userid, LocalDateTime start, LocalDateTime stop);
}