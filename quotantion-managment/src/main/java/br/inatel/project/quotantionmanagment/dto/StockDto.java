package br.inatel.project.quotantionmanagment.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import br.inatel.project.quotantionmanagment.model.Stock;

public class StockDto {
	
	private UUID id;
	private String stockId;
	private Map<LocalDate, String> quotes;
	
	public StockDto(Stock stock) {
		this.id = stock.getId();
		this.stockId = stock.getStockId();
		this.quotes = stock.getQuotes();
		
	}

	public static List<StockDto> convert(List<Stock> stocks) {
		return stocks.stream()
			.map(s -> new StockDto(s))
			.collect(Collectors.toList());
	}

	public UUID getId() {
		return id;
	}

	public String getStockId() {
		return stockId;
	}

	public Map<LocalDate, String> getQuotes() {
		return quotes;
	}

	
}

