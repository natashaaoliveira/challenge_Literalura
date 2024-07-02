package com.literalura.Repositorio;

import java.util.List;
import java.util.Optional;

public interface iAutorRepositorio extends JpaRepositorio<Autor, Long> {

        List <Autor> findAll();

        List <Autor>  findByDataDeNascimentoLessThanOrDataDeFalecimentoGreaterThanEqual (int anoBuscado, int anoBuscado1);

        Optional <Autor> findFirstByNomeContainsIgnoreCase(String escritor);
}