package stockfighter.pojo;

import lombok.Data;

@Data
public class Heartbeat implements EntityClass {

	private Boolean ok;
	private String error;
}
