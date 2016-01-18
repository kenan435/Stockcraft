package stockfighter.pojo;

import java.util.Date;

public class Quote extends EntityClass{

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

	public Quote() {

	}

	public Quote(boolean ok, String symbol, String venue, int bid, int ask, int bidSize, int askSize, int bidDepth,
			int askDepth, int last, int lastSize, Date lastTrade, Date quoteTime) {
		this.ok = ok;
		this.symbol = symbol;
		this.venue = venue;
		this.bid = bid;
		this.ask = ask;
		this.bidSize = bidSize;
		this.askSize = askSize;
		this.bidDepth = bidDepth;
		this.askDepth = askDepth;
		this.last = last;
		this.lastSize = lastSize;
		this.lastTrade = lastTrade;
		this.quoteTime = quoteTime;
	}

	/**
	 * @return the ok
	 */
	public boolean isOk() {
		return ok;
	}

	/**
	 * @param ok
	 *            the ok to set
	 */
	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol
	 *            the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
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
	 * @return the bid
	 */
	public int getBid() {
		return bid;
	}

	/**
	 * @param bid
	 *            the bid to set
	 */
	public void setBid(int bid) {
		this.bid = bid;
	}

	/**
	 * @return the ask
	 */
	public int getAsk() {
		return ask;
	}

	/**
	 * @param ask
	 *            the ask to set
	 */
	public void setAsk(int ask) {
		this.ask = ask;
	}

	/**
	 * @return the bidSize
	 */
	public int getBidSize() {
		return bidSize;
	}

	/**
	 * @param bidSize
	 *            the bidSize to set
	 */
	public void setBidSize(int bidSize) {
		this.bidSize = bidSize;
	}

	/**
	 * @return the askSize
	 */
	public int getAskSize() {
		return askSize;
	}

	/**
	 * @param askSize
	 *            the askSize to set
	 */
	public void setAskSize(int askSize) {
		this.askSize = askSize;
	}

	/**
	 * @return the bidDepth
	 */
	public int getBidDepth() {
		return bidDepth;
	}

	/**
	 * @param bidDepth
	 *            the bidDepth to set
	 */
	public void setBidDepth(int bidDepth) {
		this.bidDepth = bidDepth;
	}

	/**
	 * @return the askDepth
	 */
	public int getAskDepth() {
		return askDepth;
	}

	/**
	 * @param askDepth
	 *            the askDepth to set
	 */
	public void setAskDepth(int askDepth) {
		this.askDepth = askDepth;
	}

	/**
	 * @return the last
	 */
	public int getLast() {
		return last;
	}

	/**
	 * @param last
	 *            the last to set
	 */
	public void setLast(int last) {
		this.last = last;
	}

	/**
	 * @return the lastSize
	 */
	public int getLastSize() {
		return lastSize;
	}

	/**
	 * @param lastSize
	 *            the lastSize to set
	 */
	public void setLastSize(int lastSize) {
		this.lastSize = lastSize;
	}

	/**
	 * @return the lastTrade
	 */
	public Date getLastTrade() {
		return lastTrade;
	}

	/**
	 * @param lastTrade
	 *            the lastTrade to set
	 */
	public void setLastTrade(Date lastTrade) {
		this.lastTrade = lastTrade;
	}

	/**
	 * @return the quoteTime
	 */
	public Date getQuoteTime() {
		return quoteTime;
	}

	/**
	 * @param quoteTime
	 *            the quoteTime to set
	 */
	public void setQuoteTime(Date quoteTime) {
		this.quoteTime = quoteTime;
	}
}
