package ru.javawebinar.topjava.util.converter;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.Profiles;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.function.Function;

@Component
@Profile(Profiles.HSQL_DB)
public class ConverterToTimestampUtil implements Function<LocalDateTime, Timestamp> {

    @Override
    public Timestamp apply(LocalDateTime dateTime) {
        return Timestamp.valueOf(dateTime);
    }
}
