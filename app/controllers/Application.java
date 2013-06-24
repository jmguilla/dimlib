package controllers;

import jsmessages.JsMessages;
import models.Brand;
import play.api.templates.Html;
import play.mvc.Controller;
import play.mvc.Result;
import scala.Option;
import scala.Some;
import securesocial.core.Identity;
import securesocial.core.java.SecureSocial;

public class Application extends Controller {
	
	private static final Html defaultContent = Html.apply("<div ng-view></div>");

	public static Result brands() {
		return ok(views.html.main.render("dimlib.brands.title", scala.Option.<Identity>apply(null), defaultContent));
	}

	public static Result brandFromName(String name) {
		Brand result = Brand.findByName(name);
		if (result != null) {
			return ok(views.html.main.render("dimlib.brand.title",
					scala.Option.<Identity>apply(null), defaultContent));
		}
		return notFound("No such brand found: " + name);
	}

	@SecureSocial.UserAwareAction
	public static Result welcome() {
		return ok(views.html.main
				.render("dimlib.welcome.title", getUserInCTX(), defaultContent));
	}
	
	@SecureSocial.SecuredAction
	public static Result submit(){
		return ok();
	}

	private static Option<Identity> getUserInCTX() {
		Object user = ctx().args.get(SecureSocial.USER_KEY);
		Option<Identity> param = null;
		if (user != null) {
			if (Identity.class.isAssignableFrom(user.getClass())) {
				param = new Some<Identity>((Identity) user);
			} else if (Option.class.isAssignableFrom(user.getClass())) {
				param = (Option<Identity>) user;
			}
		} else {
			param = scala.Option.<Identity>apply(null);
		}
		return param;
	}

	public static Result jsMessages() {
		return ok(JsMessages.generate(null)).as("application/javascript");
	}

	@SecureSocial.SecuredAction
	public static Result account() {
		return ok();
	}

	/**
	 * Below that, related to partials angularjs
	 */
	public static Result partialsWelcome() {
		return ok(views.html.welcome.render());
	}
}
