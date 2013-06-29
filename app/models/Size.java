package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.codehaus.jackson.annotate.JsonIgnore;

import play.db.ebean.Model;

@Entity
public class Size extends Model {

  @Id
  public long id;

  @JsonIgnore
  @ManyToOne
  public ProductType productType;
  
  public int size;

  public Size() {
  }

  public Size(long id) {
    this.id = id;
  }

  public Size(long id, ProductType pt, int size) {
    this(id);
    this.productType = pt;
    this.size = size;
  }
}
