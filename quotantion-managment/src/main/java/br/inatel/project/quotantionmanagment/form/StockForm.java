package br.inatel.project.quotantionmanagment.form;

import java.net.URI;
import java.time.LocalDate;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;

import br.inatel.project.quotantionmanagment.dto.StockDto;
import br.inatel.project.quotantionmanagment.model.Stock;
import br.inatel.project.quotantionmanagment.repository.StockRepository;

public class StockForm {

	@NotBlank
	@NotNull
	@Length(min = 4)
	private String stockId;
	@NotNull
	@NotEmpty
	private Map<LocalDate, String> quotes;

	public StockForm(String stockId, Map<LocalDate, String> quotes) {
		this.stockId = stockId.toUpperCase();
		this.quotes = quotes;
	}

	public StockForm() {
	}

	public ResponseEntity<StockDto> verifyIfExists(StockForm form, StockRepository stockRepository, Stock stock,
			URI uri) {
		if (stockRepository.existsByStockId(form.getStockId().toUpperCase()) == false) {
			stockRepository.save(stock);
			return ResponseEntity.created(uri).build();
		}
		stock = stockRepository.getByStockId(form.getStockId().toUpperCase());
		stock.getQuotes().putAll(form.getQuotes());
		stockRepository.save(stock);
		return ResponseEntity.created(uri).build();
	}

	public Stock convert(StockForm form) {
		return new Stock(stockId.toUpperCase(), quotes);
	}

	public String getStockId() {
		return stockId;
	}

	public Map<LocalDate, String> getQuotes() {
		return quotes;
	}

}
