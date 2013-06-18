package models;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

/**
 * This is for example the lenght of a shoe, with all different sizes.
 * For an helmet it is the round of your head with all different sizes (xxs to xxl).
 * For a glove the same.
 * 
 * @author jguillaume
 * 
 */
@Entity
public class Dimension extends Model {

  @Id
  public int id;
  
  @ManyToMany
  public Item item;

  @OneToMany
  public ArrayList<Size> sizes;

  public String description;

}
