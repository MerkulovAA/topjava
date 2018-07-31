package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;


@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {

    @Test
    public void getByIdWithUser() throws Exception {
        Meal actual = service.getByIdWithUser(MEAL1_ID,USER_ID );
        assertMatchWithUser(actual, MEAL_WITH_USER);
    }

    @Test
    public void getByIdWithUserNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.getByIdWithUser(1, 1);
    }
}
