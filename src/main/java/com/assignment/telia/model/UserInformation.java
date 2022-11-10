package com.assignment.telia.model;

import lombok.Builder;

import javax.persistence.*;
import java.sql.Date;

@Builder
@Entity
@Table(name = "user_information")
public class UserInformation {

	@Id
	private long personalNumber;

	@Column(name = "fullName")
	private String fullName;

	@Column(name = "dateOfBirth")
	private Date dateOfBirth;

	@Column(name = "emailAddress")
	private String emailAddress;

	public UserInformation(long personalNumber, String fullName, Date dateOfBirth, String emailAddress) {
		this.personalNumber = personalNumber;
		this.fullName = fullName;
		this.dateOfBirth = dateOfBirth;
		this.emailAddress = emailAddress;
	}

	public UserInformation() {

	}

	public long getPersonalNumber() {
		return personalNumber;
	}

	public void setPersonalNumber(long personalNumber) {
		this.personalNumber = personalNumber;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Override
	public String toString() {
		return "UserInformation{" +
				"personalNumber=" + personalNumber +
				", fullName='" + fullName + '\'' +
				", dateOfBirth=" + dateOfBirth +
				", emailAddress='" + emailAddress + '\'' +
				'}';
	}
}
