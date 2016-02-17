package stockfighter.pojo;

import java.util.List;

import lombok.Data;

@Data
public class OrdersReponse implements EntityClass {

	private boolean ok;
	private String venue;
	private List<Orders> orders;
	
}
