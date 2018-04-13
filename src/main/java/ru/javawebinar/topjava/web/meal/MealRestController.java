package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(MealRestController.REST_URL)
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    static final String REST_URL = "/rest/meals";

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Meal get(@PathVariable("id") int id) {
        int userId = AuthorizedUser.id();
        log.info("get meal {} for user {}", id, userId);
        return service.get(id, userId);
    }

    @DeleteMapping("/{id}")
     public void delete(@PathVariable("id") int id) {
        int userId = AuthorizedUser.id();
        log.info("delete meal {} for user {}", id, userId);
        service.delete(id, userId);
    }

    @GetMapping()
    public List<MealWithExceed> getAll() {
        int userId = AuthorizedUser.id();
        log.info("getAll for user {}", userId);
        return MealsUtil.getWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay());
    }

    @PostMapping
    public Meal create(@RequestBody Meal meal) {
        int userId = AuthorizedUser.id();
        checkNew(meal);
        log.info("create {} for user {}", meal, userId);
        return service.create(meal, userId);
    }
    @PutMapping("/{id}")
    public void update(@RequestBody Meal meal,@PathVariable("id") int id) {
        int userId = AuthorizedUser.id();
        assureIdConsistent(meal, id);
        log.info("update {} for user {}", meal, userId);
        service.update(meal, userId);
    }

    /**
     * <ol>Filter separately
     * <li>by date</li>
     * <li>by time for every date</li>
     * </ol>
     */

    @GetMapping("/between")
    public List<MealWithExceed> getBetween(@RequestParam("startDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                           @RequestParam("startTime")@DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                                           @RequestParam("endDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate endDate,
                                           @RequestParam("endTime")@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)LocalTime endTime) {
        int userId = AuthorizedUser.id();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);
        List<Meal> mealsDateFiltered = service.getBetweenDates(
                startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                endDate != null ? endDate : DateTimeUtil.MAX_DATE, userId);
        return MealsUtil.getFilteredWithExceeded(mealsDateFiltered, AuthorizedUser.getCaloriesPerDay(),
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX
        );
    }
}