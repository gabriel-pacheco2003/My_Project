package br.com.gabi_la_boutique.boutique.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import br.com.gabi_la_boutique.boutique.BoutiqueApplication;
import br.com.gabi_la_boutique.boutique.config.jwt.LoginDTO;
import br.com.gabi_la_boutique.boutique.models.City;
import br.com.gabi_la_boutique.boutique.models.dto.UserDTO;
import br.com.gabi_la_boutique.boutique.resources.exceptions.StandardError;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = BoutiqueApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityResourceTest {

	@Autowired
	protected TestRestTemplate rest;
	
	@Test
	@DisplayName("Buscar por id")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/cidade.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void getOkTest() {
		LoginDTO loginDTO = new LoginDTO("email1", "senha1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST,  
				requestEntity,    
				String.class   
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		String token = responseEntity.getBody();
		System.out.println("****************" + token);
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		 ResponseEntity<City> response = rest.exchange("/city/1", HttpMethod.GET, new HttpEntity<>(null, headers),
	                City.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals("Tubarão", response.getBody().getName());
	}

	@Test
	@DisplayName("Buscar por id inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/cidade.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void getNotFoundTest() {
		LoginDTO loginDTO = new LoginDTO("email1", "senha1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST,  
				requestEntity,    
				String.class   
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		String token = responseEntity.getBody();
		System.out.println("****************" + token);
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		ResponseEntity<City> response = rest.exchange("/city/100", HttpMethod.GET, new HttpEntity<>(null, headers),
                City.class);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Inserir cidade")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/cidade.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void insertCountryTest() {
		City city = new City(1, "name");
		LoginDTO loginDTO = new LoginDTO("email1", "senha1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST,  
				requestEntity,    
				String.class   
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		String token = responseEntity.getBody();
		System.out.println("****************" + token);
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		ResponseEntity<City> response = rest.exchange("/city", HttpMethod.POST, new HttpEntity<>(city, headers),
                City.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals("name", response.getBody().getName());
	}
	
	@Test
	@DisplayName("Inserir cidade já existente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/cidade.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void insertCountryExistsTest() {
		City city = new City(null, "Camacho");
		LoginDTO loginDTO = new LoginDTO("email1", "senha1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST,  
				requestEntity,    
				String.class   
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		String token = responseEntity.getBody();
		System.out.println("****************" + token);
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		ResponseEntity<City> response = rest.exchange("/city", HttpMethod.POST, new HttpEntity<>(city, headers),
                City.class);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("Alterar cidade")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/cidade.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void udpateCountryTest() {
		City city = new City(1, "update");
		LoginDTO loginDTO = new LoginDTO("email1", "senha1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST,  
				requestEntity,    
				String.class   
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		String token = responseEntity.getBody();
		System.out.println("****************" + token);
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		ResponseEntity<City> response = rest.exchange("/city/1", HttpMethod.PUT, new HttpEntity<>(city, headers),
                City.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals("update", response.getBody().getName());
	}
	
	@Test
	@DisplayName("Alterar cidade já existente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/cidade.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void udpateCountryExistsTest() {
		City city = new City(1, "Camacho");
		LoginDTO loginDTO = new LoginDTO("email1", "senha1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST,  
				requestEntity,    
				String.class   
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		String token = responseEntity.getBody();
		System.out.println("****************" + token);
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		ResponseEntity<City> response = rest.exchange("/city/1", HttpMethod.PUT, new HttpEntity<>(city, headers),
                City.class);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("Procurar por nome")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/cidade.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void findByNameTest() {
		LoginDTO loginDTO = new LoginDTO("email1", "senha1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST,  
				requestEntity,    
				String.class   
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		String token = responseEntity.getBody();
		System.out.println("****************" + token);
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		ResponseEntity<List<City>> response = rest.exchange("/city/name/Tubarão", HttpMethod.GET, new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<List<City>>() {
				});
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Procurar por nome inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/cidade.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void findByNameNonExistsTest() {
		LoginDTO loginDTO = new LoginDTO("email1", "senha1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST,  
				requestEntity,    
				String.class   
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		String token = responseEntity.getBody();
		System.out.println("****************" + token);
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		ResponseEntity<StandardError> response = rest.exchange("/city/name/dhaslkv", HttpMethod.GET, new HttpEntity<>(null, headers),
                StandardError.class);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Listar todos")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/cidade.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void listAllTest() {
		LoginDTO loginDTO = new LoginDTO("email1", "senha1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST,  
				requestEntity,    
				String.class   
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		String token = responseEntity.getBody();
		System.out.println("****************" + token);
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		ResponseEntity<List<City>> response = rest.exchange("/city", HttpMethod.GET, new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<List<City>>() {
				});
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(3, response.getBody().size());
	}
	
	@Test
	@DisplayName("Listar todos sem possuir cadastros")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void listAllWithNoRegistersTest() {
		LoginDTO loginDTO = new LoginDTO("email1", "senha1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST,  
				requestEntity,    
				String.class   
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		String token = responseEntity.getBody();
		System.out.println("****************" + token);
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		ResponseEntity<StandardError> response = rest.exchange("/city", HttpMethod.GET, new HttpEntity<>(null, headers),
				StandardError.class);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Remover cidade")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/cidade.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void deleteCountryTest() {
		LoginDTO loginDTO = new LoginDTO("email1", "senha1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST,  
				requestEntity,    
				String.class   
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		String token = responseEntity.getBody();
		System.out.println("****************" + token);
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		ResponseEntity<City> response = rest.exchange("/city/1", HttpMethod.DELETE, new HttpEntity<>(null, headers),
                City.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);

	}
	
	@Test
	@DisplayName("Remover cidade inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/cidade.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void deleteCountryNonExistsTest() {
		LoginDTO loginDTO = new LoginDTO("email1", "senha1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST,  
				requestEntity,    
				String.class   
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		String token = responseEntity.getBody();
		System.out.println("****************" + token);
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		ResponseEntity<City> response = rest.exchange("/city/100", HttpMethod.DELETE, new HttpEntity<>(null, headers),
                City.class);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

	}
	
	@Test
	@DisplayName("Obter Token")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void getToken() {
		LoginDTO loginDTO = new LoginDTO("email1", "senha1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST,  
				requestEntity,    
				String.class   
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		String token = responseEntity.getBody();
		System.out.println("****************" + token);
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		ResponseEntity<List<UserDTO>> response =  rest.exchange("/user", HttpMethod.GET, new HttpEntity<>(null, headers),new ParameterizedTypeReference<List<UserDTO>>() {});
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

}
