package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
@Profile(Profiles.HSQL_DB)
public class JdbcMealMealRepositoryImplHsqlDb extends AbstractJdbcMealRepository<Timestamp> {


    public JdbcMealMealRepositoryImplHsqlDb(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Timestamp getDateTimeConverter(LocalDateTime localDateTime) {
        return  Timestamp.valueOf(localDateTime);
    }
}
