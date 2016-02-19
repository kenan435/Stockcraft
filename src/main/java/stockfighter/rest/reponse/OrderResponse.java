package stockfighter.rest.reponse;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrderResponse implements IEntityClass {

	boolean ok;
	String symbol;
	String venue;
	String direction;
	int originalQty;
	int qty; // this is the quantity *left outstanding*
	int price; // the price on the order -- may not match that of fills!
	String orderType; // "limit",
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
}
