package controllers;

import java.util.List;

import models.Brand;
import models.Item;

import org.codehaus.jackson.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;

public class RestApplication extends Controller {

  /****************************************************************************************/
  /** Shoes - Shoes - Shoes - Shoes - Shoes - Shoes - Shoes - Shoes - Shoes - Shoes - Sh **/
  /****************************************************************************************/
  public static Result shoes() {
    return ok(play.libs.Json.toJson(Item.all()));
  }

  public static Result newShoe() {
    JsonNode json = request().body().asJson();
    if (json == null) {
      return badRequest("Expecting Json data");
    }
    else {
      String name = json.findPath("name").getTextValue();
      if (name == null) {
        return badRequest("Missing parameter [name]");
      }
      else {
        Item shoe = new Item();
        shoe.name = name;
        shoe.save();
        return redirect(routes.RestApplication.shoes());
      }
    }
  }

  public static Result deleteShoe(Long id) {
    Item.delete(id);
    return redirect(routes.RestApplication.shoes());
  }

  public static Result shoeFromId(Long id) {
    Item shoe = Item.findById(id);
    if (shoe != null) {
      return ok(play.libs.Json.toJson(shoe));
    }
    return notFound("No such shoe -> id: " + id);
  }

  public static Result shoeFromName(String name) {
    Item shoe = Item.findByName(name);
    if (shoe != null) {
      return ok(play.libs.Json.toJson(shoe));
    }
    return notFound("No such shoe -> name: " + name);
  }

  public static Result shoeLikeName(String pieceOfName) {
    List<Item> shoes = Item.findByPieceOfName(pieceOfName);
    if (shoes != null) {
      return ok(play.libs.Json.toJson(shoes));
    }
    return notFound("No such shoe -> name: " + pieceOfName);
  }

  /****************************************************************************************/
  /** Brands - Brands - Brands - Brands - Brands - Brands - Brands - Brands - Brands - B **/
  /****************************************************************************************/
  public static Result brands() {
    return ok(play.libs.Json.toJson(Brand.all()));
  }

  public static Result newBrand() {
    JsonNode json = request().body().asJson();
    if (json == null) {
      return badRequest("Expecting Json data");
    }
    else {
      String name = json.findPath("name").getTextValue();
      if (name == null) {
        return badRequest("Missing parameter [name]");
      }
      else {
        Brand brand = new Brand(name);
        brand.save();
        return redirect(routes.RestApplication.brands());
      }
    }
  }

  public static Result deleteBrand(Long id) {
    Brand.delete(id);
    return redirect(routes.RestApplication.brands());
  }

  public static Result brandIdToShoes(Long id) {
    Brand brand = Brand.findById(id);
    if (brand != null) {
      return ok(play.libs.Json.toJson(brand.shoes));
    }
    return notFound("No such brand -> id: " + id);
  }

  public static Result brandNameToShoes(String name) {
    Brand brand = Brand.findByName(name);
    if (brand != null) {
      return ok(play.libs.Json.toJson(brand.shoes));
    }
    return notFound("No such brand -> name: " + name);
  }

  public static Result brandFromId(Long id) {
    Brand brand = Brand.findById(id);
    if (brand != null) {
      return ok(play.libs.Json.toJson(brand));
    }
    return notFound("No such brand -> id: " + id);
  }

  public static Result brandFromName(String name) {
    Brand brand = Brand.findByName(name);
    if (brand != null) {
      return ok(play.libs.Json.toJson(brand));
    }
    return notFound("No such brand -> name: " + name);
  }

  public static Result brandLikeName(String pieceOfName) {
    List<Brand> brands = Brand.findByPieceOfName(pieceOfName);
    if (brands != null) {
      return ok(play.libs.Json.toJson(brands));
    }
    return notFound("No such brand -> name: " + pieceOfName);
  }
}
