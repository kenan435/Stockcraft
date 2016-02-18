package stockfighter.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import stockfighter.pojo.EntityClass;
import stockfighter.pojo.Heartbeat;
import stockfighter.pojo.Level;
import stockfighter.pojo.LevelControl;
import stockfighter.pojo.Order;
import stockfighter.pojo.OrderResponse;
import stockfighter.pojo.Orderbook;
import stockfighter.pojo.Quote;
import stockfighter.pojo.VenueResponse;

/**
 * Service class implementing the IStockFighterService
 * 
 * @author kenan
 *
 */
public class StockfighterService implements IStockFighterService {

	final Logger slf4jLogger = LoggerFactory.getLogger(StockfighterService.class);

	public Level startLevel(String levelName, LevelControl levelControl, String instanceID)
			throws URISyntaxException, JsonGenerationException, JsonMappingException, IOException {

		if ((levelControl.equals(LevelControl.RESTART) || levelControl.equals(LevelControl.STOP)
				|| levelControl.equals(LevelControl.RESUME)) && instanceID == null) {
			throw new RuntimeException("Cannot stop, restart or resume a level withouth instanceID");
		}

		String path = null;
		final String host = "www.stockfighter.io";

		// POST https://www.stockfighter.io/gm/levels/first_steps HTTP/1.1
		// POST https://www.stockfighter.io/gm/levels/first_steps $HEADER

		Level level = null;
		HttpResponse httpResponse = null;
		CloseableHttpClient httpClient = getHttpClient();

		switch (levelControl) {
		case START:
			path = "/gm/levels/" + levelName;
			break;
		case STOP:
		case RESTART:
		case RESUME:
			path = "/gm/instances/" + instanceID + levelName.toString();
			break;
		default:
			break;
		}

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		level = (Level) parseReponse(httpResponse, Level.class);

		return level;
	}

	public void restartLevel() {

		// GET https://www.stockfighter.io/gm/instances/314159

	}

	@Override
	public OrderResponse placeOrderForStock(Order order)
			throws URISyntaxException, JsonGenerationException, JsonMappingException, IOException {

		OrderResponse orderResponse = null;
		HttpResponse httpResponse = null;
		CloseableHttpClient httpClient = getHttpClient();

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("account", order.getAccount()));
		nameValuePairs.add(new BasicNameValuePair("venues", order.getVenue()));
		nameValuePairs.add(new BasicNameValuePair("stocks", order.getStock()));
		nameValuePairs.add(new BasicNameValuePair("price", Integer.toString(order.getPrice())));
		nameValuePairs.add(new BasicNameValuePair("qty", Integer.toString(order.getQty())));
		nameValuePairs.add(new BasicNameValuePair("direction", order.getDirection().toString()));
		nameValuePairs.add(new BasicNameValuePair("orderType", order.getOrderType().toString()));

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		orderResponse = (OrderResponse) parseReponse(httpResponse, OrderResponse.class);

		return orderResponse;
	}

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
	public Quote getQuote(String venue, String stock)
			throws URISyntaxException, JsonGenerationException, JsonMappingException, IOException {

		Quote quote = null;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		quote = (Quote) parseReponse(httpResponse, Quote.class);

		return quote;
	}

	public boolean getHearBeat() throws URISyntaxException, JsonGenerationException, JsonMappingException, IOException {

		Heartbeat heartbeat = null;
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

		heartbeat = (Heartbeat) parseReponse(httpResponse, Heartbeat.class);

		return heartbeat.getOk();
	}

	public boolean isVenueUp(String venue)
			throws URISyntaxException, JsonGenerationException, JsonMappingException, IOException {

		VenueResponse venueResponse = null;
		HttpResponse httpResponse = null;
		HttpEntity httpEntity = null;
		CloseableHttpClient httpClient = getHttpClient();

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("venues", venue));

		final URIBuilder builder = new URIBuilder();
		URI uri = builder.setScheme(SCHEMA).setHost(HOST).setPath(PATH).addParameters(nameValuePairs)
				.setFragment("heartbeat").build();

		HttpGet httpGet = new HttpGet(encodeURI(uri));

		try {
			Date sentDate = new Date();
			slf4jLogger.info("POST: Requesting status of a venue at: " + sentDate.toString());

			Date receiveDate = new Date();
			httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity2 = httpResponse.getEntity();
			EntityUtils.toString(httpEntity2);
			httpResponse.setEntity(httpEntity2);
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
	public Orderbook getOrderBookForStock(String venue, String stock)
			throws URISyntaxException, JsonGenerationException, JsonMappingException, IOException {

		Orderbook orderbook = null;
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

		orderbook = (Orderbook) parseReponse(httpResponse, Orderbook.class);

		return orderbook;
	}

	private static String encodeURI(URI uri) {

		return StringUtils.replaceEach(uri.toString(), new String[] { "#", "?", "=", "&" },
				new String[] { "/", "/", "/", "/" });

	}

	private static EntityClass parseReponse(HttpResponse httpResponse, Class<? extends EntityClass> clazz)
			throws JsonGenerationException, JsonMappingException, IOException {

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
