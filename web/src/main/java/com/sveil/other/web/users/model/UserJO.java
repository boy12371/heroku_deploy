package com.sveil.other.web.users.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sveil.runtime.application.dal.BaseEntity;

public class UserJO extends BaseEntity implements Serializable {
	private static Logger logger = LoggerFactory.getLogger(UserJO.class);

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;

	private String username;

	private String passwd;

	// {"id":2,"username":"bedroom","passwd":"123456"}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
}
