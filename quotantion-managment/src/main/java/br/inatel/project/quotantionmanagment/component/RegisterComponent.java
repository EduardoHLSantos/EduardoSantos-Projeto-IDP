package br.inatel.project.quotantionmanagment.component;

import javax.annotation.PostConstruct;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import br.inatel.project.quotantionmanagment.dto.RegisterDto;
import reactor.core.publisher.Flux;

@Component
public class RegisterComponent {

	CacheManager manager;

	WebClient client = WebClient.create();

	RestTemplate restTemplate = new RestTemplate();

	public RegisterComponent(RestTemplate template) {
		this.restTemplate = template;
	}

	public RegisterComponent(WebClient client) {
		this.client = client;
	}
	
	public RegisterComponent() {
	}

	@PostConstruct
	public Flux<String> notificationInit() {
		return client.post().uri("http://localhost:8080/notification").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).bodyValue(new RegisterDto()).retrieve().bodyToFlux(String.class);
	}

	@PostConstruct
	@Cacheable("stock")
	public String getCache() {
		String uri = "http://localhost:8080/stock";
		return restTemplate.getForObject(uri, String.class).toLowerCase();

	}

}