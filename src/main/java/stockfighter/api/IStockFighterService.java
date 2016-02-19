package stockfighter.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import stockfighter.enums.LevelControl;
import stockfighter.enums.LevelNames;
import stockfighter.pojo.Order;
import stockfighter.rest.reponse.LevelResponse;
import stockfighter.rest.reponse.OrderResponse;
import stockfighter.rest.reponse.OrderbookResponse;
import stockfighter.rest.reponse.QuoteResponse;

public interface IStockFighterService {

	// REST props
	final static String API_KEY = "2e5b8ebee62687ec9d8b5c5f619a9fd54053a999";
	final static String SCHEMA = "https";
	final static String HOST = "api.stockfighter.io";
	final static String PATH = "/ob/api";

	// Interfacing with the gamemaster properties
	final static String URL = "https://www.stockfighter.io/gm";
	final static String LEVELS = "/levels/";
	final static String INSTANCES = "/instances/";

	/**
	 * @param venue
	 * @return
	 * @throws URISyntaxException
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public boolean isVenueUp(String venue)
			throws URISyntaxException, JsonGenerationException, JsonMappingException, IOException;

	/**
	 * @param venue
	 * @param stock
	 * @return
	 * @throws URISyntaxException
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public OrderbookResponse getOrderBookForStock(String venue, String stock)
			throws URISyntaxException, JsonGenerationException, JsonMappingException, IOException;

	public OrderResponse placeOrderForStock(String venue, String ticker, Order order)
			throws UnsupportedEncodingException, URISyntaxException, JsonGenerationException, JsonMappingException,
			IOException;

	public LevelResponse levelControls(LevelNames firstSteps, LevelControl levelControl, String instanceID)
			throws URISyntaxException, JsonGenerationException, JsonMappingException, IOException;

	/**
	 * Get a quick look at the most recent trade information for a stock.
	 * 
	 * @param venue
	 * @param stock
	 * @return a quote object
	 * @throws URISyntaxException
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public QuoteResponse getQuote(String venue, String stock)
			throws URISyntaxException, JsonGenerationException, JsonMappingException, IOException;

	public void testCancel() throws JsonGenerationException, JsonMappingException, IOException;
}
