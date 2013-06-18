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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import models.User;
import play.Application;
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
	private HashMap<String, Token> tokens = new HashMap<String, Token>();

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
		User toSave = new User();
		toSave.email = user.email().get();
		toSave.userId = user.id().id();
		toSave.providerId = user.id().providerId();
		toSave.save();
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
		tokens.put(token.uuid, token);
	}

	@Override
	public Identity doFind(UserId userId) {
		User result = null;
		if ((result = idToUserToUser.get(userId.id())) != null) {
			return result;
		}
		result = User.findByUserIdAndProviderId(userId.id(),
				userId.providerId());
		return cacheUser(result);
	}

	@Override
	public Token doFindToken(String tokenId) {
		return tokens.get(tokenId);
	}

	@Override
	public Identity doFindByEmailAndProvider(String email, String providerId) {
		User result = null;
		if ((result = emailAndPidToUserToUser.get(User
				.buildEmailColonProviderId(email, providerId))) != null) {
			return result;
		}
		result = User.findByEmailAndProvider(email, providerId);
		return cacheUser(result);
	}

	@Override
	public void doDeleteToken(String uuid) {
		tokens.remove(uuid);
	}

	@Override
	public void doDeleteExpiredTokens() {
		Iterator<Map.Entry<String, Token>> iterator = tokens.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Token> entry = iterator.next();
			if (entry.getValue().isExpired()) {
				iterator.remove();
			}
		}
	}

	private Identity updateUser(Identity user, User toUpdate) {
		toUpdate.firstName = user.firstName();
		toUpdate.fullName = user.fullName();
		toUpdate.lastName = user.lastName();
		toUpdate.providerId = user.id().providerId();
		toUpdate.userId = user.id().id();
		toUpdate.update();
		return cacheUser(toUpdate);
	}

	private User cacheUser(User user) {
		idToUserToUser.put(user.userId, user);
		emailAndPidToUserToUser.put(
				User.buildEmailColonProviderId(user.email, user.providerId),
				user);
		emailToUser.put(user.email, user);
		return user;
	}
}
