package br.inatel.project.quotantionmanagment.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.inatel.project.quotantionmanagment.dto.StockDto;
import br.inatel.project.quotantionmanagment.form.StockForm;
import br.inatel.project.quotantionmanagment.model.Stock;
import br.inatel.project.quotantionmanagment.repository.StockRepository;
import br.inatel.project.quotantionmanagment.service.StockService;

@RestController
@RequestMapping
public class StockController {

	private StockRepository stockRepository;

	private StockService stockService;

	@Autowired
	public StockController(StockRepository stockRepository, StockService stockService) {
		this.stockRepository = stockRepository;
		this.stockService = stockService;
	}

	@GetMapping("/stocks")
	@Cacheable(value = "quotes")
	@ResponseBody
	public List<StockDto> list() {
		List<Stock> stocks = stockRepository.findAll();
		return StockDto.convert(stocks);
	}

	@GetMapping("/stocks/{stockId}")
	@ResponseBody
	public ResponseEntity<List<StockDto>> listByStockId(@PathVariable String stockId) {
		return stockService.listById(stockId);
	}

	@PostMapping("/stocks")
	@CacheEvict(value = "quotes", allEntries = true)
	public ResponseEntity<StockDto> addStockQuote(@RequestBody @Valid StockForm form, UriComponentsBuilder uriBuilder) {
		if (stockService.isPresent(form) == true) {
			URI uri = uriBuilder.path("stocks/{stockId}").buildAndExpand(form.getStockId().toUpperCase()).toUri();
			return stockService.addStockQuote(form, stockRepository, uri);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/stockcache")
	@CacheEvict(value = "stock", allEntries = true)
	public ResponseEntity<?> deleteCache() {
		return ResponseEntity.accepted().build();
	}
}