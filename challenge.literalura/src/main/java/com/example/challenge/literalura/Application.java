package com.literatura;
import com.literalura.Livraria.Livraria;
import com.literalura.Repositorio.iAutorRepositorio;
import com.literalura.Repositorio.iLivroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class Application implements CommandLineRunner {
	@Autowired
	private iLivroRepositorio livroRepositorio;

	@Autowired
	private iAutorRepositorio autorRepositorio;

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}


	@Override
	public void run(String ... args) throws Exception {

		Livraria livraria = new Livraria(livroRepositorio, autorRepositorio);
		livraria.consumo();
	}
}
