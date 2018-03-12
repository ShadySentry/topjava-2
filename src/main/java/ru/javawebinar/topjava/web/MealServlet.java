package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.Dao;
import ru.javawebinar.topjava.dao.InMemoryDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.List;

public class MealServlet extends HttpServlet {

    private Dao<Meal> mealstorage = new InMemoryDao();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("test") != null) {
            String s = req.getParameter("datetime");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(s, formatter);
            String desc = req.getParameter("description");
            int calories = Integer.valueOf(req.getParameter("calories"));
            String id = req.getParameter("id");
            if (id.equals("")){
                mealstorage.add(new Meal(MealsUtil.counter++, dateTime, desc, calories));
            }else {

                int idd = Integer.parseInt(id);
                mealstorage.update(new Meal(idd,dateTime,desc,calories));
            }
        }
        List<MealWithExceed> listMeals = MealsUtil.getFilteredWithExceeded(mealstorage.getAll(), LocalTime.of(10, 00), LocalTime.of(23, 00), 2000);
        req.setAttribute("meals", listMeals);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String type = req.getParameter("type");
        if (type.equals("delete")) {
            int id = Integer.valueOf(req.getParameter("id"));
            mealstorage.delete(id);
        } else if (type.equals("edit")) {
            int id = Integer.valueOf(req.getParameter("id"));
            Meal meal = mealstorage.get(id);
            req.setAttribute("meal", meal);
            req.getRequestDispatcher("createmeal.jsp").forward(req, resp);
        } else if (type.equals("create")) {
            req.getRequestDispatcher("createmeal.jsp").forward(req, resp);
        }
        resp.sendRedirect("/meals");
    }
}
