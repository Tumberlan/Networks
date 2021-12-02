package model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class JsonParser {
    private final static ObjectMapper MAPPER = new ObjectMapper();

    public JsonParser() {
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @SneakyThrows
    public <T> T parse(String JsonResponse, Class<T> tClass) {
        return MAPPER.readValue(JsonResponse, tClass);
    }

    @SneakyThrows
    public <T> T parse(String JsonResponse, TypeReference<T> tRef) {
        return MAPPER.readValue(JsonResponse, tRef);
    }

}
