package com.revature.messageboard.models;

import java.sql.Timestamp;

public class Users {
	private int user_id;
	private String user_name;
	private String user_pass;
	private String user_first_name;
	private String user_last_name;
	private String user_email;
	private String user_auth_token;
	private Timestamp user_auth_expiration;
	private boolean is_superadmin;

	public Users(int user_id, String user_name, String user_pass, String user_first_name, String user_last_name,
			String user_email) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.user_pass = user_pass;
		this.user_first_name = user_first_name;
		this.user_last_name = user_last_name;
		this.user_email = user_email;
		this.user_auth_token = null;
		this.user_auth_expiration = null;
		this.is_superadmin = false;
	}

	public Users(int user_id, String user_name, String user_pass, String user_first_name, String user_last_name,
			String user_email, String user_auth_token, Timestamp user_auth_expiration, boolean is_superadmin) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.user_pass = user_pass;
		this.user_first_name = user_first_name;
		this.user_last_name = user_last_name;
		this.user_email = user_email;
		this.user_auth_token = user_auth_token;
		this.user_auth_expiration = user_auth_expiration;
		this.is_superadmin = is_superadmin;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_pass() {
		return user_pass;
	}

	public void setUser_pass(String user_pass) {
		this.user_pass = user_pass;
	}

	public String getUser_first_name() {
		return user_first_name;
	}

	public void setUser_first_name(String user_first_name) {
		this.user_first_name = user_first_name;
	}

	public String getUser_last_name() {
		return user_last_name;
	}

	public void setUser_last_name(String user_last_name) {
		this.user_last_name = user_last_name;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public Timestamp getUser_auth_expiration() {
		return user_auth_expiration;
	}

	public void setUser_auth_expiration(Timestamp user_auth_expiration) {
		this.user_auth_expiration = user_auth_expiration;
	}

	public String getUser_auth_token() {
		return user_auth_token;
	}

	public void setUser_auth_token(String user_auth_token) {
		this.user_auth_token = user_auth_token;
	}

	public boolean isIs_superadmin() {
		return is_superadmin;
	}

	public void setIs_superadmin(boolean is_superadmin) {
		this.is_superadmin = is_superadmin;
	}

	@Override
	public String toString() {
		return "Users [user_id=" + user_id + ", user_name=" + user_name + ", user_pass=" + user_pass
				+ ", user_first_name=" + user_first_name + ", user_last_name=" + user_last_name + ", user_email="
				+ user_email + ", user_auth_token=" + user_auth_token + ", user_auth_expiration=" + user_auth_expiration
				+ ", is_superadmin=" + is_superadmin + ", getUser_id()=" + getUser_id() + ", getUser_name()="
				+ getUser_name() + ", getUser_pass()=" + getUser_pass() + ", getUser_first_name()="
				+ getUser_first_name() + ", getUser_last_name()=" + getUser_last_name() + ", getUser_email()="
				+ getUser_email() + ", getUser_auth_expiration()=" + getUser_auth_expiration()
				+ ", getUser_auth_token()=" + getUser_auth_token() + ", isIs_superadmin()=" + isIs_superadmin()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
}
