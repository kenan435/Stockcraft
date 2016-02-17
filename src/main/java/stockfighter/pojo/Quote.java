package stockfighter.pojo;

import java.util.Date;

import lombok.Data;

@Data
public class Quote implements EntityClass {

	private boolean ok;
	private String symbol;
	private String venue;
	private int bid; // best price currently bid for the stock
	private int ask; // best price currently offered for the stock
	private int bidSize; // aggregate size of all orders at the best bid
	private int askSize; // aggregate size of all orders at the best ask
	private int bidDepth; // aggregate size of *all bids*
	private int askDepth; // aggregate size of *all asks*
	private int last; // price of last trade
	private int lastSize; // quantity of last trade
	private Date lastTrade; // timestamp of last trade
	private Date quoteTime; // ts we last updated quote at (server-side)

}
