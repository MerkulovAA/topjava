package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.ContextSpringUtil;
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
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController controller;

    private ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) {
        log.debug("Init controller");
        try {
            super.init(config);
            appCtx = ContextSpringUtil.getOurInstance().getAppCtx();
            controller = appCtx.getBean(MealRestController.class);

        } catch (Exception e) {
            log.error("Error init controller: " + e.getMessage());
        }
        log.debug("servlet initialization is done");
    }

    @Override
    public void destroy() {
        super.destroy();
        appCtx.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if ("getFilterDateTime".equals(action)) {
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            LocalDate startLocalDate = startDate.equals("") ? null : LocalDate.parse(startDate);
            LocalDate endLocalDate = endDate.equals("") ? null: LocalDate.parse(endDate);
            LocalTime startLocalTime = startTime.equals("") ? null: LocalTime.parse(startTime);
            LocalTime endLocalTime = endTime.equals("") ? null : LocalTime.parse(endTime);
            List<MealWithExceed> filterMeals = controller.getFilterByDateAndTime(startLocalDate, endLocalDate,
                    startLocalTime, endLocalTime);
            request.setAttribute("meals", filterMeals);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else {
            String id = request.getParameter("id");
            Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));
            log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            if (meal.isNew()) {
                controller.create(meal);
            } else {
                controller.update(meal, meal.getId());
            }
            response.sendRedirect("meals");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                controller.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        controller.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals", controller.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
