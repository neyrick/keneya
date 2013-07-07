package com.keneya.model.generics;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="person")
public class Person {

	@Id
	private Long id;

	private String firstName;

	private String lastName;
	
	private String usualName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsualName() {
		return usualName;
	}

	public void setUsualName(String usualName) {
		this.usualName = usualName;
	}

	public Long getId() {
		return id;
	}		


}
