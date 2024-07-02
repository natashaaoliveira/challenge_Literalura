package com.literalura.config;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.literalura.config.iconfig.IConverterDados;

public class ConverterDados implements IConverterDados {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T converterDadosJsonAJava(String json, Class<T> classe) {
        try {
            return objectMapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
