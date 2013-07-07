package controllers;

import jsmessages.JsMessages;
import models.Brand;
import models.User;
import play.api.templates.Html;
import play.mvc.Controller;
import play.mvc.Result;
import scala.Option;
import scala.Some;
import securesocial.core.Identity;
import securesocial.core.java.SecureSocial;

public class Application extends Controller {

	private static final Html defaultContent = Html
			.apply("<div ng-view></div>");

	@SecureSocial.UserAwareAction
	public static Result main(String any) {
		return ok(views.html.main.render("dimlib.welcome.title",
				getUserInCTX(), defaultContent));
	}
	
	@SecureSocial.UserAwareAction
	public static Result mainLogin(){
		Option<Identity> opt = getUserInCTX();
		if(opt.isDefined() && User.class.isAssignableFrom(opt.get().getClass())){
			return redirect("/welcome");
		}
		return ok(views.html.main.render("dimlib.login.title", scala.Option.<Identity> apply(null),
				views.html.login.render(null, null, null)));
	}

	@SecureSocial.UserAwareAction
	public static Result brands() {
		return ok(views.html.main.render("dimlib.brands.title",
				scala.Option.<Identity> apply(null), defaultContent));
	}

	@SecureSocial.UserAwareAction
	public static Result brandFromName(String name) {
		Brand result = Brand.findByName(name);
		if (result != null) {
			return ok(views.html.main.render("dimlib.brand.title",
					scala.Option.<Identity> apply(null), defaultContent));
		}
		return notFound("No such brand found: " + name);
	}

	@SecureSocial.UserAwareAction
	public static Result welcome() {
		return ok(views.html.main.render("dimlib.welcome.title",
				getUserInCTX(), defaultContent));
	}

	@SecureSocial.SecuredAction
	public static Result submit() {
		return ok();
	}

	static Option<Identity> getUserInCTX() {
		Object user = ctx().args.get(SecureSocial.USER_KEY);
		Option<Identity> param = null;
		if (user != null) {
			if (Identity.class.isAssignableFrom(user.getClass())) {
				param = new Some<Identity>((Identity) user);
			} else if (Option.class.isAssignableFrom(user.getClass())) {
				param = (Option<Identity>) user;
			}
		} else {
			param = scala.Option.<Identity> apply(null);
		}
		return param;
	}

	@SecureSocial.SecuredAction
	public static Result contribute() {
		return ok(views.html.main.render("dimlib.contribute.title",
				getUserInCTX(), defaultContent));
	}

	public static Result jsMessages() {
		return ok(JsMessages.generate(null)).as("application/javascript");
	}

	@SecureSocial.SecuredAction
	public static Result newRequest() {
		return ok(views.html.main.render("dimlib.request.title",
				getUserInCTX(), defaultContent));
	}

	/**
	 * Below that, related to partials angularjs
	 */
	@SecureSocial.UserAwareAction
	public static Result partialsWelcome() {
		return ok(views.html.welcome.render());
	}

	@SecureSocial.SecuredAction
	public static Result partialsContribute() {
		return ok(views.html.contribute.render());
	}

	@SecureSocial.SecuredAction
	public static Result partialsProfile() {
		return ok(views.html.main_account.render(views.html.profile.render()));
	}

	@SecureSocial.SecuredAction
	public static Result partialsDashboard() {
		return ok(views.html.main_account.render(views.html.dashboard.render()));
	}

	@SecureSocial.SecuredAction
	public static Result partialsRequests() {
		return ok(views.html.main_account.render(views.html.requests.render()));
	}

	@SecureSocial.SecuredAction
	public static Result partialsContribs() {
		return ok(views.html.main_account.render(views.html.contribs.render()));
	}

	@SecureSocial.SecuredAction
	public static Result partialsNewRequest() {
		return ok(views.html.new_request.render());

	}
}
