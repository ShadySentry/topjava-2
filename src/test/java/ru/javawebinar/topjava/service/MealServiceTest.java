package ru.javawebinar.topjava.service;

import org.junit.AfterClass;
import org.junit.AssumptionViolatedException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.rules.Timeout;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Rule
    public TimeRule timeRule = new TimeRule();

    private static StringBuilder stringBuilder = new StringBuilder();

    @AfterClass
    public static void endTest(){

        System.out.println(stringBuilder);
    }

    class TimeRule implements TestRule {

        @Override
        public Statement apply(Statement base, Description description) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {

                    long startTume = System.nanoTime();
                    base.evaluate();
                    long stopTime = System.nanoTime();
                    double resultTime = (double)stopTime - startTume;

                    stringBuilder.append("Тест  :" + description.getMethodName() + "выполнен за :" + resultTime + "\n");

                    System.out.println("-------------------------------------------------");
                    System.out.println("Название  :" + description.getClassName());
                    System.out.println("Название теста :" + description.getMethodName());
                    System.out.println("Время теста :" + resultTime + "сек");
                    System.out.println("-------------------------------------------------");
                }
            };
        }
    }

    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private MealService service;

    @Test
    public void testDelete() throws Exception {

        service.delete(MEAL1_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        service.delete(99999, 999);
    }

    @Test
    public void testSave() throws Exception {
        Meal created = getCreated();
        service.create(created, USER_ID);
        assertMatch(service.getAll(USER_ID), created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void testGet() throws Exception {
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(999, ADMIN_ID);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL1_ID, USER_ID), updated);
    }


    @Test
    public void testGetAll() throws Exception {
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(service.getAll(USER_ID), MEALS);
    }

    @Test
    public void testGetBetween() throws Exception {
        assertMatch(service.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID), MEAL3, MEAL2, MEAL1);
    }
}