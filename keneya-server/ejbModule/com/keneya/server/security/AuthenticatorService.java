package com.keneya.server.security;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.keneya.model.security.User;

/**
 * Session Bean implementation class Authenticator
 */
@Stateless
@LocalBean
public class AuthenticatorService {

	@Inject
	private UserCache userCache;
	
	public User authenticate(String login, String password) {
		User user = userCache.getUser(login);
		if ((user == null) || (password == null) || (!password.equals(user.getPassword()))) {
			// TODO: exception de securite
			return null;
		}
		return user;
	}
    /**
     * Default constructor. 
     */
    public AuthenticatorService() {
        // TODO Auto-generated constructor stub
    }

}
