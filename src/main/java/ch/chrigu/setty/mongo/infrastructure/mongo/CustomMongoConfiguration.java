package ch.chrigu.setty.mongo.infrastructure.mongo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.Arrays;

/**
 * @author christoph.huber
 * @since 17.11.2017
 */
@Configuration
public class CustomMongoConfiguration {

    @Bean
    public Converter<String, OffsetTime> stringOffsetTimeConverter() {
        return new StringOffsetTimeConverter();
    }

    @Bean
    public Converter<OffsetTime, String> offsetTimeStringConverter() {
        return new OffsetTimeStringConverter();
    }

    @Bean
    public Converter<String, OffsetDateTime> dateOffsetDateTimeConverter() {
        return new StringOffsetDateTimeConverter();
    }

    @Bean
    public Converter<OffsetDateTime, String> offsetDateTimeDateConverter() {
        return new OffsetDateTimeStringConverter();
    }

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Arrays.asList(
                stringOffsetTimeConverter(),
                offsetTimeStringConverter(),
                dateOffsetDateTimeConverter(),
                offsetDateTimeDateConverter()
        ));
    }

    @ReadingConverter
    private static class StringOffsetTimeConverter implements Converter<String, OffsetTime> {
        @Nullable
        @Override
        public OffsetTime convert(@NonNull String s) {
            return OffsetTime.parse(s);
        }
    }

    @ReadingConverter
    private static class StringOffsetDateTimeConverter implements Converter<String, OffsetDateTime> {
        @Nullable
        @Override
        public OffsetDateTime convert(@NonNull String s) {
            return OffsetDateTime.parse(s);
        }
    }

    @WritingConverter
    private static class OffsetTimeStringConverter implements Converter<OffsetTime, String> {
        @Nullable
        @Override
        public String convert(@NonNull OffsetTime offsetTime) {
            return offsetTime.toString();
        }
    }

    @WritingConverter
    private static class OffsetDateTimeStringConverter implements Converter<OffsetDateTime, String> {
        @Nullable
        @Override
        public String convert(@NonNull OffsetDateTime offsetDateTime) {
            return offsetDateTime.toString();
        }
    }
}
