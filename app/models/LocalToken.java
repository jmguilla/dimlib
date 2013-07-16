package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.joda.time.DateTime;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class LocalToken extends Model {
  @Id
  @Required
  public String uuid;

  public Date creationTime, expirationTime;

  public boolean signUp, expired;

  public String email;

  public static Finder<String, LocalToken> find = new Finder<String, LocalToken>(
      String.class, LocalToken.class);

  public LocalToken(securesocial.core.java.Token token) throws ParseException {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    creationTime = df.parse(token.creationTime
        .toString("yyyy-MM-dd HH:mm:ss"));
    expirationTime = df.parse(token.expirationTime
        .toString("yyyy-MM-dd HH:mm:ss"));
    email = token.email;
    uuid = token.uuid;
    signUp = token.isSignUp;
  }

  public securesocial.core.java.Token toToken() {
    securesocial.core.java.Token token = new securesocial.core.java.Token();
    token.creationTime = new DateTime(creationTime);
    token.expirationTime = new DateTime(expirationTime);
    token.email = email;
    token.uuid = uuid;
    token.isSignUp = signUp;
    return token;
  }
}
