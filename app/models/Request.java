package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Request extends Model {

  @Required
  @ManyToOne
  private User user;

  @Required
  private Item item;

  public static Finder<Long, Request> find = new Finder<Long, Request>(
      Long.class, Request.class);

  public static List<Request> fromUserEmail(String userEmail) {
    return find.where().eq("user_email", userEmail).findList();
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }
}
