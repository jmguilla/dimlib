package models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Measure extends Model {

  @Id
  public int id;

  @ManyToOne
  @Required
  public User user;

  public BigDecimal value;

  public BigDecimal centimeters() {
    return value;
  }

  public BigDecimal inches() {
    return value.multiply(new BigDecimal(0.393700787));
  }
}
