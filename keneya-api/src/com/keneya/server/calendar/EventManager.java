package com.keneya.server.calendar;

import java.util.Date;
import java.util.List;

import com.keneya.model.calendar.Occurrence;


public interface EventManager {

	public List<Occurrence> getOccurencesByCalendar(long calendarId, Date minDate, Date maxDate);
	public List<Occurrence> getOccurencesByReader(long userId, Date minDate, Date maxDate);

}
