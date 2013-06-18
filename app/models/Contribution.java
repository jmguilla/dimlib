package models;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

public class Contribution extends Model {

	@ManyToOne
	public User user;

	@ManyToMany
	public Size size;

}
