package br.inatel.project.quotantionmanagment.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.inatel.project.quotantionmanagment.model.Stock;

class StockDtoTest {

	@Mock
	private Stock stock;

	@Mock
	private StockDto dto;

	@BeforeEach
	void beforeEach() {
		MockitoAnnotations.openMocks(this);
		this.stock = new Stock("PETR4", new HashMap<LocalDate, String>());
		this.dto = new StockDto(stock);
	}

	@DisplayName("Should Convert List of Stock to List of StockDto")
	@Test
	void shouldConvertToListDto() {
		List<Stock> list = list();
		List<StockDto> dtos = StockDto.convert(list);
		System.out.println(dtos.get(0).getClass());
		assertEquals(dtos.get(0).getClass(), StockDto.class);

	}

	public List<Stock> list() {
		List<Stock> list = new ArrayList<>();
		HashMap<LocalDate, String> map1 = new HashMap<LocalDate, String>();
		HashMap<LocalDate, String> map2 = new HashMap<LocalDate, String>();
		map1.put(LocalDate.of(2019, 1, 3), "15");
		map2.put(LocalDate.of(2019, 2, 3), "15");
		map1.put(LocalDate.of(2019, 1, 3), "15");
		map2.put(LocalDate.of(2019, 2, 3), "15");
		list.add(new Stock("PETR4", map1));
		list.add(new Stock("VALE5", map2));
		return list;
	}

}