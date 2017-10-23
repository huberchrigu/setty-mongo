package ch.chrigu.setty.mongo.infrastructure.mongo.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class DayOfWeekToStringConverter implements Converter<DayOfWeek, String> {
    @Nullable
    @Override
    public String convert(@Nullable DayOfWeek dayOfWeek) {
        if (dayOfWeek == null) {
            return null;
        }
        return dayOfWeek.name();
    }
}
