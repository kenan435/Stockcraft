package stockfighter;

import java.io.IOException;
import java.net.URISyntaxException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.google.inject.Guice;
import com.google.inject.Injector;

import stockfighter.pojo.Direction;
import stockfighter.pojo.Order;
import stockfighter.pojo.OrderTypes;
import stockfighter.pojo.Quote;
import stockfighter.service.StockfighterService;

public class StockFighter {

	public static void main(String[] args) throws InterruptedException, JsonGenerationException, JsonMappingException,
			URISyntaxException, IOException {

		// DI code for the program
		Injector injector = Guice.createInjector(new SimpleModule());
		StockfighterService stockfighterService = injector.getInstance(StockfighterService.class);

		// Global instance Variable that holds the current amount of shares
		// bought
		int shares;

		//
		String account = "";
		int price = 0;
		int qty = 0;
		String venue = "";
		String stock = "";
		Direction direction = null;
		OrderTypes orderTypes = null;

		Order order = new Order();
		order.setAccount(account);
		order.setPrice(price);
		order.setQty(qty);
		order.setVenue(venue);
		order.setDirection(direction);
		order.setStock(stock);
		order.setOrderType(orderTypes);

		stockfighterService.placeOrderForStock(order);

	}

}
