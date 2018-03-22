package ru.javawebinar.topjava.service;



import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
;

@ContextConfiguration( {"classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"}
)
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService mealService;

    @Test
    public void get() {
        Meal meal = mealService.get(100005,1);
        assertMatch(meal, MEAL_4);
    }

    @Test(expected = NotFoundException.class)
    public void getNotExist() {
        Meal meal = mealService.get(99999,1);
    }

    @Test
    public void delete() {
        mealService.delete(MEAL_3.getId(), 1);
        List<Meal> meals2 =  mealService.getAll(1);
        Assert.assertEquals(COUNT_MEAL - 1, meals2.size());
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        mealService.delete(999999, 1);
        List<Meal> meals2 =  mealService.getAll(1);
        Assert.assertEquals(COUNT_MEAL - 1, meals2.size());
    }


    @Test
    public void getBetweenDates() {
        List<Meal> meals = mealService.getBetweenDates(START_DATE, STOP_DATE, 1);
        Assert.assertEquals(meals.size(), 5);
        System.out.println();
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> meals = mealService.getBetweenDateTimes(START_DATE_TIME, STOP_DATE_TIME ,1);
        Assert.assertEquals(meals.size(), 4);
        System.out.println();
    }

    @Test
    public void getAll() {

        List<Meal> all = mealService.getAll(1);
        Assert.assertEquals(all.size(), COUNT_MEAL);
    }

    @Test
    public void getAllUserNotFound() {

        List<Meal> all = mealService.getAll(999);
        Assert.assertEquals(all.size(), COUNT_MEAL);
    }


    @Test
    public void update() {
        mealService.create(MEAL_TEST,1);
        Meal meal = mealService.get(100004,1);
        assertMatch(meal,MEAL_TEST);
    }

    @Test()
    public void updateNotExist() {
        mealService.create(MEAL_TEST2,1);
    }

    @Test
    public void create() {
        Meal meal = new Meal(LocalDateTime.of(2018, 01 , 01,10 ,00),"Обед", 999);
        mealService.create(meal,1);
        System.out.println();
    }
}
