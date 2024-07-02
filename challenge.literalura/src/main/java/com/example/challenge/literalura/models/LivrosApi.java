package com.literalura.models;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.literalura.models.records.DadosLivro;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LivrosApi {

    @JsonAlias("results")
    List<DadosLivro> resultadoLivros;

    public List<DadosLivro> getResultadoLivros() {
        return resultadoLivros;
    }

    public void setResultadoLivros(List<DadosLivro> resultadoLivros) {
        this.resultadoLivrosros = resultadoLivros;
    }
}