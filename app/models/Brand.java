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
	private Long id;

	@Required
	@Formats.NonEmpty
	@Column(unique = true)
	private String name;

	@OneToMany
	@OrderBy("name")
	@JsonIgnore
	private List<Item> items;

	private List<URL> urls;

	private String thumbnail;

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

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public List<URL> getUrls() {
		return urls;
	}

	public void setUrls(List<URL> urls) {
		this.urls = urls;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
}
