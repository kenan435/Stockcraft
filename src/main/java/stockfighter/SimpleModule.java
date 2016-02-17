package stockfighter;

import com.google.inject.AbstractModule;

import stockfighter.service.IStockFighterService;
import stockfighter.service.StockfighterService;

public class SimpleModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IStockFighterService.class).to(StockfighterService.class);
	}
}
