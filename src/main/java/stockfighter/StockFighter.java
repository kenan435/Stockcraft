package stockfighter;

import java.io.IOException;
import java.net.URISyntaxException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.google.inject.Guice;
import com.google.inject.Injector;

import stockfighter.pojo.Direction;
import stockfighter.pojo.Level;
import stockfighter.pojo.LevelControl;
import stockfighter.pojo.LevelNames;
import stockfighter.pojo.Order;
import stockfighter.pojo.OrderTypes;
import stockfighter.pojo.Orderbook;
import stockfighter.pojo.Quote;
import stockfighter.service.StockfighterService;

public class StockFighter {

	public static void main(String[] args) throws InterruptedException, JsonGenerationException, JsonMappingException,
			URISyntaxException, IOException {

		/* DI code for the program */
		Injector injector = Guice.createInjector(new SimpleModule());
		StockfighterService stockfighterService = injector.getInstance(StockfighterService.class);
		/* ----------------------- */

		// 1) Start the level
		Level level = stockfighterService.levelControls(LevelNames.SELL_SIDE, LevelControl.START, null);

		String venue = (level.getVenues())[0];
		String ticker = (level.getTickers())[0];

		Orderbook orderBookForStock = stockfighterService.getOrderBookForStock(venue, ticker);

		// BUY LOW, SELL HIGH algo

		Order order = new Order();
		order.setAccount(level.getAccount());
		order.setPrice(7000);
		order.setQty(100);
		order.setVenue(order.getVenue());
		order.setDirection(Direction.BUY);
		order.setStock(ticker);
		order.setOrderType(OrderTypes.LIMIT);
		stockfighterService.placeOrderForStock(order);

		// *) Stop the level
		stockfighterService.levelControls(LevelNames.SELL_SIDE, LevelControl.STOP, level.getInstanceId());

		// String account= "";
		// int price = 0;
		// int qty = 0;
		// String venue = "";
		// String stock = "";
		// Direction direction = null;
		// OrderTypes orderTypes = null;
		//
		// Order order = new Order();
		// order.setAccount(account);
		// order.setPrice(price);
		// order.setQty(qty);
		// order.setVenue(venue);
		// order.setDirection(direction);
		// order.setStock(stock);
		// order.setOrderType(orderTypes);
		//
		// stockfighterService.placeOrderForStock(order);

	}

}
