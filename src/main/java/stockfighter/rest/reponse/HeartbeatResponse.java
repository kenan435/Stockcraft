package stockfighter.rest.reponse;

import lombok.Data;

@Data
public class HeartbeatResponse implements IEntityClass {

	private Boolean ok;
	private String error;
}
