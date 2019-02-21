package config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;

@Configuration
public class JsonConfig {

    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

//    class NullSerializer extends JsonSerializer {
//
//        @Override
//        public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//            gen.writeString("");
//        }
//    }
//
//    public class SpecialSpringSerializer extends JsonSerializer {
//
//        @Override
//        public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//            if (value instanceof String) {
//                if (((String) value).equalsIgnoreCase("123")) {
//                    gen.writeString("123____+");
//                }
//            }
//        }
//    }
}
