package br.inatel.project.quotantionmanagment.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.project.quotantionmanagment.model.Stock;

public interface StockRepository extends JpaRepository<Stock, UUID> {

	public List<Stock> findByStockId(String stockId);

	public boolean existsByStockId(String stockId);

	public Stock getByStockId(String upperCase);

}
