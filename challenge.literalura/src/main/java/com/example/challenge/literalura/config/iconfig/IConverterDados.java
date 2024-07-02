package com.literalura.config.iconfig;

public interface IConverterDados {

    <T> T converterDadosJsonAJava(String json , Class<T> classe);
}
