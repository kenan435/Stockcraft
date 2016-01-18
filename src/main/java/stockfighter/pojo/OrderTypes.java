package stockfighter.pojo;

enum OrderTypes {
	LIMIT("limit"), MARKET("market"), FILL_OR_KILL("fill-or-kill"), IMMEDIATE_OR_CANCEL("immediate-or-cancel");

	private final String orderType;

	private OrderTypes(String s) {
		orderType = s;
	}

	public boolean equalsName(String otherName) {
		return (otherName == null) ? false : orderType.equals(otherName);
	}

	public String toString() {
		return this.orderType;
	}
}
