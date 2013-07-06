package controllers;

import helpers.Notification;

import java.util.List;

import models.Brand;
import models.Contribution;
import models.Item;
import models.ProductType;
import models.Request;
import models.Size;
import models.User;

import org.codehaus.jackson.JsonNode;

import play.mvc.BodyParser;
import play.mvc.BodyParser.Json;
import play.mvc.Controller;
import play.mvc.Result;
import securesocial.core.Identity;
import securesocial.core.java.SecureSocial;

import com.avaje.ebean.Ebean;

public class RestApplication extends Controller {

	/*************************************************************************/
	/** Items - Items - Items - Items - Items - Items - Items - Items - Ite **/
	/*************************************************************************/
	public static Result items() {
		return ok(play.libs.Json.toJson(Item.all()));
	}

	public static Result newItem() {
		JsonNode json = request().body().asJson();
		if (json == null) {
			return badRequest(play.libs.Json.toJson(new Notification.Error(
					"Missing json content")));
		}
		String name = json.findPath("name").getTextValue();
		if (name == null) {
			return badRequest(play.libs.Json.toJson(new Notification.Error(
					"Missing parameter [name]")));
		}
		String brand = json.findPath("brand").getTextValue();
		if (brand == null) {
			return badRequest(play.libs.Json.toJson(new Notification.Error(
					"Missing parameter [brand]")));
		}
		Brand itemBrand = Brand.findByName(brand);
		boolean brandCreated = false;
		if (itemBrand == null) {
			itemBrand = new Brand(brand);
			Ebean.save(itemBrand);
			brandCreated = true;
		}
		Item item = new Item(name, itemBrand);
		Ebean.save(item);
		itemBrand.getItems().add(item);
		Ebean.saveAssociation(itemBrand, "items");
		if (!brandCreated) {
			return ok(play.libs.Json.toJson(new Notification.Success(
					"Item successfully created!", item)));
		}
		return ok(play.libs.Json.toJson(new Notification.Success(
				"Item & Brand successfully created!", item)));
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
		}
		String name = json.findPath("name").getTextValue();
		if (name == null) {
			return badRequest("Missing parameter [name]");
		}
		Brand brand = new Brand(name);
		brand.save();
		return redirect(routes.RestApplication.brands());
	}

	public static Result deleteBrand(Long id) {
		Brand.delete(id);
		return redirect(routes.RestApplication.brands());
	}

	public static Result brandIdToItems(Long id) {
		Brand brand = Brand.findById(id);
		if (brand != null) {
			return ok(play.libs.Json.toJson(brand.getItems()));
		}
		return notFound("No such brand -> id: " + id);
	}

	public static Result brandNameToItems(String name) {
		Brand brand = Brand.findByName(name);
		if (brand != null) {
			return ok(play.libs.Json.toJson(brand.getItems()));
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
	@SecureSocial.SecuredAction
	@BodyParser.Of(Json.class)
	public static Result contribute() {
		JsonNode json = request().body().asJson();
		Long itemId = json.findPath("itemId").asLong();
		Long sizeId = json.findPath("sizeId").asLong();
		int adjustment = json.findPath("adjustment").asInt();
		scala.Option<Identity> authenticatedUser = Application.getUserInCTX();
		if (!authenticatedUser.isDefined()
				|| !User.class.isAssignableFrom(authenticatedUser.get()
						.getClass())) {
			return unauthorized("{ 'type': 'error', 'msg': 'User must have signed in.' }");
		}
		User user = (User) authenticatedUser.get();
		Contribution newContribution = Contribution.find(itemId, sizeId, user);
		if (newContribution == null) {
			// creating a new one, otherwise, update will be done
			newContribution = new Contribution();
			newContribution.setItem(Item.findById(itemId));
			newContribution.setSize(Size.findById(sizeId));
			newContribution.setUser(user);
			if (newContribution.getItem() == null
					|| newContribution.getSize() == null) {
				return notFound("{ 'type': 'error', 'msg': 'Item, size and contribution are mandatory!!' }");
			}
		}
		newContribution.setAdjustment(adjustment);
		Ebean.save(newContribution);
		return ok(play.libs.Json.toJson(new Notification.Success(
				"Contribution successfully submitted!!")));
	}

	@SecureSocial.SecuredAction
	public static Result contributions() {
		scala.Option<Identity> authenticatedUser = Application.getUserInCTX();
		if (!authenticatedUser.isDefined()
				|| !User.class.isAssignableFrom(authenticatedUser.get()
						.getClass())) {
			return unauthorized("{ 'type': 'error', 'msg': 'User must have signed in.' }");
		}
		return ok(play.libs.Json.toJson(Contribution
				.fromUserEmail(((User) authenticatedUser.get()).email)));
	}

	/*************************************************************************/
	/** User - User - User - User - User - User - User - User - User - User **/
	/*************************************************************************/
	@SecureSocial.SecuredAction
	public static Result loggedInUser() {
		scala.Option<Identity> authenticatedUser = Application.getUserInCTX();
		if (authenticatedUser.isDefined()
				&& User.class.isAssignableFrom(authenticatedUser.get()
						.getClass())) {
			return ok(play.libs.Json.toJson(authenticatedUser.get()));
		}
		return unauthorized("{ 'type': 'error', 'msg': 'User must have signed in.' }");
	}

	/*************************************************************************/
	/** Requests - Requests - Requests - Requests - Requests - Requests - R **/
	/*************************************************************************/
	@SecureSocial.SecuredAction
	public static Result requests() {
		scala.Option<Identity> authenticatedUser = Application.getUserInCTX();
		if (authenticatedUser.isDefined()
				&& User.class.isAssignableFrom(authenticatedUser.get()
						.getClass())) {
			return ok(play.libs.Json.toJson(Request
					.fromUserEmail(((User) authenticatedUser.get()).email)));
		}
		return unauthorized("{ 'type': 'error', 'msg': 'User must have signed in.' }");
	}

	@SecureSocial.SecuredAction
	public static Result newRequests() {
		scala.Option<Identity> authenticatedUser = Application.getUserInCTX();
		if (!authenticatedUser.isDefined()
				|| !User.class.isAssignableFrom(authenticatedUser.get()
						.getClass())) {
			return unauthorized("{ 'type': 'error', 'msg': 'User must have signed in.' }");
		}
		JsonNode json = request().body().asJson();
		Long itemId = json.findPath("itemId").asLong();
		if (itemId == null) {
			return notFound(play.libs.Json.toJson(new Notification.Error(
					"Item is mandatory!!")));
		}
		Item item = Item.findById(itemId);
		if (item == null) {
			return notFound(play.libs.Json.toJson(new Notification.Error(
					"Item with id " + itemId + " not found.")));
		}
		Request newRequest = new Request();
		newRequest.setItem(item);
		newRequest.setUser((User) authenticatedUser.get());
		return ok(play.libs.Json.toJson(new Notification.Success("Your request has been submitted! Ofcourse, you will have help soon ;)!", newRequest)));
	}
}
