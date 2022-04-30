package com.ripple.provision.vm.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "USER_DETAILS")
public class User implements Serializable {

	private static final long serialVersionUID = -5832767428535043665L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private int id;

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "DESIGNATION")
	private String designation;

	@Column(name = "UPDATE_DATE", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp updateDate;
	
	@Column(name = "CREATE_DATE", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String name) {
		this.designation = designation;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
	
}