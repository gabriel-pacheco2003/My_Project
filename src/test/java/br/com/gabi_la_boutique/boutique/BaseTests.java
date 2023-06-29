package br.com.gabi_la_boutique.boutique;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import br.com.gabi_la_boutique.boutique.services.ClientService;
import br.com.gabi_la_boutique.boutique.services.impl.ClientServiceImpl;

@TestConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class BaseTests {

	@Bean
	public ClientService clientService() {
		return new ClientServiceImpl();
	}
	
}
