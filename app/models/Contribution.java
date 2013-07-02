package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Contribution extends Model {

  @Id
  private long id;

  @Required
  @ManyToOne
  private User user;

  @Required
  @ManyToOne
  private Size size;

  @Required
  @ManyToOne
  private Item item;

  /**
   * Something like a scale: 0: fits perfectly -2: really too small +2: really
   * too big
   */
  @Required
  private int adjustment;

  public static Finder<Long, Contribution> find = new Finder<Long, Contribution>(
      Long.class, Contribution.class);

  public static Contribution find(Long itemId, Long sizeId, User user) {
    return find.where().eq("user_email", user.email).eq("item_id", itemId).eq("size_id", sizeId).findUnique();
  }

  public static List<Contribution> fromUserEmail(String userEmail) {
    return find.where().eq("user_email", userEmail).findList();
  }

  public static List<Contribution> all() {
    return find.all();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Size getSize() {
    return size;
  }

  public void setSize(Size size) {
    this.size = size;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public int getAdjustment() {
    return adjustment;
  }

  public void setAdjustment(int adjustment) {
    this.adjustment = adjustment;
  }
}
