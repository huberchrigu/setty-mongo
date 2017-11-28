package ch.chrigu.setty.mongo.infrastructure.mongo;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.OffsetTime;
import java.util.Arrays;

/**
 * @author christoph.huber
 * @since 17.11.2017
 */
@Configuration
public class CustomMongoConfiguration extends AbstractMongoConfiguration {

    @Bean
    public Converter<String, OffsetTime> documentOffsetTimeConverter() {
        return new StringOffsetTimeConverter();
    }

    @Bean
    public Converter<OffsetTime, String> offsetTimeStringConverter() {
        return new OffsetTimeStringConverter();
    }

    @Override
    @NonNull
    public MongoClient mongoClient() {
        return new MongoClient();
    }

    @Override
    @NonNull
    protected String getDatabaseName() {
        return "setty";
    }

    @Override
    @NonNull
    public CustomConversions customConversions() {
        return new MongoCustomConversions(Arrays.asList(
                documentOffsetTimeConverter(),
                offsetTimeStringConverter()
        ));
    }

    @ReadingConverter
    private class StringOffsetTimeConverter implements Converter<String, OffsetTime> {
        @Nullable
        @Override
        public OffsetTime convert(@NonNull String s) {
            return OffsetTime.parse(s);
        }
    }

    @WritingConverter
    private class OffsetTimeStringConverter implements Converter<OffsetTime, String> {
        @Nullable
        @Override
        public String convert(@NonNull OffsetTime offsetTime) {
            return offsetTime.toString();
        }
    }
}
