package com.revature.messageboard.models;

import java.sql.Timestamp;

public class UserMessage {
	private int message_id;
	private Timestamp message_timestamp;
	private String message_content;
	private int board_id;
	private int user_id;
	private String user_first_name;
	private String user_last_name;

	public int getMessage_id() {
		return message_id;
	}

	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}

	public Timestamp getMessage_timestamp() {
		return message_timestamp;
	}

	public void setMessage_timestamp(Timestamp message_timestamp) {
		this.message_timestamp = message_timestamp;
	}

	public String getMessage_content() {
		return message_content;
	}

	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}

	public int getBoard_id() {
		return board_id;
	}

	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
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

	public UserMessage(int message_id, Timestamp message_timestamp, String message_content, int board_id, int user_id,
			String user_first_name, String user_last_name) {
		super();
		this.message_id = message_id;
		this.message_timestamp = message_timestamp;
		this.message_content = message_content;
		this.board_id = board_id;
		this.user_id = user_id;
		this.user_first_name = user_first_name;
		this.user_last_name = user_last_name;
	}
}
