package ch.chrigu.setty.mongo.infrastructure.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.OffsetTime;

@Configuration
public class JacksonDateConfig implements RepositoryRestConfigurer {
    @Override
    public void configureJacksonObjectMapper(ObjectMapper objectMapper) {
        SimpleModule module = new SimpleModule();
        module.addSerializer(OffsetTime.class, new JsonSerializer<>() {
            @Override
            public void serialize(OffsetTime offsetTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeString(offsetTime.toString());
            }
        });
        module.addDeserializer(OffsetTime.class, new JsonDeserializer<>() {
            @Override
            public OffsetTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                String value = p.getValueAsString();
                if (value.contains("T")) {
                    return OffsetDateTime.parse(value).toOffsetTime();
                }
                return OffsetTime.parse(value);
            }
        });
        objectMapper.registerModule(module);

    }
}
