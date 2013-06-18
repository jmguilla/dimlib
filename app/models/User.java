package models;

import java.util.Hashtable;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.format.Formats;
import play.db.ebean.Model;
import scala.Option;
import scala.Some;
import securesocial.core.AuthenticationMethod;
import securesocial.core.Identity;
import securesocial.core.OAuth1Info;
import securesocial.core.OAuth2Info;
import securesocial.core.PasswordInfo;
import securesocial.core.UserId;

@Entity
public class User extends Model implements Identity {

	@Id
	@Formats.NonEmpty
	public String email;

	public Hashtable<ProductType, Measure> measures;

	public String firstName, lastName, fullName, userId, providerId;

	public static Finder<String, User> find = new Finder<String, User>(
			String.class, User.class);

	public static void create(User user) {
		user.save();
	}

	public static User findByEmail(String email) {
		return find.where().ieq("email", email).findUnique();
	}

	public static User findByUserIdAndProviderId(String uid, String pid) {
		return find.where().ieq("userId", uid).ieq("providerId", pid)
				.findUnique();
	}

	public static User findByEmailAndProvider(String email, String pid) {
		return find.where().ieq("email", email).ieq("providerId", pid)
				.findUnique();
	}

	@Override
	public AuthenticationMethod authMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Option<String> avatarUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Option<String> email() {
		return new Some<String>(email);
	}

	@Override
	public String firstName() {
		return firstName;
	}

	@Override
	public String fullName() {
		return fullName;
	}

	@Override
	public UserId id() {
		return new UserId(userId, providerId);
	}

	@Override
	public String lastName() {
		return lastName;
	}

	@Override
	public Option<OAuth1Info> oAuth1Info() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Option<OAuth2Info> oAuth2Info() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Option<PasswordInfo> passwordInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public static String buildEmailColonProviderId(String email,
			String providerId) {
		return email + ":" + providerId;
	}
}
