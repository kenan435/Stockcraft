package stockfighter.rest.reponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrderbookResponse implements IEntityClass {

	private boolean ok;
	private String venue;
	private String symbol;

	private List<Price> bids = new ArrayList<>();
	private List<Price> asks = new ArrayList<>();
	private Date ts;
}