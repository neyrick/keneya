package com.keneya.jboss.security;

import java.security.acl.Group;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import org.jboss.security.auth.spi.DatabaseServerLoginModule;

public class CachedDatabaseServerLoginModule extends DatabaseServerLoginModule {

	private static final Long DEFAULT_CACHE_VALIDITY = 300000L;
	private static final String CACHE_VALIDITY_OPTION = "cacheValidity";
	
	private Map<String, Long> loginTimestamps = new HashMap<String, Long>();
	private Map<String, String> passwordsCache = new HashMap<String, String>();
	private Map<String, Group[]> rolesCache = new HashMap<String, Group[]>();
	
	private Long cacheValidity;
	
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map<String, ?> sharedState, Map<String, ?> options) {
		super.initialize(subject, callbackHandler, sharedState, options);
		cacheValidity = (Long)options.get(CACHE_VALIDITY_OPTION);
		if (cacheValidity == null) cacheValidity = DEFAULT_CACHE_VALIDITY;
	}

	protected boolean isCached(String username) {
		Long lastLogin = loginTimestamps.get(username);
		long now = new Date().getTime();
		if (lastLogin != null) {
			if (now < (lastLogin + cacheValidity)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	protected Group[] getRoleSets() throws LoginException {
		Group[] roles = null;
		String username = getUsername();
		if (isCached(username)) {
			roles = rolesCache.get(username);
			if (roles != null) return roles;
		}
		roles = super.getRoleSets();
		rolesCache.put(username, roles);
		return roles;
	}

	@Override
	protected String getUsersPassword() throws LoginException {
		String password = null;
		String username = getUsername();
		if (isCached(username)) {
			password = passwordsCache.get(username);
			if (password != null) return password;
		}
		password = super.getUsersPassword();
		passwordsCache.put(username, password);
		loginTimestamps.put(username, new Date().getTime());
		return password;
	}
	
}
