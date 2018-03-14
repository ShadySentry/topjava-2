package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.List;

public interface MealRepository {

    Meal save(Meal meal);

    void delete(int id, int userid);

    Meal get(int id, int userid);

    List<Meal> getAll(int userid);
}
