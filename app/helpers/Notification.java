package helpers;

public abstract class Notification {
  private String type, msg;

  public Notification(String type, String msg) {
    this.type = type;
    this.msg = msg;
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

  public static class Error extends Notification {

    public Error(String msg) {
      this("error", msg);
    }

    public Error(String type, String msg) {
      super(type, msg);
    }
  }

  public static class Success extends Notification {

    public Success(String msg) {
      this("success", msg);
    }

    public Success(String type, String msg) {
      super(type, msg);
    }

  }
}
