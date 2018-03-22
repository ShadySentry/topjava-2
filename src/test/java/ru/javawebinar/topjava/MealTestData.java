package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class MealTestData {

   public static List<Meal> meals;
   public static final int COUNT_MEAL = 11;
   public static final  LocalDateTime START_DATE_TIME = LocalDateTime.of(2018,01,01,13,00,00);
   public static final  LocalDateTime STOP_DATE_TIME = LocalDateTime.of(2018,01,02,13,00,00);
   public static final  LocalDate START_DATE = LocalDate.of(2018,01,02);
   public static final  LocalDate STOP_DATE = LocalDate.of(2018,01,03);



    public static Meal MEAL_1 = new Meal(100002,LocalDateTime.of(2018, Month.JANUARY, 1, 10, 0), "Завтрак", 500);
    public static Meal MEAL_2 = new Meal(100003,LocalDateTime.of(2018, Month.JANUARY, 1, 13, 0), "Обед", 1000);
    public static Meal MEAL_3 =  new Meal(100004,LocalDateTime.of(2018, Month.JANUARY, 1, 20, 0), "Ужин", 500);

    public static Meal MEAL_4 =  new Meal(100005,LocalDateTime.of(2018, Month.JANUARY, 2, 10, 0), "Завтрак", 1000);
    public static Meal MEAL_5 = new Meal(100006,LocalDateTime.of(2018, Month.JANUARY, 2, 13, 0), "Обед", 500);
    public static Meal MEAL_6 =  new Meal(100007,LocalDateTime.of(2018, Month.JANUARY, 2, 20, 0), "Ужин", 510);

    public static Meal MEAL_7 = new Meal(100006,LocalDateTime.of(2018, Month.JANUARY, 3, 13, 0), "Обед", 555);
    public static Meal MEAL_8 =  new Meal(100007,LocalDateTime.of(2018, Month.JANUARY, 3, 20, 0), "Ужин", 666);

    public static Meal MEAL_9 =  new Meal(100005,LocalDateTime.of(2018, Month.JANUARY, 4, 10, 0), "Завтрак", 1231);
    public static Meal MEAL_10 = new Meal(100006,LocalDateTime.of(2018, Month.JANUARY, 4, 13, 0), "Обед", 236);
    public static Meal MEAL_11 =  new Meal(100007,LocalDateTime.of(2018, Month.JANUARY, 4, 20, 0), "Ужин", 896);




    public static Meal MEAL_TEST =  new Meal(100004,LocalDateTime.of(2018, Month.JANUARY, 2, 20, 0), "Test", 999);
    public static Meal MEAL_TEST2 =  new Meal(777777,LocalDateTime.of(2018, Month.JUNE, 10, 20, 0), "Test2", 777);


    {
        meals.add(MEAL_1);
        meals.add(MEAL_2);
        meals.add(MEAL_3);
        meals.add(MEAL_4);
        meals.add(MEAL_5);
        meals.add(MEAL_6);
        meals.add(MEAL_7);
        meals.add(MEAL_8);
        meals.add(MEAL_9);
        meals.add(MEAL_10);
        meals.add(MEAL_11);

    }


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields().isEqualTo(expected);
    }
}
