package ru.javawebinar.topjava.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealMemoryDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.DateFormatter;
import ru.javawebinar.topjava.util.MealsUtil;
import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);

    private MealService mealService;

    @Override
    public void init() throws ServletException {
        log.info("Start init MealServlet");
        super.init();
        mealService = new MealService(MealMemoryDao.getInstance());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        try {
            switch (action == null ? "" : action) {
                case "new":
                    showNewForm(req, resp);
                    break;
                case "create":
                    createMeal(req, resp);
                    break;
                case "delete":
                    deleteMeal(req, resp);
                    break;
                case "edit":
                    showEditForm(req, resp);
                    break;
                case "update":
                    updateMeal(req, resp);
                    break;
                default:
                    listMeals(req, resp);
                    break;
            }
        } catch (ServletException | IOException e) {
            log.error("error in servlet", e.getCause());
        }
    }

    private void listMeals(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        List<Meal> listMeals = mealService.getAll();
        List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(listMeals,
                LocalTime.MIN, LocalTime.MAX, 2000);
        request.setAttribute("mealWithExceeds", mealWithExceeds);
        RequestDispatcher dispatcher = request.getRequestDispatcher("meals.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("mealsForm.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Meal existingMeal = mealService.getById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("mealsForm.jsp");
        request.setAttribute("meal", existingMeal);
        dispatcher.forward(request, response);

    }

    private void createMeal(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String dateTime = request.getParameter("dateTime");
        LocalDateTime localDateTime = DateFormatter.getDateTime(dateTime);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        Meal meal = new Meal(localDateTime, description, calories);
        mealService.create(meal);
        response.sendRedirect("meals");
    }

    private void updateMeal(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String dateTime = request.getParameter("dateTime");
        LocalDateTime localDateTime = DateFormatter.getDateTime(dateTime);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        Meal meal = new Meal(id, localDateTime, description, calories);
        mealService.update(meal);
        response.sendRedirect("meals");
    }

    private void deleteMeal(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Meal meal = new Meal(id);
        mealService.delete(meal);
        response.sendRedirect("meals");
    }
}
