package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.format.Formats;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Item extends Model {

	@Id
	public Long id;

	@Required
	@Formats.NonEmpty
	public String name;

	@ManyToOne
	@Required
	public Brand brand;

	public String thumbnail;

	@OneToMany
	public List<Contribution> contributions;

	@OneToMany
	public List<String> urls;

	@ManyToMany
	public List<ProductType> productTypes;

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
}
