package ru.javawebinar.topjava.service.jdbc;


import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

@ActiveProfiles(profiles =  { Profiles.JDBC, Profiles.POSTGRES_DB })
public class JdbcMealServiceTest extends AbstractMealServiceTest {


}