package br.com.alura.Desafio.Java;

import br.com.alura.Desafio.Java.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesafioJavaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DesafioJavaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}
