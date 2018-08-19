package ru.javawebinar.topjava.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class LocalTimeConverter implements Converter<String, LocalTime> {

    @Override
    public LocalTime convert(String source) {
        return StringUtils.isEmpty(source) ? null : LocalTime.parse(source, DateTimeFormatter.ofPattern("HH:mm"));
    }
}
