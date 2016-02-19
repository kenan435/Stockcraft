package stockfighter.pojo;

import lombok.Data;
import stockfighter.enums.Direction;
import stockfighter.enums.OrderTypes;

@Data
public class Order {

	String account;
	int price;
	int qty;
	Direction direction;
	OrderTypes orderType;
}
