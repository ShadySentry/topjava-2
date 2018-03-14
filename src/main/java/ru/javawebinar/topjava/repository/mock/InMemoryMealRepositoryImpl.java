package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {

    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        log.info("save {}", meal.getId());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public void delete(int id, int userid) {
        log.info("detele {}", id);
        Meal m =  repository.values().stream().filter(e -> e.getId() == id && e.getUserid() == userid).findFirst().get();
        repository.remove(m);
    }

    @Override
    public Meal get(int id, int userid) {
        log.info("get {}", id);
        return repository.values().stream().filter(e -> e.getId() == id && e.getUserid() == userid).findFirst().get();
    }

    @Override
    public List<Meal> getAll(int userid){
        log.info("getAll");
        return repository.values().stream().filter( e -> e.getId() == userid).collect(Collectors.toList());
    }
}

