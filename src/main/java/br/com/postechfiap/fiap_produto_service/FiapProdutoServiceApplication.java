package br.com.postechfiap.fiap_produto_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FiapProdutoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FiapProdutoServiceApplication.class, args);
	}

}
