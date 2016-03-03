package stockfighter;

import com.google.inject.AbstractModule;

import stockfighter.api.IStockFighterService;
import stockfighter.api.impl.StockfighterService;

public class SimpleModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IStockFighterService.class).to(StockfighterService.class);
	}
}
