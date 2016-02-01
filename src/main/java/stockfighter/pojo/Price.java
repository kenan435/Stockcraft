package stockfighter.pojo;

import lombok.Data;

@Data
public class Price {

	private int price;
	private int qty;
	private Boolean isBuy;

//	public Price() {
//		// TODO Auto-generated constructor stub
//	}
//	
//	public Price(int price, int qty, Boolean isBuy) {
//		this.price = price;
//		this.qty = qty;
//		this.isBuy = isBuy;
//	}
//	
//	/**
//	 * @return the price
//	 */
//	public int getPrice() {
//		return price;
//	}
//
//	/**
//	 * @param price
//	 *            the price to set
//	 */
//	public void setPrice(int price) {
//		this.price = price;
//	}
//
//	/**
//	 * @return the qty
//	 */
//	public int getQty() {
//		return qty;
//	}
//
//	/**
//	 * @param qty
//	 *            the qty to set
//	 */
//	public void setQty(int qty) {
//		this.qty = qty;
//	}
//
//	/**
//	 * @return the isBuy
//	 */
//	public Boolean isBuy() {
//		return isBuy;
//	}
//
//	/**
//	 * @param isBuy
//	 *            the isBuy to set
//	 */
//	public void setIsBuy(Boolean isBuy) {
//		this.isBuy = isBuy;
//	}
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