/**
 * Copyright 2012 Jorge Aliss (jaliss at gmail dot com) - twitter: @jaliss
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package services;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;

import models.LocalToken;
import models.User;
import play.Application;
import play.Logger;
import securesocial.core.Identity;
import securesocial.core.UserId;
import securesocial.core.java.BaseUserService;
import securesocial.core.java.Token;

/**
 * A Sample In Memory user service in Java
 * 
 * Note: This is NOT suitable for a production environment and is provided only
 * as a guide. A real implementation would persist things in a database
 */
public class MyUserService extends BaseUserService {
	private HashMap<String, User> emailToUser = new HashMap<String, User>();

	private HashMap<String, User> idToUserToUser = new HashMap<String, User>();

	private HashMap<String, User> emailAndPidToUserToUser = new HashMap<String, User>();

	public MyUserService(Application application) {
		super(application);
	}

	@Override
	public Identity doSave(Identity user) {
		User alreadyRegistered = User.findByEmail(user.email().get());
		if (alreadyRegistered != null) {
			return updateUser(user, alreadyRegistered);
		}
		alreadyRegistered = User.findByEmailAndProvider(user.email().get(),
				user.id().providerId());
		if (alreadyRegistered != null) {
			return updateUser(user, alreadyRegistered);
		}
		alreadyRegistered = User.findByUserIdAndProviderId(user.id().id(), user
				.id().providerId());
		if (alreadyRegistered != null) {
			return updateUser(user, alreadyRegistered);
		}
		Logger.debug("Persisting user: "
				+ (user.email().isDefined() ? user.email().get() : null));
		User toSave = new User();
		toSave.email = user.email().get();
		recopyUser(user, toSave).save();
		// this sample returns the same user object, but you could return an
		// instance of your own class
		// here as long as it implements the Identity interface. This will allow
		// you to use your own class in the
		// protected actions and event callbacks. The same goes for the
		// doFind(UserId userId) method.
		return user;
	}

	@Override
	public void doSave(Token token) {
		try {
			Logger.debug("Persisting token: " + token.uuid);
			new LocalToken(token).save();
		} catch (ParseException e) {
			Logger.error("User has not been able to save a token", e);
			throw new IllegalStateException(
					"Not been able to process to the registration.");
		}
	}

	@Override
	public Identity doFind(UserId userId) {
		Logger.debug("Retrieving user: " + userId.providerId() + ":"
				+ userId.id());
		User result = null;
		if ((result = idToUserToUser.get(userId.id())) != null) {
			return result;
		}
		result = User.findByUserIdAndProviderId(userId.id(),
				userId.providerId());
		if (result != null) {
			cacheUser(result);
		}
		return result;
	}

	@Override
	public Token doFindToken(String tokenId) {
		Logger.debug("Retrieving token: " + tokenId);
		LocalToken token = LocalToken.find.byId(tokenId);
		return (token != null ? token.toToken() : null);
	}

	@Override
	public Identity doFindByEmailAndProvider(String email, String providerId) {
		Logger.debug("Retrieving user by email & provider: " + providerId + ":"
				+ email);
		User result = null;
		if ((result = emailAndPidToUserToUser.get(User
				.buildEmailColonProviderId(email, providerId))) != null) {
			return result;
		}
		result = User.findByEmailAndProvider(email, providerId);
		if (result != null) {
			cacheUser(result);
		}
		return result;
	}

	@Override
	public void doDeleteToken(String uuid) {
		Logger.debug("Deleting token: " + uuid);
		models.LocalToken token = models.LocalToken.find.byId(uuid);
		if (token != null) {
			token.delete();
		}
	}

	@Override
	public void doDeleteExpiredTokens() {
		Iterator<LocalToken> iterator = LocalToken.find.all().iterator();
		while (iterator.hasNext()) {
			LocalToken tmp = iterator.next();
			if (tmp.toToken().isExpired()) {
				Logger.debug("Deleting expired token: " + tmp.uuid);
				tmp.delete();
			}
		}
	}

	private Identity updateUser(Identity user, User toUpdate) {
		Logger.debug("Updating user: " + toUpdate.email);
		recopyUser(user, toUpdate).update();
		return cacheUser(toUpdate);
	}

	private User recopyUser(Identity user, User toUpdate) {
		toUpdate.password = null;
		if (User.AUTH_USERPASS.equalsIgnoreCase(user.id().providerId())) {
			toUpdate.password = user.passwordInfo().get().password();
			toUpdate.hasher = user.passwordInfo().get().hasher();
			toUpdate.salt = (user.passwordInfo().get().salt().isDefined() ? user
					.passwordInfo().get().salt().get()
					: null);
		}
		toUpdate.firstName = user.firstName();
		toUpdate.fullName = user.fullName();
		toUpdate.lastName = user.lastName();
		toUpdate.providerId = user.id().providerId();
		toUpdate.userId = user.id().id();
		toUpdate.avatarUrl = (user.avatarUrl().isDefined() ? user.avatarUrl()
				.get() : null);
		return toUpdate;
	}

	private User cacheUser(User user) {
		Logger.debug("Caching user: " + user.email);
		idToUserToUser.put(user.userId, user);
		emailAndPidToUserToUser.put(
				User.buildEmailColonProviderId(user.email, user.providerId),
				user);
		emailToUser.put(user.email, user);
		return user;
	}
}
