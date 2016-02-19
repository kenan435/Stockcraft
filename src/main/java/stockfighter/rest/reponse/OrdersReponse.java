package stockfighter.rest.reponse;

import java.util.List;

import lombok.Data;

@Data
public class OrdersReponse implements IEntityClass {

	private boolean ok;
	private String venue;
	private List<OrdersResponse> orders;
	
}
