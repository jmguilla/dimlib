package controllers;

import models.Brand;
import play.mvc.Controller;
import play.mvc.Result;
import securesocial.core.java.SecureSocial;
import jsmessages.JsMessages;

public class Application extends Controller {

	@SecureSocial.SecuredAction
	public static Result brands() {
		return ok(views.html.main.render("brands.title"));
	}

	public static Result brandFromName(String name) {
		Brand result = Brand.findByName(name);
		if (result != null) {
			return ok(views.html.main.render("brand.title"));
		}
		return notFound("No such brand found: " + name);
	}

	public static Result welcome() {
		return ok(views.html.main.render("dimlib.welcome.title"));
	}

	public static Result jsMessages() {
		return ok(JsMessages.generate(null)).as("application/javascript");
	}
	
	/**
	 * Below that, related to partials angularjs
	 */
	public static Result partialsWelcome(){
		return ok(views.html.welcome.render());
	}
}
