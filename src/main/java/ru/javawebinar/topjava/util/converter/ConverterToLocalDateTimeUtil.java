package ru.javawebinar.topjava.util.converter;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.Profiles;

import java.time.LocalDateTime;
import java.util.function.Function;

@Component
@Profile(Profiles.POSTGRES_DB)
public class ConverterToLocalDateTimeUtil implements Function<LocalDateTime, LocalDateTime> {

    @Override
    public LocalDateTime apply(LocalDateTime dateTime) {
        return dateTime;
    }
}
