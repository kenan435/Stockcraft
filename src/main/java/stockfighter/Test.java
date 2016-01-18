package stockfighter;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.ws.rs.ClientErrorException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.map.ObjectMapper;

import stockfighter.pojo.EntityClass;
import stockfighter.pojo.Heartbeat;
import stockfighter.pojo.Quote;

public class Test {

	private Heartbeat testHeartbeat() {

		Heartbeat heartbeat = null;
		HttpResponse httpResponse = null;
		CloseableHttpClient httpClient = getHttpClient();
		HttpGet httpGet = new HttpGet("https://api.stockfighter.io/ob/api/heartbeat");

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

		heartbeat = (Heartbeat) parseReponse(httpResponse, Heartbeat.class);

		System.out.println(heartbeat.getOk());
		return heartbeat;
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

	public static void main(String[] args) throws ClientErrorException, IOException {

		Test test = new Test();

		try {
			while (true) {

				test.testHeartbeat();
				test.testQuote();


				HttpGet httpGet_2 = new HttpGet("https://api.stockfighter.io/ob/api/venues/TESTEX/stocks/FOOBAR");

				// List<NameValuePair> urlParameters = new
				// ArrayList<NameValuePair>();
				// urlParameters.add(new BasicNameValuePair("Cookie:api_key",
				// "2e5b8ebee62687ec9d8b5c5f619a9fd54053a999"));
				//
				// add header
				// httpGet_2.setHeader("api_key","2e5b8ebee62687ec9d8b5c5f619a9fd54053a999");

				// HttpResponse response3 = httpClient.execute(httpGet_2);
			
				// InputStreamReader inputStreamReader = new
				// InputStreamReader((response3.getEntity().getContent()));

				// Orderbook orderbook = mapper.readValue(new
				// InputStreamReader(response3.getEntity().getContent()),
				// Orderbook.class);
				
				// orderbook.getBids().forEach(bid ->
				// System.out.println(bid.getPrice() + " -- " +
				// orderbook.getSymbol()));

				Thread.sleep(1000);

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

	}
}
