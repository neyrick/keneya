package com.keneya.model.calendar;

import java.io.Serializable;
import java.util.Date;

public class Occurrence implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date date;
	
	private Event event;

	public Date getDate() {
		return date;
	}

	public Event getEvent() {
		return event;
	}

	public Occurrence(Date date, Event event) {
		super();
		this.date = date;
		this.event = event;
	}
	
}
