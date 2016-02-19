package stockfighter.rest.reponse;

import java.util.Date;

import lombok.Data;

@Data
public class Fills {

	private int price;

	private int qty;

	private Date ts;

	public Fills() {
		// TODO Auto-generated constructor stub
	}

}
