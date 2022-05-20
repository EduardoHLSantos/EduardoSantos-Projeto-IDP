package br.inatel.project.quotantionmanagment.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;

@Entity
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private String stockId;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyColumn(name = "date")
	private Map<LocalDate, String> quotes;

	public Stock(String stockId, Map<LocalDate, String> quotes) {
		this.stockId = stockId;
		this.quotes = quotes;
	}

	protected Stock() {

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
