package stockfighter.rest.reponse;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LevelResponse implements IEntityClass {

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
