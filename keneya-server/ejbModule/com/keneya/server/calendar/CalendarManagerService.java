package com.keneya.server.calendar;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;

import com.keneya.model.calendar.Calendar;

/**
 * Session Bean implementation class UserManager
 */
@Stateless
@LocalBean
@NamedQueries({@NamedQuery(name = CalendarManagerService.QUERY_ALL, query = "select calendar c from calendar"),
		@NamedQuery(name = CalendarManagerService.QUERY_OWNER, query = "select calendar c from calendar where c.owner = ?")})
public class CalendarManagerService {

		public static final String QUERY_ALL = "calendar_all";
		public static final String QUERY_OWNER = "calendar_owner";
	
	   @Inject
	   private Event<Calendar> event;

	   @PersistenceContext
	   private EntityManager entityManager;

	   public Long addCalendar(Calendar cal) {
	       entityManager.persist(cal);
		   entityManager.flush();
	       event.fire(cal);
		   entityManager.refresh(cal);
		   return cal.getId();
	   }


	   public List<Calendar> findAllCalendars() {
		   return entityManager.createNamedQuery(QUERY_ALL, Calendar.class).getResultList();
	   }
	   
	   public Calendar findByPrimaryKey(Long key) {
		   return entityManager.find(Calendar.class, key);
	   }

	   public List<Calendar> findByOwner(Long key) {
		   return entityManager.createNamedQuery(QUERY_OWNER, Calendar.class).getResultList();
	   }
}
