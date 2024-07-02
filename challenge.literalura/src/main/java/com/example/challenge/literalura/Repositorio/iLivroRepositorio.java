package com.literalura.Repositorio;

import java.util.List;
import java.util.Optional;

public interface iLivroRepositorio extends JpaRepositorio<livro, long> {

    boolean existsByTitulo (String titulo);

    livro findBytituloContainsIgnoreCase(String titulo);

    list <Livro> findByIdioma(String idioma);


    @Query ("SELECT l From Livro l ORDER BY l.quantidadeDownload DESC LIMIT 10")
    List<Livro> findTop10ByTituloByQuantidadeDownload();
}
