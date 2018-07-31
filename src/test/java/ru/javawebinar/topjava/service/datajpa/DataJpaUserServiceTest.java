package ru.javawebinar.topjava.service.datajpa;


import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.UserTestData.assertMatch;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void getByIdWithMeals() throws Exception {
        User actual = service.getByIdWithMeals(ADMIN_ID);
        assertMatch(actual, ADMIN);
        assertMatch(actual.getMeals(), ADMIN_MEAL2, ADMIN_MEAL1);
    }

    @Test
    public void getByIdWithMealsNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.getByIdWithMeals(1);
    }
}
