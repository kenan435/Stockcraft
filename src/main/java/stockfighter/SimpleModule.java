package stockfighter;

import com.google.inject.AbstractModule;

import stockfighter.api.IStockFighterService;
import stockfighter.service.StockfighterService;

public class SimpleModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IStockFighterService.class).to(StockfighterService.class);
	}
}
