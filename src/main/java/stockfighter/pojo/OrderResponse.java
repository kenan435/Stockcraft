package stockfighter.pojo;

import java.util.Date;
import java.util.List;

public class OrderResponse {

	boolean ok;
	String symbol;
	String venue;
	String direction;
	int originalQty;
	int qty; // this is the quantity *left outstanding*
	int price; // the price on the order -- may not match that of fills!
	String type; // "limit",
	int id; // guaranteed unique *on this venue*
	String account;
	Date ts; // ISO-8601 timestamp for when we received order

	List<Price> fills;	
	/*
	 * "fills": [ { "price": 5050, "qty": 50 "ts": "2015-07-05T22:16:18+00:00"
	 * }, ... // may have zero or multiple fills. Note this order presumably has
	 * a total of 80 shares worth ],
	 */
	
	int totalFilled;
	boolean open; // ": true

	public OrderResponse() {
		// TODO Auto-generated constructor stub
	}

	public OrderResponse(boolean ok, String symbol, String venue, String direction, int originalQty, int qty, int price,
			String type, int id, String account, List<Price> fills, int totalFilled, boolean open) {
		this.ok = ok;
		this.symbol = symbol;
		this.venue = venue;
		this.direction = direction;
		this.originalQty = originalQty;
		this.qty = qty;
		this.price = price;
		this.type = type;
		this.id = id;
		this.account = account;
		this.fills = fills;
		this.totalFilled = totalFilled;
		this.open = open;

	}

	/**
	 * @return the ok
	 */
	public boolean isOk() {
		return ok;
	}

	/**
	 * @param ok the ok to set
	 */
	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return the venue
	 */
	public String getVenue() {
		return venue;
	}

	/**
	 * @param venue the venue to set
	 */
	public void setVenue(String venue) {
		this.venue = venue;
	}

	/**
	 * @return the direction
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * @return the originalQty
	 */
	public int getOriginalQty() {
		return originalQty;
	}

	/**
	 * @param originalQty the originalQty to set
	 */
	public void setOriginalQty(int originalQty) {
		this.originalQty = originalQty;
	}

	/**
	 * @return the qty
	 */
	public int getQty() {
		return qty;
	}

	/**
	 * @param qty the qty to set
	 */
	public void setQty(int qty) {
		this.qty = qty;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the ts
	 */
	public Date getTs() {
		return ts;
	}

	/**
	 * @param ts the ts to set
	 */
	public void setTs(Date ts) {
		this.ts = ts;
	}

	/**
	 * @return the fills
	 */
	public List<Price> getFills() {
		return fills;
	}

	/**
	 * @param fills the fills to set
	 */
	public void setFills(List<Price> fills) {
		this.fills = fills;
	}

	/**
	 * @return the totalFilled
	 */
	public int getTotalFilled() {
		return totalFilled;
	}

	/**
	 * @param totalFilled the totalFilled to set
	 */
	public void setTotalFilled(int totalFilled) {
		this.totalFilled = totalFilled;
	}

	/**
	 * @return the open
	 */
	public boolean isOpen() {
		return open;
	}

	/**
	 * @param open the open to set
	 */
	public void setOpen(boolean open) {
		this.open = open;
	}
}
