package com.keneya.model.security;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.keneya.model.generics.Person;

@Entity
@Table(name="users")
public class User {

	public enum UserStatus { VALID, CLOSED, TRASHED};
	
	@Id
	private Long id;

	private String login;

	private String password;

	@Enumerated(EnumType.ORDINAL)
	private UserStatus status;

	@ManyToOne	
	@JoinColumn(name="personid")
	private Person person;

	public Long getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	
	
}
