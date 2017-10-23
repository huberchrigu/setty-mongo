package ch.chrigu.setty.mongo.infrastructure.mongo.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class StringToDayOfWeekConverter implements Converter<String, DayOfWeek> {

    @Nullable
    @Override
    public DayOfWeek convert(@Nullable String s) {
        return DayOfWeek.valueOf(s);
    }
}
