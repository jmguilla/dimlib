package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonIgnore;

import play.db.ebean.Model;

/**
 * This is for example the lenght of a shoe, with all different sizes. For an
 * helmet it is the round of your head with all different sizes (xxs to xxl).
 * For a glove the same.
 * 
 * @author jguillaume
 * 
 */
@Entity
public class ProductType extends Model {

	@Id
	public int id;

  @JsonIgnore
	@ManyToMany
	public List<Item> items;

	@OneToMany
	public List<Size> sizes;

	public String description;

	public static Finder<Long, ProductType> find = new Finder<Long, ProductType>(
			Long.class, ProductType.class);

	public static List<ProductType> all() {
		return find.all();
	}

}
