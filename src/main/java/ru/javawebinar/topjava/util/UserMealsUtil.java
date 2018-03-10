package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2018, Month.JANUARY, 1, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2018, Month.JANUARY, 1, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2018, Month.JANUARY, 1, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2018, Month.JANUARY, 2, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2018, Month.JANUARY, 2, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2018, Month.JANUARY, 3, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2018, Month.JANUARY, 3, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2018, Month.FEBRUARY, 3, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2018, Month.FEBRUARY, 5, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2018, Month.FEBRUARY, 5, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2018, Month.FEBRUARY, 6, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2018, Month.FEBRUARY, 6, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2018, Month.FEBRUARY, 6, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2018, Month.FEBRUARY, 10, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2018, Month.FEBRUARY, 10, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2018, Month.FEBRUARY, 10, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(8, 0), LocalTime.of(22, 0), 1800);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesByDate = new HashMap<>();
        mealList.forEach(meal -> caloriesByDate.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum));

        List<UserMealWithExceed> mealsWithExceeded = new ArrayList<>();
        mealList.forEach(meal -> {
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                mealsWithExceeded.add(createExceedUserMeal(meal, caloriesByDate.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        });
        return mealsWithExceeded;
    }

    public static UserMealWithExceed createExceedUserMeal(UserMeal meal, boolean exceeded) {
        return new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded);
    }
}
