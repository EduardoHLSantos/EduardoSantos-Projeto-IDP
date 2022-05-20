package br.inatel.project.quotantionmanagment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.inatel.project.quotantionmanagment.component.RegisterComponent;
import br.inatel.project.quotantionmanagment.dto.StockDto;
import br.inatel.project.quotantionmanagment.form.StockForm;
import br.inatel.project.quotantionmanagment.model.Stock;
import br.inatel.project.quotantionmanagment.repository.StockRepository;

class StockServiceTest {

	private StockService service;

	@Mock
	Stock stock;

	@Mock
	StockRepository stockRepository;

	@Mock
	RegisterComponent component;

	@Mock
	StockForm form;

	@Mock
	RestTemplate restTemplate;

	@BeforeEach
	void beforeEach() {
		HashMap<LocalDate, String> map1 = new HashMap<LocalDate, String>();
		MockitoAnnotations.openMocks(this);
		this.stock = new Stock("PETR4", map1);
		this.form = new StockForm("PETR7", map1);
	}

	@Test
	@DisplayName("Should return list of StockDto with same stockId")
	void shouldListById() {
		StockRepository stockRepository = Mockito.mock(StockRepository.class);
		List<Stock> list = list();
		this.service = new StockService(component, stockRepository);
		when(stockRepository.findByStockId("PETR4")).thenReturn(list);
		ResponseEntity<List<StockDto>> listById = ResponseEntity.ok(StockDto.convert(list));
		assertEquals(listById.getStatusCode(), service.listById("PETR4").getStatusCode());
	}
	
	@Test
	@DisplayName("Should return Error 404 - Not found")
	void shouldReturnError404() {
		List<Stock> list = new ArrayList<>();
		this.service = new StockService(component, stockRepository);
		when(stockRepository.findByStockId("PETR4")).thenReturn(list);
		assertEquals(ResponseEntity.notFound().build(),service.listById("PETR4"));
	}

	@Test
	@DisplayName("Should Add Stock to Database")
	void shouldAddStockQuote() throws URISyntaxException {
		this.service = new StockService(component, stockRepository);
		URI uri = new URI("/stocks/PETR4");
		when(form.verifyIfExists(form, stockRepository, stock, uri)).thenReturn(ResponseEntity.created(uri).build());
		assertEquals(ResponseEntity.created(uri).build(), service.addStockQuote(form, stockRepository, uri));

	}

	@Test
	@DisplayName("Should call method getCache from RegisterComponent")
	void shouldCallMethodGetCache() throws URISyntaxException {
		this.service = new StockService(component, stockRepository);
		String response = "{\r\n\"id\": \"petr7\", \r\n\"description\": \"test petr\" \r\n}";
		when(component.getCache()).thenReturn(response);
		service.isPresent(form);
		verify(component).getCache();
	}

	public List<Stock> list() {
		List<Stock> list = new ArrayList<>();
		HashMap<LocalDate, String> map1 = new HashMap<LocalDate, String>();
		map1.put(LocalDate.of(2019, 1, 3), "15");
		map1.put(LocalDate.of(2019, 1, 3), "15");
		list.add(new Stock("PETR4", map1));
		return list;
	}
}
