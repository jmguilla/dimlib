package models;

import java.net.URL;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.codehaus.jackson.annotate.JsonIgnore;

import play.data.format.Formats;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Brand extends Model {

  @Id
  public Long id;

  @Required
  @Formats.NonEmpty
  @Column(unique = true)
  public String name;

  @OneToMany
  @OrderBy("name")
  @JsonIgnore
  public List<Item> items;

  public List<URL> urls;

  public String thumbnail;

  public static Finder<Long, Brand> find = new Finder<Long, Brand>(
      Long.class, Brand.class);

  public Brand() {
  }

  public Brand(String name) {
    this.name = name;
  }

  public static Brand findById(Long id) {
    return find.byId(id);
  }

  public static List<Brand> all() {
    return find.orderBy("name asc").findList();
  }

  public static void create(Brand brand) {
    brand.save();
  }

  public static void delete(Long id) {
    find.ref(id).delete();
  }

  public static Brand findByName(String name) {
    return find.where().ieq("name", name).findUnique();
  }

  public static List<Brand> findByPieceOfName(String pieceOfName) {
    return find.where().ilike("name", "%" + pieceOfName + "%")
        .orderBy("name asc").findList();
  }
}
