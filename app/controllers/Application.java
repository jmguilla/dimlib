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
  public static Result mainLogin() {
    Option<Identity> opt = getUserInCTX();
    if (opt.isDefined() && User.class.isAssignableFrom(opt.get().getClass())) {
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
        param = new Some<Identity>((Identity)user);
      }
      else if (Option.class.isAssignableFrom(user.getClass())) {
        param = (Option<Identity>)user;
      }
    }
    else {
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
    // TODO disable button if user has not loggedin
    return ok(views.html.welcome.render());
  }

  @SecureSocial.SecuredAction
  public static Result partialsContribute() {
    return ok(views.html.contribute.render());
  }

  @SecureSocial.SecuredAction
  public static Result partialsProfile() {
    return ok(views.html.account.main.render(views.html.account.profile.render((User)getUserInCTX().get(),
        request())));
  }

  @SecureSocial.SecuredAction
  public static Result partialsDashboard() {
    return ok(views.html.account.main.render(views.html.account.dashboard.render()));
  }

  @SecureSocial.SecuredAction
  public static Result partialsRequests() {
    return ok(views.html.account.main.render(views.html.account.requests.render()));
  }

  @SecureSocial.SecuredAction
  public static Result partialsContribs() {
    return ok(views.html.account.main.render(views.html.account.contribs.render()));
  }

  @SecureSocial.SecuredAction
  public static Result partialsNewRequest() {
    return ok(views.html.new_request.render());
  }

  public static Result partialsBrandsList() {
    return ok(views.html.brands_list.render());
  }

  public static Result partialsBrandDetails() {
    return ok(views.html.brand_details.render());
  }

  public static Result partialsItemDetails() {
    return ok(views.html.item_details.render());
  }
}
