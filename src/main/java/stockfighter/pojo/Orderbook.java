package stockfighter.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Orderbook {

	private boolean ok;
	private String venue;
	private String symbol;

	private List<Price> bids = new ArrayList<>();
	private List<Price> asks = new ArrayList<>();
	private Date ts;

	public Orderbook() {
		// TODO Auto-generated constructor stub
	}

	public Orderbook(boolean ok, String venue, String symbol, List<Price>bids, List<Price>asks, Date ts) {
		this.ok = ok;
		this.venue = venue; 
		this.symbol = symbol; 
		this.bids = bids; 
		this.asks = asks; 
		this.ts = ts;
	}

	/**
	 * @return the ok
	 */
	public boolean isOk() {
		return ok;
	}

	/**
	 * @param ok
	 *            the ok to set
	 */
	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * @return the venue
	 */
	public String getVenue() {
		return venue;
	}

	/**
	 * @param venue
	 *            the venue to set
	 */
	public void setVenue(String venue) {
		this.venue = venue;
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol
	 *            the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return the priceList
	 */
	public List<Price> getBids() {
		return bids;
	}

	/**
	 * @param bids
	 *            the priceList to set
	 */
	public void setBids(List<Price> bids) {
		this.bids = bids;
	}

	/**
	 * @return the askList
	 */
	public List<Price> getAsks() {
		return asks;
	}

	/**
	 * @param asks
	 *            the askList to set
	 */
	public void setAsks(List<Price> asks) {
		this.asks = asks;
	}

	/**
	 * @return the ts
	 */
	public Date getTs() {
		return ts;
	}

	/**
	 * @param ts
	 *            the ts to set
	 */
	public void setTs(Date ts) {
		this.ts = ts;
	}
}

/*
 * "ok": true, "venue": "OGEX", "symbol": "FAC", "bids": [ {"price": 5200,
 * "qty": 1, "isBuy": true}, ... ... ... {"price": 815, "qty": 15, "isBuy":
 * true}, {"price": 800, "qty": 12, "isBuy": true}, {"price": 800, "qty": 152,
 * "isBuy": true} ], "asks": [ {"price": 5205, "qty": 150, "isBuy": false},
 * {"price": 5205, "qty": 1, "isBuy": false}, ... ... ... {"price":
 * 1000000000000, "qty": 99999, "isBuy": false} ], "ts":
 * "2015-12-04T09:02:16.680986205Z" // timestamp we grabbed the book at }
 * 
 */