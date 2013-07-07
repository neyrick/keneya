package com.keneya.model.calendar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.keneya.model.calendar.Subscription.SubscriptionRole;
import com.keneya.model.security.User;

/**
 * Entity implementation class for Entity: Calendar
 *
 */
@Entity
@Table(name="calendar")
public class Calendar implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;

	private String name;

	@ManyToOne
	@JoinColumn(name="ownerid")
	private User owner;

	@OneToMany(mappedBy="calendar")
	private List<Subscription> subscriptions;
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}


	public Long getId() {
		return id;
	}


	public User getOwner() {
		return owner;
	}


	private List<User> getSubscribersWithRole(SubscriptionRole role) {
		List<User> result = new ArrayList<>();
		Iterator<Subscription> iter = getSubscriptions().iterator();
		Subscription currentSub;
		while (iter.hasNext()) {
			currentSub = iter.next();
			if (SubscriptionRole.EDITOR == currentSub.getRole()) result.add(currentSub.getUser());
		}
		return result;
	}
	
	public List<User> getEditors() {
		return getSubscribersWithRole(SubscriptionRole.EDITOR);
	}
	
	public List<User> getReaders() {
		return getSubscribersWithRole(SubscriptionRole.READER);
	}
	
	public Calendar() {
		super();
	}
   
}
