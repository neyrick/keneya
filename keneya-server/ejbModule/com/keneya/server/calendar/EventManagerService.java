package com.keneya.server.calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;

import com.keneya.model.calendar.Event;
import com.keneya.model.calendar.Occurrence;

/**
 * Session Bean implementation class EventManagerService
 */
@Stateless
@LocalBean
@NamedQueries({@NamedQuery(name = EventManagerService.QUERY_RANGE_READER, query = "select event e from event where status != 0 and ((frequency = 0 and enddate >= ? and startdate < ?) or (frequency > 0 and maxdate < ?))"),
	@NamedQuery(name = EventManagerService.QUERY_RANGE_CALENDAR, query = "select event e from event join calendar where calendar.id = ? and status != 0 and ((frequency = 0 and enddate >= ? and startdate < ?) or (frequency > 0 and maxdate < ?))")})
public class EventManagerService implements EventManager {

	public static final String QUERY_RANGE_READER = "event_range_user";
	public static final String QUERY_RANGE_CALENDAR = "event_range_calendar";
	
   @PersistenceContext
   private EntityManager entityManager;

	private List<Occurrence> getOccurencesFromEvents(List<Event> events, Date minDate, Date maxDate) {
		List<Occurrence> result = new ArrayList<>();
		Iterator<Event> iter = events.iterator();
		Event event = null;
		while (iter.hasNext()) {
			event = iter.next();
			result.addAll(event.getOccurences(minDate, maxDate));			
		}
		return result;
	}
	
	public List<Occurrence> getOccurencesByCalendar(long calendarId, Date minDate, Date maxDate) {
		// TODO: jointure JPQL
		// TODO: parametres JPQL
		// TODO: fetch overrides
		List<Event> rawEvent = entityManager.createNamedQuery(QUERY_RANGE_CALENDAR, Event.class).getResultList();
		return getOccurencesFromEvents(rawEvent, minDate, maxDate);
	}
	
	public List<Occurrence> getOccurencesByReader(long userId, Date minDate, Date maxDate) {
		List<Event> rawEvent = entityManager.createNamedQuery(QUERY_RANGE_READER, Event.class).getResultList();
		return getOccurencesFromEvents(rawEvent, minDate, maxDate);
	}
	
}
