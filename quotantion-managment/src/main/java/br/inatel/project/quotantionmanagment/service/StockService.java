package br.inatel.project.quotantionmanagment.service;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import br.inatel.project.quotantionmanagment.component.RegisterComponent;
import br.inatel.project.quotantionmanagment.dto.StockDto;
import br.inatel.project.quotantionmanagment.form.StockForm;
import br.inatel.project.quotantionmanagment.model.Stock;
import br.inatel.project.quotantionmanagment.repository.StockRepository;

@Service
public class StockService {

	private RegisterComponent component;

	private StockRepository stockRepository;

	@Autowired
	public StockService(RegisterComponent component, StockRepository stockRepository) {
		this.component = component;
		this.stockRepository = stockRepository;
	}

	public ResponseEntity<List<StockDto>> listById(String stockId) {
		List<Stock> stocks = stockRepository.findByStockId(stockId.toUpperCase());
		if (stocks.isEmpty() == true) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(StockDto.convert(stocks));
	}

	public ResponseEntity<StockDto> addStockQuote(@RequestBody @Valid StockForm form, StockRepository stockRepository,
			URI uri) {
		Stock stock = form.convert(form);
		return form.verifyIfExists(form, stockRepository, stock, uri);
	}

	public boolean isPresent(StockForm form) {
		return component.getCache().contains(form.getStockId().toLowerCase());

	}
}