package stockfighter.pojo;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Level implements EntityClass {

	String account;
	String instanceId;
//	Instructions instructions;
	
	String[] tickers;
	String[] venues;
	
	boolean ok;
	int secondsPerTradingDay;
	// List<String> tickers;
	// List<String> venues;
	// List<String> balances;
}

@Data
class Instructions {

	Map<String, String> instructions;

}
