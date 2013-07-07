package com.keneya.rs.calendar;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.keneya.model.calendar.Occurrence;

@XmlRootElement(namespace="com.keneya")
public class OccurrenceListResult {

	private List<Occurrence> occurences;

	public List<Occurrence> getOccurences() {
		return occurences;
	}

	public OccurrenceListResult() {
		super();
	}
		
	public OccurrenceListResult(List<Occurrence> occurences) {
		this();
		this.occurences = occurences;
	}
		
}
