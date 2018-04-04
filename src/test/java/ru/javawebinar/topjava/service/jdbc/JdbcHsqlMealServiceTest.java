package ru.javawebinar.topjava.service.jdbc;


import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

@ActiveProfiles(profiles =  { Profiles.JDBC, Profiles.HSQL_DB })
public class JdbcHsqlMealServiceTest  extends AbstractMealServiceTest{
}