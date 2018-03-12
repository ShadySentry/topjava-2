package ru.javawebinar.topjava.dao;

import java.util.List;

public interface Dao<T> {

    void add(T t);

    T get(int id);

    void update(T t);

    void delete(int i);

    List<T> getAll();
}
