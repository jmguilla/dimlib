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
      this("error", msg, object);
    }

    public Error(String type, String msg, Object result) {
      super(type, msg, result);
    }
  }

  public static class Success extends Notification {

    public Success(String msg) {
      this(msg, null);
    }

    public Success(String msg, Object object) {
      this("success", msg, object);
    }

    public Success(String type, String msg, Object result) {
      super(type, msg, result);
    }
  }
}
