package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Contribution extends Model {

	@Required
	@ManyToOne
	public User user;

	@Required
	@ManyToOne
	public Size size;

	@Required
	@ManyToOne
	public Item item;
	
	/**
	 * Something like a scale:
	 *  0: fits perfectly
	 * -5: really too small
	 * +5: really too big
	 */
	@Required
	public int adjustment;

}
