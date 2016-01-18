package stockfighter.pojo;

public class Heartbeat extends EntityClass {

	private Boolean ok;
	private String error;

	public Heartbeat() {
		// TODO Auto-generated constructor stub
	}

	public Heartbeat(Boolean ok, String error) {
		this.ok = ok;
		this.error = error;
	}

	public Boolean getOk() {
		return ok;
	}

	public void setOk(Boolean ok) {
		this.ok = ok;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
