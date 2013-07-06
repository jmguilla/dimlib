package helpers;

public abstract class Notification {
	private String type, msg;

	private Object result;

	public Notification(String type, String msg, Object object) {
		this.type = type;
		this.msg = msg;
		this.result = object;
	}

	public Notification(String type, String msg) {
		this(type, msg, null);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public static class Error extends Notification {

		public Error(String msg) {
			this(msg, null);
		}

		public Error(String msg, Object object) {
			super("error", msg, object);
		}
	}

	public static class Success extends Notification {

		public Success(String msg) {
			this(msg, null);
		}

		public Success(String msg, Object object) {
			super("success", msg, object);
		}
	}
}
