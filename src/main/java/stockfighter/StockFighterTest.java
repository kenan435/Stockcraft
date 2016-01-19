package stockfighter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import stockfighter.pojo.Cancel;
import stockfighter.pojo.EntityClass;
import stockfighter.pojo.Heartbeat;
import stockfighter.pojo.Orders;
import stockfighter.pojo.OrdersReponse;
import stockfighter.pojo.Quote;

public class StockFighterTest {

	private final Logger slf4jLogger = LoggerFactory.getLogger(StockFighterTest.class);

	@Test
	public void testStatusForAllOrders() {

		OrdersReponse ordersReponse = null;
		HttpResponse httpResponse = null;
		CloseableHttpClient httpClient = getHttpClient();
		HttpGet httpGet = new HttpGet(
				"https://api.stockfighter.io/ob/api/venues/TESTEX/accounts/EXB123456/stocks/FOOBAR/orders");
		
		httpGet.addHeader("X-Starfighter-Authorization", "2e5b8ebee62687ec9d8b5c5f619a9fd54053a999");

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

	private Quote testQuote() {

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
		return quote;
	}

	private EntityClass parseReponse(HttpResponse httpResponse, Class<? extends EntityClass> clazz) {

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

	private CloseableHttpClient getHttpClient() {

		return HttpClientBuilder.create().build();

	}

	/*
	 * public static void main(String[] args) throws ClientErrorException,
	 * IOException {
	 * 
	 * Test test = new Test();
	 * 
	 * try { while (true) {
	 * 
	 * test.testHeartbeat(); test.testQuote();
	 * 
	 * 
	 * 
	 * HttpGet httpGet_2 = new
	 * HttpGet("https://api.stockfighter.io/ob/api/venues/TESTEX/stocks/FOOBAR")
	 * ;
	 * 
	 * // List<NameValuePair> urlParameters = new // ArrayList<NameValuePair>();
	 * // urlParameters.add(new BasicNameValuePair("Cookie:api_key", //
	 * "2e5b8ebee62687ec9d8b5c5f619a9fd54053a999")); // // add header //
	 * httpGet_2.setHeader("api_key","2e5b8ebee62687ec9d8b5c5f619a9fd54053a999")
	 * ;
	 * 
	 * // HttpResponse response3 = httpClient.execute(httpGet_2);
	 * 
	 * // InputStreamReader inputStreamReader = new //
	 * InputStreamReader((response3.getEntity().getContent()));
	 * 
	 * // Orderbook orderbook = mapper.readValue(new //
	 * InputStreamReader(response3.getEntity().getContent()), //
	 * Orderbook.class);
	 * 
	 * // orderbook.getBids().forEach(bid -> //
	 * System.out.println(bid.getPrice() + " -- " + // orderbook.getSymbol()));
	 * 
	 * Thread.sleep(1000);
	 * 
	 * }
	 * 
	 * } catch (Exception e) {
	 * 
	 * e.printStackTrace();
	 * 
	 * }
	 * 
	 * }
	 */
}
