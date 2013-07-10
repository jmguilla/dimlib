package models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.codehaus.jackson.annotate.JsonIgnore;

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

  public static final String AUTH_USERPASS = "userpass";

  @Id
  @Formats.NonEmpty
  private String email;

  @Column(precision = 4, scale = 1)
  private BigDecimal shoesMeasure;

  @Formats.NonEmpty
  private String firstName, lastName, userId, providerId;

  private String fullName, avatarUrl;

  @JsonIgnore
  private String hasher, password, salt;

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
    return null;
  }

  @Override
  public Option<String> avatarUrl() {
    return new Some<String>(avatarUrl);
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
    return new Some<PasswordInfo>(new PasswordInfo(this.hasher,
        this.password, new Some<String>(this.salt)));
  }

  public static String buildEmailColonProviderId(String email,
      String providerId) {
    return email + ":" + providerId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public BigDecimal getShoesMeasure() {
    return shoesMeasure;
  }

  public void setShoesMeasure(BigDecimal shoesMeasure) {
    // TODO ensure correct value
    if (shoesMeasure != null) {
      BigDecimal remainder = shoesMeasure.remainder(BigDecimal.ONE);
      BigDecimal lower = new BigDecimal("0.25");
      BigDecimal upper = new BigDecimal("0.75");
      BigDecimal toAdd = BigDecimal.ZERO;
      if (remainder.compareTo(upper) >= 0) {
        toAdd = BigDecimal.ONE;
      }
      else if (remainder.compareTo(lower) >= 0 && remainder.compareTo(upper) < 0) {
        toAdd = new BigDecimal("0.5");
      }
      shoesMeasure = shoesMeasure.subtract(remainder).add(toAdd);
    }
    this.shoesMeasure = shoesMeasure;
  }

  public String getHasher() {
    return hasher;
  }

  public void setHasher(String hasher) {
    this.hasher = hasher;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
    updateFullName();
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
    updateFullName();
  }

  private void updateFullName() {
    if (this.firstName != null && this.lastName != null) {
      this.setFullName(this.firstName + " " + this.lastName);
    }
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getProviderId() {
    return providerId;
  }

  public void setProviderId(String providerId) {
    this.providerId = providerId;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }
}
