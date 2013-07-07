package com.keneya.server.security;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;

import com.keneya.model.security.User;

/**
 * Session Bean implementation class UserManager
 */
@Stateless
@LocalBean
@NamedQuery(name = UserManagerService.QUERY_ALL, query = "select user u from user")
public class UserManagerService {

		public static final String QUERY_ALL = "user_all";
	
	   @Inject
	   private Event<User> event;

	   @PersistenceContext
	   private EntityManager entityManager;

	   @Inject
	   private UserCache userCache;
	   
	   public Long addUser(User user) {
		   userCache.addUser(user);
	       entityManager.persist(user);
		   entityManager.flush();
	       event.fire(user);
		   entityManager.refresh(user);
		   return user.getId();
	   }


	   public List<User> findAllUsers() {
		   return entityManager.createNamedQuery(QUERY_ALL, User.class).getResultList();
	   }
	   
	   public User findByPrimaryKey(Long key) {
		   return entityManager.find(User.class, key);
	   }
}
