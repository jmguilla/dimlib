package controllers;

import models.Brand;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

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
}
