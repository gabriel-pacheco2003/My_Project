package br.com.gabi_la_boutique.boutique;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import br.com.gabi_la_boutique.boutique.services.AddressService;
import br.com.gabi_la_boutique.boutique.services.CategoryService;
import br.com.gabi_la_boutique.boutique.services.CityService;
import br.com.gabi_la_boutique.boutique.services.ClientService;
import br.com.gabi_la_boutique.boutique.services.PhoneService;
import br.com.gabi_la_boutique.boutique.services.ProductService;
import br.com.gabi_la_boutique.boutique.services.SellProductService;
import br.com.gabi_la_boutique.boutique.services.SellService;
import br.com.gabi_la_boutique.boutique.services.UserService;
import br.com.gabi_la_boutique.boutique.services.impl.AddressServiceImpl;
import br.com.gabi_la_boutique.boutique.services.impl.CategoryServiceImpl;
import br.com.gabi_la_boutique.boutique.services.impl.CityServiceImpl;
import br.com.gabi_la_boutique.boutique.services.impl.ClientServiceImpl;
import br.com.gabi_la_boutique.boutique.services.impl.PhoneServiceImpl;
import br.com.gabi_la_boutique.boutique.services.impl.ProductServiceImpl;
import br.com.gabi_la_boutique.boutique.services.impl.SellProductServiceImpl;
import br.com.gabi_la_boutique.boutique.services.impl.SellServiceImpl;
import br.com.gabi_la_boutique.boutique.services.impl.UserServiceImpl;

@TestConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class BaseTests {

	@Bean
	public AddressService addressService() {
		return new AddressServiceImpl();
	}

	@Bean
	public CategoryService categoryService() {
		return new CategoryServiceImpl();
	}

	@Bean
	public CityService cityService() {
		return new CityServiceImpl();
	}

	@Bean
	public ClientService clientService() {
		return new ClientServiceImpl();
	}

	@Bean
	public PhoneService phoneService() {
		return new PhoneServiceImpl();
	}

	@Bean
	public ProductService productService() {
		return new ProductServiceImpl();
	}

	@Bean
	public SellProductService sellProductService() {
		return new SellProductServiceImpl();
	}

	@Bean
	public SellService sellService() {
		return new SellServiceImpl();
	}
	
	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}

}
