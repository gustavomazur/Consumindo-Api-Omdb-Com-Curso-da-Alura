package br.com.alura.filme;
//Ctrl + Alt + o -> (Tira tudo que não estamos mais usando como imports)

import br.com.alura.filme.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FilmeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FilmeApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal();
		principal.exibeMenu();


		//Ctrl + / -> pra comentar tudo para
		// Ctrl + -> para descomentar tudo
	}
}
