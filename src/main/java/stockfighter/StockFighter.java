package stockfighter;

import java.io.IOException;
import java.net.URISyntaxException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.google.inject.Guice;
import com.google.inject.Injector;

import stockfighter.api.impl.StockfighterService;
import stockfighter.enums.Direction;
import stockfighter.enums.LevelControl;
import stockfighter.enums.LevelNames;
import stockfighter.enums.OrderTypes;
import stockfighter.pojo.Order;
import stockfighter.rest.reponse.LevelResponse;
import stockfighter.rest.reponse.OrderResponse;
import stockfighter.rest.reponse.OrderbookResponse;
import stockfighter.rest.reponse.Price;

public class StockFighter {

	public static void main(String[] args) throws InterruptedException, JsonGenerationException, JsonMappingException,
			URISyntaxException, IOException {

		/* DI code for the program */
		Injector injector = Guice.createInjector(new SimpleModule());
		StockfighterService stockfighterService = injector.getInstance(StockfighterService.class);
		/* ----------------------- */

		// 1) Start the level
		LevelResponse level = stockfighterService.levelControls(LevelNames.SELL_SIDE, LevelControl.START, null);

		String venue = (level.getVenues())[0];
		String ticker = (level.getTickers())[0];

		OrderbookResponse orderBookForStock = stockfighterService.getOrderBookForStock(venue, ticker);

		// BUY LOW, SELL HIGH algo

		Order order = new Order();

		// set the price
		Double priceAverage = orderBookForStock.getBids().stream().mapToInt(Price::getPrice).average().getAsDouble();
		order.setPrice(priceAverage.intValue());

		order.setQty(100);
		order.setDirection(Direction.BUY);
		order.setOrderType(OrderTypes.LIMIT);

		OrderResponse placeOrderForStock = stockfighterService.placeOrderForStock(venue, ticker, order);

		// *) Stop the level
		stockfighterService.levelControls(LevelNames.SELL_SIDE, LevelControl.STOP, level.getInstanceId());

	}

}
