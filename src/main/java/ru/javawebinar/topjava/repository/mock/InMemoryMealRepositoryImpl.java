package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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


        MealsUtil.MEALS.stream().forEach( e -> this.save(e, 1));
//        repository.values().stream().filter(e -> e.getDate().getYear() == 2017).forEach(e -> e.setId(2));
    }

    @Override
    public Meal save(Meal meal, int userid) {
        log.info("save {}", meal.getId());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserid(userid);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public void delete(int id, int userid) {
        log.info("detele {}", id,userid);
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
        log.info("getAll",userid);
        return repository.values().stream().filter( e -> e.getUserid() == userid).collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAllByDateTime(int userid, LocalTime starttime, LocalTime endtime, LocalDate startdate , LocalDate enddate) {
        log.info("getAllByTime");
//        return getAll(userid).stream().filter(e -> e.getDateTime().compareTo(starttime) >= 0 && e.getDateTime().compareTo(stoptime) <= 0).collect(Collectors.toList());
        return getAll(userid).stream().filter(e -> e.getDate().compareTo(startdate) >= 0 && e.getDate().compareTo(enddate) <= 0 &&
                e.getTime().compareTo(starttime) >= 0 && e.getTime().compareTo(endtime) <= 0).collect(Collectors.toList());
    }
}

