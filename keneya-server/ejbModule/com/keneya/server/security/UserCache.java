package com.keneya.server.security;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import com.keneya.model.security.User;

@ApplicationScoped
public class UserCache {

	private Map<String, User> userMap = new TreeMap<String, User>();
	
	@Inject 
	private Instance<UserManagerService> userManager;
	
	@PostConstruct
	private void init() {
		synchronize();
	}
	
	public void synchronize() {
		userMap.clear();
		Iterator<User> iter = userManager.get().findAllUsers().iterator();
		User user;
		while(iter.hasNext()) {
			user = iter.next();
			userMap.put(user.getLogin(), user);
		}
	}
	
	public synchronized void addUser(User user) {
		if (userMap.containsKey(user.getLogin())) {			
			throw new IllegalArgumentException("User " + user.getLogin() + " is already in the cache");
		}
		userMap.put(user.getLogin(), user);
	}
	
	public synchronized void removeUser(User user) {
		userMap.remove(user.getLogin());
	}
	
	public User getUser(String login) {
		return userMap.get(login);
	}
}
