package com.literalura.models;
import com.literalura.dtos.Genero;
import com.literalura.models.record.DadosLivro;
import com.literalura.models.record.Media;
import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "Livros")
public class livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long livroId;

    @Column(unique = true)
    private String titulo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "autor_id")

    private Autor autor;
    @Enumerated(EnumType.STRING)
    private Genero genero;
    private String idioma;
    private String imagem;
    private Long quantidadeDownload;

    public Livro() {
    }

    public Livro(DadosLivro dadosLivro) {
        this.livroId = dadosLivro.livroId();
        this.titulo = dadosLivro.titulo();

        if (dadosLivro.autor() != null && !dadosLivro.autor().isEmpty()) {
            this.autor = new Autor(dadosLivro.autor().get(0));
        } else {
            this.autor = null;
        }
        this.genero =  generoModificado(dadosLivro.genero());
        this.idioma = idiomaModificado(dadosLivro.idioma());
        this.imagen = imagenModificada(dadosLivro.imagem());
        this.quantidadeDownload = dadosLivro.quantidadeDownload();
    }

    public Livro(Livro livro) {
    }

    private Genero generoModificado(List<String> generos) {
        if (generos == null || generos.isEmpty()) {
            return Genero.DESCONHECIDO;
        }
        Optional<String> firstGenero = generos.stream()
                .map(g -> {
                    int index = g.indexOf("--");
                    return index != -1 ? g.substring(index + 2).trim() : null;
                })
                .filter(Objects::nonNull)
                .findFirst();
        return firstGenero.map(Genero::fromString).orElse(Genero.DESCONHECIDO);
    }

    private String idiomaModificado(List<String> idiomas) {
        if (idiomas == null || idiomas.isEmpty()) {
            return "Desconhecido";
        }
        return idiomas.get(0);
    }

    private String imagemModificada(Media media) {
        if (media == null || media.imagem().isEmpty()) {
            return "Sem imagem";
        }
        return media.imagem();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagen(String imagem) {
        this.imagen = imagen;
    }

    public Long getLivroId() {
        return livroId;
    }

    public void setLivroId(Long livroId) {
        this.livroId = livroId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutores() {
        return autor;
    }

    public void setAutores(Autor autores) {
        this.autor = autores;
    }


    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }



    public Long getQuantidadeDownload() {
        return quantidadeDownload;
    }

    public void setQuantidadeDownload(Long quantidadeDownload) {
        this.quantidadeDownload = quantidadeDownload;
    }

    @Override
    public String toString() {
        return
                "  \nid=" + id +
                        "  \nLivro id=" + livroId +
                        ", \ntitulo ='" + titulo + '\'' +
                        ", \nautores =" + (autor != null ? autor.getNome() : "N/A")+
                        ", \ngenero =" + genero +
                        ", \nidioma =" + idioma +
                        ", \nimagem =" + imagem +
                        ", \nquantidade Download =" + quantidadeDownload;
    }

}