package ru.javawebinar.topjava;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Spring_test {

    public static void main(String[] args) {


        ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-mvc.xml", "spring/spring-db.xml", "spring/spring-security.xml");

        System.out.println();
    }
}
