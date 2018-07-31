package ru.javawebinar.topjava.service.datajpa;


import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    // FIXME
    @Test
    public void getByIdWithMeals() throws Exception {
        User actual = service.getByIdWithMeals(ADMIN_ID);
        assertMatchWithMeals(actual, ADMIN_WITH_MEALS);
    }

}
