package stockfighter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import stockfighter.api.IStockFighterService;
import stockfighter.enums.Direction;
import stockfighter.enums.LevelControl;
import stockfighter.enums.LevelNames;
import stockfighter.enums.OrderTypes;
import stockfighter.pojo.Order;
import stockfighter.rest.reponse.CancelResponse;
import stockfighter.rest.reponse.IEntityClass;
import stockfighter.rest.reponse.LevelResponse;
import stockfighter.rest.reponse.OrderResponse;
import stockfighter.rest.reponse.OrderbookResponse;
import stockfighter.rest.reponse.OrdersReponse;
import stockfighter.rest.reponse.QuoteResponse;
import stockfighter.service.StockfighterService;

public class StockFighterTest {

	private final Logger slf4jLogger = LoggerFactory.getLogger(StockFighterTest.class);
	private final static String API_KEY = "2e5b8ebee62687ec9d8b5c5f619a9fd54053a999";

	private Injector injector;

	@Before
	public void setUp() throws Exception {
		injector = Guice.createInjector(new AbstractModule() {

			@Override
			protected void configure() {
				bind(IStockFighterService.class).to(StockfighterService.class);
			}
		});
	}

	@After
	public void tearDown() throws Exception {
		injector = null;
	}

	@Test
	public void testLevelControls()
			throws JsonGenerationException, JsonMappingException, URISyntaxException, IOException {

		LevelResponse level = injector.getInstance(StockfighterService.class).levelControls(LevelNames.FIRST_STEPS,
				LevelControl.START, null);
		assertTrue(level.isOk());
		assertNotNull(level.getInstanceId());

		level = injector.getInstance(StockfighterService.class).levelControls(LevelNames.FIRST_STEPS,
				LevelControl.RESTART, level.getInstanceId());
		assertTrue(level.isOk());
		assertNotNull(level.getInstanceId());

		level = injector.getInstance(StockfighterService.class).levelControls(LevelNames.FIRST_STEPS, LevelControl.STOP,
				level.getInstanceId());
		assertTrue(level.isOk());
		assertNull(level.getInstanceId());
	}

	@Test
	public void testPlaceOrderForStock()
			throws URISyntaxException, JsonGenerationException, JsonMappingException, IOException {

		Order order = new Order();
		order.setAccount("EXB123456");
		order.setPrice(1);
		order.setQty(1);
		order.setDirection(Direction.BUY);
		order.setOrderType(OrderTypes.MARKET);

		OrderResponse placeOrderForStock = injector.getInstance(StockfighterService.class).placeOrderForStock("TESTEX",
				"FOOBAR", order);

		assertTrue(placeOrderForStock.isOk());
		assertEquals(placeOrderForStock.getAccount(), "EXB123456");
		assertEquals(placeOrderForStock.getDirection(), Direction.BUY.name().toLowerCase());
		assertEquals(placeOrderForStock.getVenue(), "TESTEX");
		assertEquals(placeOrderForStock.getSymbol(), "FOOBAR");

	}

	@Test
	public void testVenueUp() throws URISyntaxException, JsonGenerationException, JsonMappingException, IOException {

		assertTrue(injector.getInstance(StockfighterService.class).isVenueUp("TESTEX"));
	}

	@Test
	public void testOrderBookForStock() throws URISyntaxException, JsonParseException, JsonMappingException,
			UnsupportedOperationException, IOException {

		OrderbookResponse orderBookForStock = injector.getInstance(StockfighterService.class).getOrderBookForStock("TESTEX",
				"FOOBAR");

		assertNotNull(orderBookForStock);
		assertEquals(OrderbookResponse.class, orderBookForStock.getClass());
	}

	@Test
	@Ignore
	public void testStatusForAllOrders() throws URISyntaxException {

		OrdersReponse ordersReponse = null;
		HttpResponse httpResponse = null;
		CloseableHttpClient httpClient = getHttpClient();
		HttpGet httpGet = new HttpGet("https://api.stockfighter.io/ob/api/venues/:venue/accounts/:account/orders");

		httpGet.addHeader("X-Starfighter-Authorization", API_KEY);

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("venues", "TESTEX"));
		nameValuePairs.add(new BasicNameValuePair("accounts", "EXB123456"));
		nameValuePairs.add(new BasicNameValuePair("stocks", "FOOBAR"));
		nameValuePairs.add(new BasicNameValuePair("orders", ""));

		final URIBuilder builder = new URIBuilder();
		URI uri = builder.setScheme("https").setHost("api.stockfighter.io").setPath("/ob/api")
				.addParameters(nameValuePairs).build();
		HttpGet httpGet2 = new HttpGet(uri);
		httpGet2.addHeader("X-Starfighter-Authorization", API_KEY);

		try {
			Date sentDate = new Date();
			slf4jLogger.info("POST: Requesting status of all orders in a stock. " + sentDate.toString());

			Date receiveDate = new Date();
			httpResponse = httpClient.execute(httpGet2);
			slf4jLogger.info("Response received. It took " + (receiveDate.getTime() - sentDate.getTime())
					+ " milliseconds to fulfill request.");

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException(
						"Failed : HTTP error code : " + httpResponse.getStatusLine().getStatusCode());
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ordersReponse = (OrdersReponse) parseReponse(httpResponse, OrdersReponse.class);

		assertNotNull(ordersReponse);
		assertEquals(ordersReponse.getClass(), OrdersReponse.class);

	}

	@Test
	@Ignore
	public void testCancel() {

		CancelResponse cancel = null;
		HttpResponse httpResponse = null;
		CloseableHttpClient httpClient = getHttpClient();
		HttpDelete httpDelete = new HttpDelete("https://api.stockfighter.io/ob/api/venues/ROBUST/stocks/ROBO/orders/1");

		try {
			Date sentDate = new Date();
			slf4jLogger.info("DELETE: Requesting Delete information. " + sentDate.toString());

			Date receiveDate = new Date();
			httpResponse = httpClient.execute(httpDelete);

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				slf4jLogger.error("Failed : HTTP error code : " + httpResponse.getStatusLine().getStatusCode());
				throw new RuntimeException(
						"Failed : HTTP error code : " + httpResponse.getStatusLine().getStatusCode());
			} else {
				slf4jLogger.info("Response received. It took " + (receiveDate.getTime() - sentDate.getTime())
						+ " milliseconds to cancel an order.");
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		cancel = (CancelResponse) parseReponse(httpResponse, CancelResponse.class);

		assertNotNull(cancel);
		assertEquals(cancel.getClass(), CancelResponse.class);

	}

	@Test
	public void testHeartbeat() throws URISyntaxException, JsonGenerationException, JsonMappingException, IOException {

		boolean heartbeat = injector.getInstance(StockfighterService.class).getHearBeat();
		assertEquals(true, heartbeat);
	}

	@Test
	public void testQuote() throws URISyntaxException, JsonGenerationException, JsonMappingException, IOException {

		QuoteResponse quote = injector.getInstance(StockfighterService.class).getQuote("TESTEX", "FOOBAR");

		assertEquals(QuoteResponse.class, quote.getClass());
	}

	private IEntityClass parseReponse(HttpResponse httpResponse, Class<? extends IEntityClass> clazz) {

		IEntityClass entityClass = null;
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			entityClass = objectMapper.readValue(new InputStreamReader(httpResponse.getEntity().getContent()), clazz);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return entityClass;
	}

	private static CloseableHttpClient getHttpClient() {

		return HttpClientBuilder.create().build();

	}

}
