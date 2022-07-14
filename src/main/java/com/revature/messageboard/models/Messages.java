package com.revature.messageboard.models;

import java.sql.Timestamp;

public class Messages {
	private int message_id;
	private Timestamp message_timestamp;
	private String message_content;
	private int board_id;
	private int user_id;

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

	public Messages(Timestamp message_timestamp, String message_content, int board_id, int user_id) {
		super();
		this.message_timestamp = message_timestamp;
		this.message_content = message_content;
		this.board_id = board_id;
		this.user_id = user_id;
	}

	public Messages(int message_id, Timestamp message_timestamp, String message_content, int board_id, int user_id) {
		super();
		this.message_id = message_id;
		this.message_timestamp = message_timestamp;
		this.message_content = message_content;
		this.board_id = board_id;
		this.user_id = user_id;
	}

}
