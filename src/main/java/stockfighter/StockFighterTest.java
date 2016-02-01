package stockfighter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
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

import stockfighter.pojo.Cancel;
import stockfighter.pojo.EntityClass;
import stockfighter.pojo.Heartbeat;
import stockfighter.pojo.Orderbook;
import stockfighter.pojo.OrdersReponse;
import stockfighter.pojo.Quote;
import stockfighter.service.IStockFighterService;
import stockfighter.service.StockfighterService;

public class StockFighterTest {

	private final Logger slf4jLogger = LoggerFactory.getLogger(StockFighterTest.class);
	private final static String API_KEY = "2e5b8ebee62687ec9d8b5c5f619a9fd54053a999";
	private final static String SCHEMA = "https";
	private final static String HOST = "api.stockfighter.io";
	private final static String PATH = "/ob/api";

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
	public void testVenueUp() throws URISyntaxException {

		assertEquals(true, injector.getInstance(StockfighterService.class).isVenueUp("TESTEX"));
	}

	@Test
	public void testOrderBookForStock() throws URISyntaxException, JsonParseException, JsonMappingException,
			UnsupportedOperationException, IOException {

		Orderbook orderbook = null;
		HttpResponse httpResponse = null;
		CloseableHttpClient httpClient = getHttpClient();

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("venues", "TESTEX"));
		nameValuePairs.add(new BasicNameValuePair("stocks", "FOOBAR"));

		// final URIBuilder builder = new URIBuilder();
		// URI uri =
		// builder.setScheme("https").setHost("api.stockfighter.io").setPath("/ob/api")
		// .addParameters(nameValuePairs).build();

		HttpGet httpGet = new HttpGet("https://api.stockfighter.io/ob/api/venues/TESTEX/stocks/FOOBAR");

		try {
			Date sentDate = new Date();
			slf4jLogger.info("POST: Requesting status of all orders in a stock. " + sentDate.toString());

			Date receiveDate = new Date();
			httpResponse = httpClient.execute(httpGet);
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

		orderbook = (Orderbook) parseReponse(httpResponse, Orderbook.class);

		assertNotNull(orderbook);
		assertEquals(orderbook.getClass(), Orderbook.class);

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

		Cancel cancel = null;
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

		cancel = (Cancel) parseReponse(httpResponse, Cancel.class);

		assertNotNull(cancel);
		assertEquals(cancel.getClass(), Cancel.class);

	}

	@Test
	public void testHeartbeat() {

		Heartbeat heartbeat = null;
		HttpResponse httpResponse = null;
		CloseableHttpClient httpClient = getHttpClient();
		HttpGet httpGet = new HttpGet("https://api.stockfighter.io/ob/api/heartbeat");

		try {
			Date sentDate = new Date();
			slf4jLogger.info("POST: Requesting hearbeat information. " + sentDate.toString());

			Date receiveDate = new Date();
			httpResponse = httpClient.execute(httpGet);
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

		heartbeat = (Heartbeat) parseReponse(httpResponse, Heartbeat.class);

		assertNotNull(heartbeat);
		assertEquals(heartbeat.getClass(), Heartbeat.class);

	}

	@Test
	public void testQuote() {

		Quote quote = null;
		HttpResponse httpResponse = null;
		CloseableHttpClient httpClient = getHttpClient();
		HttpGet httpGet = new HttpGet("https://api.stockfighter.io/ob/api/venues/TESTEX/stocks/FOOBAR/quote");

		try {
			httpResponse = httpClient.execute(httpGet);

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

		quote = (Quote) parseReponse(httpResponse, Quote.class);

		System.out.println(quote.getLastTrade());
	}

	private static String encodeURI(URI uri) {

		return StringUtils.replaceEach(uri.toString(), new String[] { "#", "?", "=" }, new String[] { "/", "/", "/" });

	}

	private static EntityClass parseReponse(HttpResponse httpResponse, Class<? extends EntityClass> clazz) {

		EntityClass entityClass = null;
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
