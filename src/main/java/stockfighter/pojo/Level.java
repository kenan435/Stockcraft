package stockfighter.pojo;

import java.util.List;

import lombok.Data;

@Data
public class Level implements EntityClass {

	String account;
	String instanceId;
	List<String> instructions;
	boolean ok;
	int secondsPerTradingDay;
	List<String> tickers;
	List<String> venues;
	List<String> balances;
}
