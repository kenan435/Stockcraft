package stockfighter.pojo;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Cancel extends EntityClass{

	private String venue;
	private boolean ok;
	private String symbol;
	private String direction;
	private String originalQty;
	private int qty;
	private int price;
	private String orderType;
	private int id;
	private String account;
	private Date ts;
	private List<Fills> fills;
	
	private int totalFilled;
	private boolean open;

	public Cancel() {
		// TODO Auto-generated constructor stub
	}

}
