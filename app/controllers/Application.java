package controllers;

import models.Brand;
import play.mvc.Controller;
import play.mvc.Result;
import securesocial.core.java.SecureSocial;

public class Application extends Controller {

	@SecureSocial.SecuredAction
	public static Result brands() {
		return ok(views.html.brands.render(Brand.all()));
	}

	public static Result brandFromName(String name) {
		Brand result = Brand.findByName(name);
		if (result != null) {
			return ok(views.html.brand.render(result));
		}
		return notFound("No such brand found: " + name);
	}
	
	public static Result welcome(){
		return ok(views.html.welcome.render());
	}
}
