package com.literalura.models.record;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Autor(
        @JsonAlias("name") String nome,
        @JsonAlias("birth_year") Integer DataDeNascimento,
        @JsonAlias("death_year") Integer DataDeFalecimento
) {
}
