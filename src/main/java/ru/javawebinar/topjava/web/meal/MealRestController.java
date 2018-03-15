package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll() {
        log.info("getAll");
        return  MealsUtil.getFilteredWithExceeded(service.getAll(AuthorizedUser.id()),MealsUtil.DEFAULT_CALORIES_PER_DAY, LocalTime.MIN, LocalTime.MAX);

    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id,AuthorizedUser.id());
    }

    public Meal save(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.save(meal, AuthorizedUser.id());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, AuthorizedUser.id());
    }

    public void update(Meal meal) {
        log.info("update {} with id={}", meal, meal.getId());
        assureIdConsistent(meal, meal.getId());
        service.update(meal, AuthorizedUser.id());
    }

    public List<MealWithExceed> getAllByDateTime(LocalDateTime start, LocalDateTime stop){
        log.info("getAllByDateTime", AuthorizedUser.id(),start,stop);
        List<Meal> meals = service.getAll(AuthorizedUser.id());
        List<MealWithExceed> withExceeds = MealsUtil.getFilteredWithExceeded(meals, MealsUtil.DEFAULT_CALORIES_PER_DAY, start.toLocalTime(), stop.toLocalTime());
       return  withExceeds.stream().filter(e -> e.getDateTime().isAfter(start) && e.getDateTime().isBefore(stop))
                .collect(Collectors.toList());

    }

}