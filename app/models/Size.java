package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class Size extends Model {
	
	@Id
	public long id;

	@ManyToOne
	public ProductType productType;
	
	public Size(){}
	
	public Size(long id){
		this.id = id;
	}
	
	public Size(long id, ProductType pt){
		this(id);
		this.productType = pt;
	}
}
