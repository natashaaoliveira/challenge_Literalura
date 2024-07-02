package com.literalura.models;
import com.literalura.models.record.DadosLivro;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table (name = " autores")
public class Autor {

    @Id
    @GenerateValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Integer DataDeNascimento;

    private Integer DataDeFalecimento;

    @OneToMany (mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <Livro> livros;

    public Autor(){
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getDataDeNascimento() {
        return DataDeNascimento;
    }

    public Integer getDataDeFalecimento() {
        return DataDeFalecimento;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public Autor(com.literalura.models.record.Autor autor) {
        this.nome = autor.nome();
        this.DataDeNascimento = autor.DataDeNascimento();
        this.DataDeFalecimento = autor.DataDeFalecimento();
    }

    @Override
    public String toString() {
        return
                "Nome = " + nome + '\'' +
                 ", Data de Nascimento = " + DataDeNascimento +
                 ", Data de Falecimento = " + DataDeFalecimento;
    }
}