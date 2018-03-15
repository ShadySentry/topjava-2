package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface MealRepository {

    Meal save(Meal meal, int userid);

    void delete(int id, int userid);

    Meal get(int id, int userid);

    List<Meal> getAll(int userid);

    List<Meal> getAllByDateTime(int userid, LocalDateTime start, LocalDateTime end);
}
