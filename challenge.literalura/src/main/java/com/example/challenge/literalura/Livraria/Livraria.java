package com.literalura.Livraria;
import com.literalura.config.ConsumoApi;
import com.literalura.config.ConverterDados;
import com.literalura.models.Autor;
import com.literalura.models.Livro;
import com.literalura.models.LivrosApi;
import com.literalura.models.record.DadosLivro;
import com.literalura.Repositorio.iAutorRepositorio;
import com.literalura.Repositorio.iLivroRepositorio;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public class Livraria {

    private Scanner sc = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverterDados converter = new ConverterDados();
    private static String API_BASE = "https://gutendex.com/books/?search=";
    private List<Livro> dadosLivro = new ArrayList<>();
    private iLivroRepositorio livroRepositorio;
    private iAutorRepositorio autorRepositorio;
    public Livraria(iLivroRepositorio livroRepositorio, iAutorRepositorio autorRepositorio) {
        this.livroRepositorio = livroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void consumo(){
        var opcao = -1;
        while (opcao != 0){
            var menu = """
                    
                    |***************************************************|
                    
                    1 - Adicionar Livro por Nome
                    2 - Livros buscados
                    3 - Buscar livro por Nome
                    4 - Buscar todos os Autores de livros buscados
                    5 - Buscar Autores por ano
                    6 - Buscar Livros por Idioma
                    7 - Top 10 Livros mais baixados
                    8 - Buscar Autor por Nome
                   
                    
               
                    0 - Sair
                    
                    """

            try {
                System.out.println(menu);
                opcion = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {

                System.out.println("|****************************************|");
                System.out.println("|  Por favor, ingresse um número válido.  |");
                System.out.println("|****************************************|\n");
                sc.nextLine();
                continue;
            }



            switch (opcion){
                case 1:
                    buscarLivroNaWeb();
                    break;
                case 2:
                    livrosBuscados();
                    break;
                case 3:
                    buscarLivroPorNome();
                    break;
                case 4:
                    BuscarAutores();
                    break;
                case 5:
                    buscarAutoresPorAno();
                    break;
                case 6:
                    buscarLivrosPorIdioma();
                    break;
                case 7:
                    top10LivrosMaisBaixados();
                    break;
                case 8:
                    buscarAutorPorNome();
                    break;
                case 0:
                    opcion = 0;
                    System.out.println("|********************************|");
                    System.out.println("|    Encerrando !!!!    |");
                    System.out.println("|********************************|\n");
                    break;
                default:
                    System.out.println("|*********************|");
                    System.out.println("|  Opção Incorreta. |");
                    System.out.println("|*********************|\n");
                    System.out.println("Tente uma nova opção");
                    consumo();
                    break;
            }
        }
    }

    private Livro getDadosLivro(){
        System.out.println("Insira o nome de livro: ");
        var nomeLivro = sc.nextLine().toLowerCase();
        var json = consumoApi.obterDados(API_BASE + nomeLivro.replace(" ", "%20"));
        LivrosApi dados = converter.converterDadosJsonAJava(json, LivrosApi.class);

        if (dados != null && dados.getResultadoLivros() != null && !dados.getResultadoLivros().isEmpty()) {
            DadosLivro primeiroLivro = dados.getResultadoLivros().get(0); // Obtener el primer libro de la lista
            return new Livro(primeiroLivro);
        } else {
            System.out.println("Não foi possível encontrar.");
            return null;
        }
    }


    private void buscarLivroNaWeb() {
        Livro livro = getDadosLivro();

        if (livro == null){
            System.out.println("Livro não encontrado.");
            return;
        }

        try{
            boolean livroExists = livroRepositorio.existsByTitulo(livro.getTitulo());
            if (livroExists){
                System.out.println("O livro já existe na base de dados !! ");
            }else {
                livroRepositorio.save(livro);
                System.out.println(livro.toString());
            }
        }catch (InvalidDataAccessApiUsageException e){
            System.out.println("Não existe o livro buscado.");
        }
    }

    @Transactional(readOnly = true)
    private void livrosBuscados(){
        List<Livro> livros = livroRepositorio.findAll();
        if (livros.isEmpty()) {
            System.out.println("Não foi encontrado o livro na base de dados!!");
        } else {
            System.out.println("Livros encontrados na base de dados:");
            for (Livro livro : livros) {
                System.out.println(livro.toString());
            }
        }
    }

    private void buscarLivroPorNome() {
        System.out.println("Insira um título de um livro que deseja: ");
        var titulo = sc.nextLine();
        Livro livroBuscado = livroRepositorio.findByTituloContainsIgnoreCase(titulo);
        if (livroBuscado != null) {
            System.out.println("O livro buscado foi : " + livroBuscado);
        } else {
            System.out.println("O livro com o título'" + titulo + "' não foi encontrado.");
        }
    }

    private  void BuscarAutores(){
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("Não foi possível encontrar os livros na base de dados. \n");
        } else {
            System.out.println("Livros encontrados na base de dados: \n");
            Set<String> autoresUnicos = new HashSet<>();
            for (Autor autor : autores) {
                if (autoresUnicos.add(autor.getNome())){
                    System.out.println(autor.getNome()+'\n');
                }
            }
        }
    }

    private void  buscarLivrosPorIdioma(){
        System.out.println("Insira um idioma que deseja procurar: \n");
        System.out.println("|***********************************|");
        System.out.println("|  Opcão - es : Livros em espanhol. |");
        System.out.println("|  Opcão - en : Livros em ingles.  |");
        System.out.println("|  Opcão - br : Livros em Português.  |");
        System.out.println("|***********************************|\n");

        var idioma = sc.nextLine();
        List<Livro> livrosPorIdioma = livroRepositorio.findByIdioma(idioma);

        if (livrosPorIdioma.isEmpty()) {
            System.out.println("Não foi encontrado o livro na base de dados");
        } else {
            System.out.println("Livros segundo o idioma escolhido na base de dados:");
            for (Livro livro : livrosPorIdioma) {
                System.out.println(livro.toString());
            }
        }

    }

    private void buscarAutoresPorAno() {

        System.out.println("Insira o ano para consultar os autores da época: \n");
        var anoBuscado = sc.nextInt();
        sc.nextLine();

        List<Autor> autoresVivos = autorRepositorio.findByDataDeNascimentoLessThanOrDataDeFalecimentoGreaterThanEqual(anoBuscado, anoBuscado);

        if (autoresVivos.isEmpty()) {
            System.out.println("Não foi possível encontrar autores da época " + anoBuscado + ".");
        } else {
            System.out.println("Os autores da época " + anoBuscado + " são:");
            Set<String> autoresUnicos = new HashSet<>();

            for (Autor autor : autoresVivos) {
                if (autor.getDataDeNascimento() != null && autor.getDataDeFalecimento() != null) {
                    if (autor.getDataDeNascimento() <= anoBuscado && autor.getDataDeFalecimento() >= anoBuscado) {
                        if (autoresUnicos.add(autor.getNome())) {
                            System.out.println("Autor: " + autor.getNome());
                        }
                    }
                }
            }
        }
    }

    private void top10LivrosMaisBaixados(){
        List<Livro> top10Livros = livroRepositorio.findTop10ByTituloByQuantidadeDownload();
        if (!top10Livros.isEmpty()) {
            int index = 1;
            for (Livro livro : top10Livros) {
                System.out.printf("Livro %d: %s Autor: %s Download: %d\n",
                        index, livro.getTitulo(), livro.getAutores().getNome(), livro.getQuantidadeDownload());
                index++;
            }
        }
    }


    private void buscarAutorPorNome() {
        System.out.println("Insira o nome do autor que deseja procurar ");
        var escritor = sc.nextLine();
        Optional<Autor> escritorBuscado = autorRepositorio.findFirstByNomeContainsIgnoreCase(escritor);
        if (escritorBuscado != null) {
            System.out.println("\nO escritor buscado foi: " + escritorBuscado.get().getNome());

        } else {
            System.out.println("\nO escitor com o título '" + escritor + "' não foi encontrado.");
        }
    }
}
