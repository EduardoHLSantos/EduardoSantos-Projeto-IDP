package br.inatel.project.quotantionmanagment.form;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import br.inatel.project.quotantionmanagment.model.Stock;
import br.inatel.project.quotantionmanagment.repository.StockRepository;

class StockFormTest {

	@Mock
	private StockForm form;

	@Mock
	private Stock stock;

	@Mock
	private StockRepository repository;

	@BeforeEach
	void beforeEach(){
		MockitoAnnotations.openMocks(this);
		this.stock = new Stock("PETR4", new HashMap<LocalDate, String>());
	}

	
	@Test
	@DisplayName("Should Convert StockForm to Stock ")
	void shouldConvertToStock() {
		this.form = new StockForm("PETR4", new HashMap<LocalDate, String>());

		stock = form.convert(form);

		assertEquals(Stock.class, stock.getClass());
		assertEquals(stock.getStockId(), form.getStockId());

	}

	@Test
	@DisplayName("Should save Stock to Database")
	void shouldSaveToDatabase() throws URISyntaxException {
		Mockito.when(form.convert(form)).thenReturn(stock);
		this.form = new StockForm("PETR4", new HashMap<LocalDate, String>());
		URI uri = new URI("/stocks/PETR4");
		assertEquals(ResponseEntity.created(uri).build(), form.verifyIfExists(form, repository, stock, uri));
		verify(repository).save(stock);
	}

}
