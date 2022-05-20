package br.inatel.project.quotantionmanagment.component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import br.inatel.project.quotantionmanagment.dto.RegisterDto;
import reactor.core.publisher.Flux;

class RegisterComponentTest {

	RegisterComponent registerComponent;

	@Mock
	RestTemplate restTemplate;

	@Mock
	WebClient client;

	@Test
	@DisplayName("Should send POST request with JSON to External REST and return flux as response")
	void shouldSendPostWithJsonToApplication() {
		client = Mockito.mock(WebClient.class);
		registerComponent = new RegisterComponent(WebClient.create());
		Flux<String> flux = WebClient.create().post().uri("http://localhost:8080/notification")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).bodyValue(new RegisterDto())
				.retrieve().bodyToFlux(String.class);

		assertEquals(flux.getClass(), registerComponent.notificationInit().getClass());
	}

	@Test
	@DisplayName("Should get cache from external application")
	void shouldGetCacheFromApplication() {
		String uri = "http://localhost:8080/stock";
		this.restTemplate = Mockito.mock(RestTemplate.class);
		this.registerComponent = new RegisterComponent(restTemplate);
		when(restTemplate.getForObject(uri, String.class))
				.thenReturn("{\r\n\"id\": \"petr7\", \r\n\"description\": \"test petr\" \r\n}");
		assertEquals("{\r\n\"id\": \"petr7\", \r\n\"description\": \"test petr\" \r\n}", registerComponent.getCache());
	}

}
