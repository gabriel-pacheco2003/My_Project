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
import br.com.gabi_la_boutique.boutique.models.City;
import br.com.gabi_la_boutique.boutique.resources.exceptions.StandardError;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = BoutiqueApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityResourceTest {

	@Autowired
	protected TestRestTemplate rest;

	private ResponseEntity<City> getCity(String url) {
		return rest.getForEntity(url, City.class);
	}

	private ResponseEntity<List<City>> getCities(String url) {
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<City>>() {
		});
	}
	
	@Test
	@DisplayName("Buscar por id")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/cidade.sql"})
	public void getOkTest() {
		ResponseEntity<City> response = getCity("/city/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals("Tubarão", response.getBody().getName());
	}

	@Test
	@DisplayName("Buscar por id inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/cidade.sql"})
	public void getNotFoundTest() {
		ResponseEntity<City> response = getCity("/city/100");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Cadastrar cidade")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/cidade.sql"})
	public void insertCountryTest() {
		City city = new City(1, "name");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<City> requestEntity = new HttpEntity<>(city, headers);
		ResponseEntity<City> responseEntity = rest.exchange(
	            "/city", 
	            HttpMethod.POST,  
	            requestEntity,    
	            City.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("name", responseEntity.getBody().getName());
	}
	
	@Test
	@DisplayName("Cadastrar cidade já existente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/cidade.sql"})
	public void insertCountryExistsTest() {
		City city = new City(null, "Camacho");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<City> requestEntity = new HttpEntity<>(city, headers);
		ResponseEntity<City> responseEntity = rest.exchange(
	            "/city", 
	            HttpMethod.POST,  
	            requestEntity,    
	            City.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("Alterar cidade")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/cidade.sql"})
	public void udpateCountryTest() {
		City city = new City(null, "update");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<City> requestEntity = new HttpEntity<>(city, headers);
		ResponseEntity<City> responseEntity = rest.exchange(
	            "/city/1", 
	            HttpMethod.PUT,  
	            requestEntity,    
	            City.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("update", responseEntity.getBody().getName());
	}
	
	@Test
	@DisplayName("Alterar cidade já existente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/cidade.sql"})
	public void udpateCountryExistsTest() {
		City city = new City(1, "Camacho");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<City> requestEntity = new HttpEntity<>(city, headers);
		ResponseEntity<City> responseEntity = rest.exchange(
	            "/city/1", 
	            HttpMethod.PUT,  
	            requestEntity,    
	            City.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("Procurar por nome")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/cidade.sql"})
	public void findByNameTest() {
		ResponseEntity<List<City>> response = getCities("/city/name/Tubarão");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Procurar por nome inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/cidade.sql"})
	public void findByNameNonExistsTest() {
		ResponseEntity<StandardError> response = rest.getForEntity("/city/name/nsadlkbag", StandardError.class);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Listar todos")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/cidade.sql"})
	public void listAllTest() {
		ResponseEntity<List<City>> response = getCities("/city");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(3, response.getBody().size());
	}
	
	@Test
	@DisplayName("Listar todos sem possuir cadastros")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	public void listAllWithNoRegistersTest() {
		ResponseEntity<StandardError> response = rest.getForEntity("/city", StandardError.class);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Remover cidade")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/cidade.sql"})
	public void deleteCountryTest() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<City> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<City> responseEntity = rest.exchange(
				"/city/1", 
				HttpMethod.DELETE,  
				requestEntity,    
				City.class   
		);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

	}
	
	@Test
	@DisplayName("Remover cidade inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/cidade.sql"})
	public void deleteCountryNonExistsTest() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<City> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<City> responseEntity = rest.exchange(
				"/city/10", 
				HttpMethod.DELETE,  
				requestEntity,    
				City.class   
		);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);

	}

}
