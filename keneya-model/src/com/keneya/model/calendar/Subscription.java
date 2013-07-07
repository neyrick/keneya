package com.keneya.model.calendar;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.keneya.model.security.User;

/**
 * Entity implementation class for Entity: Subscription
 *
 */
@Entity
@Table(name="calendar_subscriptions")
public class Subscription implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public enum SubscriptionRole { NONE, READER, EDITOR};

	@Id
	private Long id;

	@Enumerated(EnumType.ORDINAL)
	private SubscriptionRole role;
	
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="calendarid")
	private Calendar calendar;
	
	public Long getId() {
		return id;
	}

	public SubscriptionRole getRole() {
		return role;
	}

	public User getUser() {
		return user;
	}


	public Calendar getCalendar() {
		return calendar;
	}


	public Subscription() {
		super();
	}
   
}
