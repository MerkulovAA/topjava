package ru.javawebinar.topjava.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealMemoryDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    MealService mealService;

    @Override
    public void init() throws ServletException {
        log.info("Start init MealServlet");
        super.init();
        mealService  = new MealService(new MealMemoryDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        listMeals(req, resp);
    }


    private void listMeals(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        List<Meal> listMeals = mealService.getAll();
        List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(listMeals, LocalTime.MIN, LocalTime.MAX, 2000);
        request.setAttribute("mealWithExceeds", mealWithExceeds);
        RequestDispatcher dispatcher = request.getRequestDispatcher("meals.jsp");
        dispatcher.forward(request, response);
    }
}
