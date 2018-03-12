package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryDao implements Dao<Meal> {

    private List<Meal> storage = new ArrayList<>();

    public InMemoryDao() {
        storage.addAll(MealsUtil.meals);
    }
    @Override
    public void add(Meal meal) {
        storage.add(meal);
    }
    @Override
    public Meal get(int id) {
        return storage.stream().filter(m -> m.getId() == id).findFirst().get();
    }
    @Override
    public void update(Meal meal) {
        for (Meal m : storage) {
            if (m.getId() == meal.getId()) {
                int i = storage.indexOf(m);
                storage.set(i, meal);
            }
        }
    }
    @Override
    public void delete(int  id) {
        for (Meal m : storage) {
            if (m.getId() == id) {
                storage.remove(m);
                return;
            }
        }
    }
    @Override
    public List<Meal> getAll() {
        return storage;
    }
}
