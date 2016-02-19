package stockfighter.rest.reponse;

import lombok.Data;

@Data
public class VenueResponse implements IEntityClass {

	boolean ok;
	String venue;
}
