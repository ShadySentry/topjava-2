package ru.javawebinar.topjava;

import org.hsqldb.ParserRoutine;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class Test {

    public static void main(String[] args) {

        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
//        ctx.getEnvironment().setActiveProfiles(Profiles.POSTGRES_DB, Profiles.JDBC);
//        ctx.getEnvironment().setActiveProfiles(Profiles.POSTGRES_DB, Profiles.JPA);
        ctx.getEnvironment().setActiveProfiles(Profiles.POSTGRES_DB, Profiles.DATAJPA);
        ctx.load("spring/spring-app.xml", "spring/spring-db.xml");
        ctx.refresh();

        System.out.println();
    }
}
