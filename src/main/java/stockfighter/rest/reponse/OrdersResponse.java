package stockfighter.rest.reponse;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrdersResponse implements IEntityClass {

	private String symbol;
	private String venue;
	private String direction;
	private int originalQty;
	private int qty;
	private int price;
	String orderType;
	private int id;
	private String account;
	private Date ts;
	List<Fills> fills;
	private int totalFilled;
	private boolean open;
	private boolean ok;

}
