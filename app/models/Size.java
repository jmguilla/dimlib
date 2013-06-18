package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class Size extends Model {

  @ManyToOne
  public Dimension dimension;

}
