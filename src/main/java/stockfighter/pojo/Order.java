package stockfighter.pojo;

public class Order {
	
	String account;
	String venue;
	String stock;
	int qty;
	String direction;
	OrderTypes orderType;

	public Order() {
		// TODO Auto-generated constructor stub
	}

	public Order(String account, String venue, String stock, int qty, String direction, OrderTypes orderTypes) {
		this.account = account;
		this.venue = venue;
		this.stock = stock;
		this.qty = qty;
		this.direction = direction;
		this.orderType = orderTypes;

	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
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
	 * @return the stock
	 */
	public String getStock() {
		return stock;
	}

	/**
	 * @param stock
	 *            the stock to set
	 */
	public void setStock(String stock) {
		this.stock = stock;
	}

	/**
	 * @return the qty
	 */
	public int getQty() {
		return qty;
	}

	/**
	 * @param qty
	 *            the qty to set
	 */
	public void setQty(int qty) {
		this.qty = qty;
	}

	/**
	 * @return the direction
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * @param direction
	 *            the direction to set
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * @return the orderType
	 */
	public OrderTypes getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType
	 *            the orderType to set
	 */
	public void setOrderType(OrderTypes orderType) {
		this.orderType = orderType;
	}
}
