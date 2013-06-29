package controllers;

import java.util.List;

import models.Brand;
import models.Item;
import models.ProductType;

import org.codehaus.jackson.JsonNode;

import play.mvc.BodyParser;
import play.mvc.BodyParser.Json;
import play.mvc.Controller;
import play.mvc.Result;
import securesocial.core.java.SecureSocial.SecuredAction;

public class RestApplication extends Controller {

	/*************************************************************************/
	/** Items - Items - Items - Items - Items - Items - Items - Items - Ite **/
	/*************************************************************************/
	public static Result items() {
		return ok(play.libs.Json.toJson(Item.all()));
	}

	public static Result newitem() {
		JsonNode json = request().body().asJson();
		if (json == null) {
			return badRequest("Expecting Json data");
		} else {
			String name = json.findPath("name").getTextValue();
			if (name == null) {
				return badRequest("Missing parameter [name]");
			} else {
				Item item = new Item();
				item.name = name;
				item.save();
				return redirect(routes.RestApplication.items());
			}
		}
	}

	public static Result deleteItem(Long id) {
		Item.delete(id);
		return redirect(routes.RestApplication.items());
	}

	public static Result itemFromId(Long id) {
		Item item = Item.findById(id);
		if (item != null) {
			return ok(play.libs.Json.toJson(item));
		}
		return notFound("No such item -> id: " + id);
	}

	public static Result itemFromName(String name) {
		Item item = Item.findByName(name);
		if (item != null) {
			return ok(play.libs.Json.toJson(item));
		}
		return notFound("No such item -> name: " + name);
	}

	public static Result itemLikeName(String pieceOfName) {
		List<Item> items = Item.findByPieceOfName(pieceOfName);
		if (items != null) {
			return ok(play.libs.Json.toJson(items));
		}
		return notFound("No such item -> name: " + pieceOfName);
	}

	/*************************************************************************/
	/** Brands - Brands - Brands - Brands - Brands - Brands - Brands - Bran **/
	/*************************************************************************/
	public static Result brands() {
		return ok(play.libs.Json.toJson(Brand.all()));
	}

	public static Result newBrand() {
		JsonNode json = request().body().asJson();
		if (json == null) {
			return badRequest("Expecting Json data");
		} else {
			String name = json.findPath("name").getTextValue();
			if (name == null) {
				return badRequest("Missing parameter [name]");
			} else {
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

	public static Result brandIdToItems(Long id) {
		Brand brand = Brand.findById(id);
		if (brand != null) {
			return ok(play.libs.Json.toJson(brand.items));
		}
		return notFound("No such brand -> id: " + id);
	}

	public static Result brandNameToItems(String name) {
		Brand brand = Brand.findByName(name);
		if (brand != null) {
			return ok(play.libs.Json.toJson(brand.items));
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

	/*************************************************************************/
	/** Types - Types - Types - Types - Types - Types - Types - Types - Typ **/
	/*************************************************************************/
	public static Result types() {
		List<ProductType> result = ProductType.all();
		if (result == null || result.size() <= 0) {
			return notFound("No product type found.");
		}
		return ok(play.libs.Json.toJson(result));
	}

	/*************************************************************************/
	/** Contrib - Contrib - Contrib - Contrib - Contrib - Contrib - Contrib **/
	/*************************************************************************/
	@SecuredAction
	@BodyParser.Of(Json.class)
	public static Result contribute() {
		JsonNode json = request().body().asJson();
		Long itemId = json.findPath("itemId").asLong();
		Long sizeId = json.findPath("sizeId").asLong();
		int adjustment = json.findPath("adjustment").asInt();
		return ok(json);
	}
}
