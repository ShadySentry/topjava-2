package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);


    private MealRepository repository;

    private MealRestController mealRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {

        ApplicationContext aptx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = (MealRestController) aptx.getBean("mealRestController");
        super.init(config);
        repository = new InMemoryMealRepositoryImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");

        String startdate = request.getParameter("startdate");
        String enddate = request.getParameter("enddate");
        String starttime = request.getParameter("startime");
        String endtime = request.getParameter("endtime");

        if (startdate != null || enddate != null || starttime != null || endtime != null) {

            LocalDate sd = startdate.equals("") ? LocalDate.MIN : LocalDate.parse(startdate);
            LocalDate ed = enddate.equals("") ? LocalDate.MAX : LocalDate.parse(enddate);
            LocalTime st = starttime.equals("")? LocalTime.MIN : LocalTime.parse(starttime);
            LocalTime et = endtime.equals("") ? LocalTime.MAX : LocalTime.parse(endtime);

            LocalDateTime startLocalDateTIme = LocalDateTime.of(sd, st);
            LocalDateTime endLocalDateTime = LocalDateTime.of(ed, et);
            List<MealWithExceed> all = mealRestController.getAllByDateTime(startLocalDateTIme, endLocalDateTime);
            request.setAttribute("meals", all);

            request.getRequestDispatcher("meals.jsp").forward(request, response);

        }

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        mealRestController.save(meal);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals", mealRestController.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
