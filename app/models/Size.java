package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class Size extends Model {

	@Id
	public int id;

	@ManyToOne
	public Dimension dimension;

}
