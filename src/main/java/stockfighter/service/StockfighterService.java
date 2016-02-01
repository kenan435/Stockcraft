package stockfighter.service;

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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import stockfighter.pojo.EntityClass;
import stockfighter.pojo.Orderbook;
import stockfighter.pojo.VenueResponse;

public class StockfighterService implements IStockFighterService {

	final Logger slf4jLogger = LoggerFactory.getLogger(StockfighterService.class);

	public boolean isVenueUp(String venue) throws URISyntaxException {

		VenueResponse venueResponse = null;
		HttpResponse httpResponse = null;
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

	private static String encodeURI(URI uri) {

		return StringUtils.replaceEach(uri.toString(), new String[] { "#", "?", "=", "&" },
				new String[] { "/", "/", "/", "/" });

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

	@Override
	public Orderbook orderBookForStock(String venue, String stock) throws URISyntaxException {

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

		return orderbook;
	}

}
