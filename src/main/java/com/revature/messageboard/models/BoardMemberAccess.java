package com.revature.messageboard.models;

public class BoardMemberAccess {
	private int board_id;
	private String board_name;
	private int user_id;
	private String user_name;
	private int access_id;
	private boolean is_admin;
	private boolean is_moderator;
	private boolean is_member;

	public String getBoard_name() {
		return board_name;
	}

	public void setBoard_name(String board_name) {
		this.board_name = board_name;
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

	public boolean isIs_admin() {
		return is_admin;
	}

	public void setIs_admin(boolean is_admin) {
		this.is_admin = is_admin;
	}

	public boolean isIs_moderator() {
		return is_moderator;
	}

	public void setIs_moderator(boolean is_moderator) {
		this.is_moderator = is_moderator;
	}

	public boolean isIs_member() {
		return is_member;
	}

	public void setIs_member(boolean is_member) {
		this.is_member = is_member;
	}

	public int getAccess_id() {
		return access_id;
	}

	public void setAccess_id(int access_id) {
		this.access_id = access_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public BoardMemberAccess(int board_id, String board_name, int user_id, String user_name, int access_id,
			boolean is_admin, boolean is_moderator, boolean is_member) {
		super();
		this.board_id = board_id;
		this.board_name = board_name;
		this.user_id = user_id;
		this.user_name = user_name;
		this.access_id = access_id;
		this.is_admin = is_admin;
		this.is_moderator = is_moderator;
		this.is_member = is_member;
	}

	@Override
	public String toString() {
		return "BoardMemberAccess [board_id=" + board_id + ", board_name=" + board_name + ", user_id=" + user_id
				+ ", user_name=" + user_name + ", access_id=" + access_id + ", is_admin=" + is_admin + ", is_moderator="
				+ is_moderator + ", is_member=" + is_member + "]";
	}

}
