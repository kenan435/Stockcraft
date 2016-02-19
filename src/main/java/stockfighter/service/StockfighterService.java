package stockfighter.service;

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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import stockfighter.api.IStockFighterService;
import stockfighter.enums.LevelControl;
import stockfighter.enums.LevelNames;
import stockfighter.pojo.Order;
import stockfighter.rest.reponse.CancelResponse;
import stockfighter.rest.reponse.IEntityClass;
import stockfighter.rest.reponse.HeartbeatResponse;
import stockfighter.rest.reponse.LevelResponse;
import stockfighter.rest.reponse.OrderResponse;
import stockfighter.rest.reponse.OrderbookResponse;
import stockfighter.rest.reponse.QuoteResponse;
import stockfighter.rest.reponse.VenueResponse;

/**
 * Service class implementing the IStockFighterService
 * 
 * @author kenan
 *
 */
public class StockfighterService implements IStockFighterService {

	final Logger slf4jLogger = LoggerFactory.getLogger(StockfighterService.class);

	@Override
	public LevelResponse levelControls(LevelNames levelNames, LevelControl levelControl, String instanceID)
			throws URISyntaxException, JsonGenerationException, JsonMappingException, IOException {

		if ((levelControl.equals(LevelControl.RESTART) || levelControl.equals(LevelControl.STOP)
				|| levelControl.equals(LevelControl.RESUME)) && instanceID == null) {
			throw new RuntimeException("Cannot stop, restart or resume a level withouth instanceID");
		}

		String path = "/gm";
		final String host = "www.stockfighter.io";

		// POST https://www.stockfighter.io/gm/levels/first_steps HTTP/1.1
		// POST https://www.stockfighter.io/gm/levels/first_steps $HEADER

		LevelResponse level = null;
		HttpResponse httpResponse = null;
		CloseableHttpClient httpClient = getHttpClient();

		path = buildPathProperty(levelNames, levelControl, instanceID, path);

		URIBuilder uriBuilder = new URIBuilder();
		URI uri = uriBuilder.setScheme(SCHEMA).setHost(host).setPath(path).build();

		HttpPost post = new HttpPost(encodeURI(uri));
		post.addHeader("X-Starfighter-Authorization", API_KEY);

		try {
			Date sentDate = new Date();
			slf4jLogger.info("POST: Starting new level. " + sentDate.toString());

			Date receiveDate = new Date();
			httpResponse = httpClient.execute(post);
			slf4jLogger.info("Response received. It took " + (receiveDate.getTime() - sentDate.getTime())
					+ " milliseconds to fulfill request.");

			// slf4jLogger.info(EntityUtils.toString(httpResponse.getEntity()));

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException(
						"Failed : HTTP error code : " + httpResponse.getStatusLine().getStatusCode());
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		level = (LevelResponse) parseReponse(httpResponse, LevelResponse.class);

		return level;
	}

	private static String buildPathProperty(LevelNames levelNames, LevelControl levelControl, String instanceID,
			String path) {
		switch (levelControl) {
		case START:
			path = path + LEVELS + levelNames.name().toLowerCase();
			break;
		case STOP:
		case RESTART:
		case RESUME:
			path = path + INSTANCES + instanceID + "/" + levelControl.name().toLowerCase();
			break;
		default:
			break;
		}
		return path;
	}

	@Override
	public OrderResponse placeOrderForStock(String venue, String ticker, Order order)
			throws URISyntaxException, JsonGenerationException, JsonMappingException, IOException {

		OrderResponse orderResponse = null;
		HttpResponse httpResponse = null;
		CloseableHttpClient httpClient = getHttpClient();

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("venues", venue));
		nameValuePairs.add(new BasicNameValuePair("stocks", ticker));

		Gson gson = new Gson();
		StringEntity entity = new StringEntity(gson.toJson(order));

		// https://api.stockfighter.io/ob/api/venues/TESTEX/stocks/FOOBAR/orders

		URIBuilder uriBuilder = new URIBuilder();
		URI uri = uriBuilder.setScheme(SCHEMA).setHost(HOST).setPath(PATH).addParameters(nameValuePairs)
				.setFragment("orders").build();

		HttpPost post = new HttpPost(encodeURI(uri));
		post.setEntity(entity);
		post.addHeader("X-Starfighter-Authorization", API_KEY);

		try {
			Date sentDate = new Date();
			slf4jLogger.info("POST: Place order for stock. " + sentDate.toString());

			Date receiveDate = new Date();
			httpResponse = httpClient.execute(post);
			slf4jLogger.info("Response received. It took " + (receiveDate.getTime() - sentDate.getTime())
					+ " milliseconds to fulfill request.");

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException(
						"Failed : HTTP error code : " + httpResponse.getStatusLine().getStatusCode());
			}

		} catch (ClientProtocolException e) {
			slf4jLogger.error(e.getMessage());
		} catch (IOException e) {
			slf4jLogger.error(e.getMessage());
		}

		orderResponse = (OrderResponse) parseReponse(httpResponse, OrderResponse.class);

		return orderResponse;
	}

	@Override
	public QuoteResponse getQuote(String venue, String stock)
			throws URISyntaxException, JsonGenerationException, JsonMappingException, IOException {

		QuoteResponse quote = null;
		HttpResponse httpResponse = null;
		CloseableHttpClient httpClient = getHttpClient();

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("venues", venue));
		nameValuePairs.add(new BasicNameValuePair("stocks", stock));

		final URIBuilder builder = new URIBuilder();
		URI uri = builder.setScheme(SCHEMA).setHost(HOST).setPath(PATH).addParameters(nameValuePairs)
				.setFragment("quote").build();

		HttpGet httpGet = new HttpGet(encodeURI(uri));

		try {
			Date sentDate = new Date();
			slf4jLogger.info("POST: Requesting Quote information. " + sentDate.toString());

			Date receiveDate = new Date();
			httpResponse = httpClient.execute(httpGet);
			slf4jLogger.info("Response received. It took " + (receiveDate.getTime() - sentDate.getTime())
					+ " milliseconds to fulfill request.");

			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException(
						"Failed : HTTP error code : " + httpResponse.getStatusLine().getStatusCode());
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		quote = (QuoteResponse) parseReponse(httpResponse, QuoteResponse.class);

		return quote;
	}

	public boolean getHearBeat() throws URISyntaxException, JsonGenerationException, JsonMappingException, IOException {

		HeartbeatResponse heartbeat = null;
		HttpResponse httpResponse = null;
		CloseableHttpClient httpClient = getHttpClient();
		final URIBuilder builder = new URIBuilder();
		URI uri = builder.setScheme(SCHEMA).setHost(HOST).setPath(PATH).setFragment("heartbeat").build();

		HttpGet httpGet = new HttpGet(encodeURI(uri));

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

		heartbeat = (HeartbeatResponse) parseReponse(httpResponse, HeartbeatResponse.class);

		return heartbeat.getOk();
	}

	@Override
	public boolean isVenueUp(String venue)
			throws URISyntaxException, JsonGenerationException, JsonMappingException, IOException {

		VenueResponse venueResponse = null;
		HttpResponse httpResponse = null;
		CloseableHttpClient httpClient = getHttpClient();

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("venues", venue));

		final URIBuilder builder = new URIBuilder();
		URI uri = builder.setScheme(SCHEMA).setHost(HOST).setPath(PATH).addParameters(nameValuePairs)
				.setFragment("heartbeat").build();

		HttpGet httpGet = new HttpGet(encodeURI(uri));

		// https://api.stockfighter.io/ob/api/venues/:venue/heartbeat
		// https://api.stockfighter.io/ob/api/venues/TESTEX/heartbeat HTTP/1.1

		try {
			Date sentDate = new Date();
			slf4jLogger.info("POST: Requesting status of a venue at: " + sentDate.toString());

			Date receiveDate = new Date();
			httpResponse = httpClient.execute(httpGet);
			slf4jLogger
					.info("RESPONSE: Received in " + (receiveDate.getTime() - sentDate.getTime()) + " milliseconds.");

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

		venueResponse = (VenueResponse) parseReponse(httpResponse, VenueResponse.class);

		return venueResponse.isOk();
	}

	@Override
	public OrderbookResponse getOrderBookForStock(String venue, String stock)
			throws URISyntaxException, JsonGenerationException, JsonMappingException, IOException {

		OrderbookResponse orderbook = null;
		HttpResponse httpResponse = null;
		CloseableHttpClient httpClient = getHttpClient();

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("venues", venue));
		nameValuePairs.add(new BasicNameValuePair("stocks", stock));

		final URIBuilder builder = new URIBuilder();
		URI uri = builder.setScheme(SCHEMA).setHost(HOST).setPath(PATH).addParameters(nameValuePairs).build();

		HttpGet httpGet = new HttpGet(encodeURI(uri));

		try {
			Date sentDate = new Date();
			slf4jLogger.info("POST: Requesting status of all orders in a stock. " + sentDate.toString() + " >>> "
					+ encodeURI(uri));

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

		orderbook = (OrderbookResponse) parseReponse(httpResponse, OrderbookResponse.class);

		return orderbook;
	}

	@Override
	public void testCancel() throws JsonGenerationException, JsonMappingException, IOException {

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

	private static String encodeURI(URI uri) {

		return StringUtils.replaceEach(uri.toString(), new String[] { "#", "?", "=", "&" },
				new String[] { "/", "/", "/", "/" });

	}

	private static IEntityClass parseReponse(HttpResponse httpResponse, Class<? extends IEntityClass> clazz)
			throws JsonGenerationException, JsonMappingException, IOException {

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
