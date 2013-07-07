package com.keneya.model.calendar;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Calendar.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.keneya.model.security.User;


/**
 * The persistent class for the events database table.
 * 
 */
@Entity
@Table(name="events")
public class Event implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public enum EventFrequency { NONE, YEARLY, MONTHLY, WEEKLY, DAILY};

	public enum EventStatus { ACTIVE, CANCELLED, DELETED};

	@Id
	private Long id;

	private Integer category;

	private String comments;

	@Enumerated(EnumType.ORDINAL)
	private EventFrequency frequency;

	private Date maxdate;

	private String name;

    @ManyToOne
	@JoinColumn(name="owner")
	private User owner;

	private Date startDate;

	private Date endDate;

	@Transient
	private Calendar startCalendar = null;
	
	@Transient
	private Calendar endCalendar = null;
	
	
	@Enumerated(EnumType.ORDINAL)
	private EventStatus status;

	private Timestamp tstamp;

	//bi-directional many-to-one association to Event
    @ManyToOne
	@JoinColumn(name="overrides")
	private Event overrides;

	//bi-directional many-to-one association to Event
	@OneToMany(mappedBy="overrides")
	private Set<Event> overriders;

    public Event() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCategory() {
		return this.category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	
	public Calendar getStartCalendar() {
		if ((startCalendar == null) && (startDate != null)) {
			startCalendar = Calendar.getInstance();
			startCalendar.setTime(startDate);
		}
		return startCalendar;
	}

	public Calendar getEndCalendar() {
		if ((endCalendar == null) && (endDate != null)) {
			endCalendar = Calendar.getInstance();
			endCalendar.setTime(endDate);
		}
		return endCalendar;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getEnddate() {
		return this.endDate;
	}

	public void setEnddate(Date enddate) {
		this.endDate = enddate;
	}

	public EventFrequency getFrequency() {
		return frequency;
	}

	public void setFrequency(EventFrequency frequency) {
		this.frequency = frequency;
	}

	public Date getMaxdate() {
		return this.maxdate;
	}

	public void setMaxdate(Date maxdate) {
		this.maxdate = maxdate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Date getStartdate() {
		return this.startDate;
	}

	public void setStartdate(Date startDate) {
		this.startDate = startDate;
	}

	public EventStatus getStatus() {
		return this.status;
	}

	public void setStatus(EventStatus status) {
		this.status = status;
	}

	public Timestamp getTstamp() {
		return this.tstamp;
	}

	public void setTstamp(Timestamp tstamp) {
		this.tstamp = tstamp;
	}

	public Event getEvent() {
		return this.overrides;
	}

	public void setEvent(Event event) {
		this.overrides = event;
	}
	
	public Set<Event> getOverriders() {
		return this.overriders;
	}

	public void setOverriders(Set<Event> overriders) {
		this.overriders = overriders;
	}
	
	public List<Occurrence> getOccurences(Date minDate, Date maxDate) {
		getStartCalendar();
		getEndCalendar();
		List<Occurrence> result = new ArrayList<>();		
		if (EventFrequency.NONE == frequency) result.add(new Occurrence(startDate, this));
		else {
			Calendar cal = Calendar.getInstance();
			cal.set(HOUR_OF_DAY, startCalendar.get(HOUR_OF_DAY));
			cal.set(MINUTE, startCalendar.get(MINUTE));
			cal.set(SECOND, startCalendar.get(SECOND));
			cal.set(MILLISECOND, startCalendar.get(MILLISECOND));
		}
		return result;
	}
}