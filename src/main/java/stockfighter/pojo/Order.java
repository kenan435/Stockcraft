package stockfighter.pojo;

import lombok.Data;

@Data
public class Order {

	String account;
	String venue;
	String stock;
	int price;
	int qty;
	Direction direction;
	OrderTypes orderType;
}
