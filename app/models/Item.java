package models;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonIgnore;

import play.data.format.Formats;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Item extends Model {

  @Id
  private Long id;

  @Required
  @Formats.NonEmpty
  private String name;

  @ManyToOne
  @Required
  private Brand brand;

  private String thumbnail;

  @OneToMany
  @JsonIgnore
  private List<Contribution> contributions;

  @ElementCollection(targetClass = String.class)
  private List<String> urls;

  @ManyToMany
  private List<ProductType> productTypes;

  public static Finder<Long, Item> find = new Finder<Long, Item>(Long.class,
      Item.class);

  public Item() {
  }

  public Item(String name, Brand brand) {
    this.name = name;
    this.brand = brand;
  }

  public static List<Item> all() {
    return find.all();
  }

  public static void create(Item shoe) {
    shoe.save();
  }

  public static void delete(Long id) {
    find.ref(id).delete();
  }

  public static Item findById(Long id) {
    return find.byId(id);
  }

  public static Item findByName(String name) {
    return find.where().ieq("name", name).findUnique();
  }

  public static List<Item> findByPieceOfName(String pieceOfName) {
    return find.where().ilike("name", "%" + pieceOfName + "%")
        .orderBy("name asc").findList();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Brand getBrand() {
    return brand;
  }

  public void setBrand(Brand brand) {
    this.brand = brand;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  public List<Contribution> getContributions() {
    return contributions;
  }

  public void setContributions(List<Contribution> contributions) {
    this.contributions = contributions;
  }

  public List<String> getUrls() {
    return urls;
  }

  public void setUrls(List<String> urls) {
    this.urls = urls;
  }

  public List<ProductType> getProductTypes() {
    return productTypes;
  }

  public void setProductTypes(List<ProductType> productTypes) {
    this.productTypes = productTypes;
  }
}
