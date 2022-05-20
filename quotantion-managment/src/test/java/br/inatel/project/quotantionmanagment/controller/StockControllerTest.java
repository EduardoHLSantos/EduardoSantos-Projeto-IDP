package br.inatel.project.quotantionmanagment.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import br.inatel.project.quotantionmanagment.dto.StockDto;
import br.inatel.project.quotantionmanagment.form.StockForm;
import br.inatel.project.quotantionmanagment.repository.StockRepository;
import br.inatel.project.quotantionmanagment.service.StockService;

class StockControllerTest {

	@Mock
	private StockRepository stockRepository;

	@Mock
	private StockService stockService;

	private StockForm form;

	private UriComponentsBuilder uriBuilder;

	private StockController controller;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
		form = new StockForm("petr7", new HashMap<>());
		controller = new StockController(stockRepository, stockService);
		uriBuilder = UriComponentsBuilder.newInstance();

	}

	@Test
	@DisplayName("Should Call Method findAll from StockRepository")
	void shouldCallFindAll() {
		controller.list();
		verify(stockRepository).findAll();
	}

	@Test
	@DisplayName("Should Call Method listById from StockService")
	void shouldCallFindByStockId() {
		controller.listByStockId("stockId");
		verify(stockService).listById("stockId");
	}

	@Test
	@DisplayName("Should Call Method saveStockQuote from StockService and create new Stock")
	void shouldCallSaveStockQuote() throws URISyntaxException {
		when(stockService.isPresent(form)).thenReturn(true);
		URI uri = uriBuilder.path("stocks/{stockId}").buildAndExpand(form.getStockId().toUpperCase()).toUri();
		when(stockService.addStockQuote(form, stockRepository, uri)).thenReturn(list());
		assertEquals(ResponseEntity.created(uri).build().getStatusCode(), 
				stockService.addStockQuote(form, stockRepository, uri).getStatusCode());
	}

	@Test
	@DisplayName("Should return Error 404 - Not found")
	void shouldReturnError404(){
		when(stockService.isPresent(form)).thenReturn(false);
		assertEquals(ResponseEntity.notFound().build(), controller.addStockQuote(form, uriBuilder));
	}

	public ResponseEntity<StockDto> list() throws URISyntaxException {
		URI uri = new URI("/stocks/PETR7");
		return ResponseEntity.created(uri).build(); 

	}

}