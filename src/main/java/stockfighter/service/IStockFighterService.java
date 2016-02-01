package stockfighter.service;

import java.net.URISyntaxException;

import stockfighter.pojo.Orderbook;

public interface IStockFighterService {

	final static String API_KEY = "2e5b8ebee62687ec9d8b5c5f619a9fd54053a999";
	final static String SCHEMA = "https";
	final static String HOST = "api.stockfighter.io";
	final static String PATH = "/ob/api";

	public boolean isVenueUp(String venue) throws URISyntaxException;

	public Orderbook orderBookForStock(String venue, String stock) throws URISyntaxException;
}
