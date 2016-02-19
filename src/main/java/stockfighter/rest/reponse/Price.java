package stockfighter.rest.reponse;

import lombok.Data;

@Data
public class Price {

	private int price;
	private int qty;
	private Boolean isBuy;
}