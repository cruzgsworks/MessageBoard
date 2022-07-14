package com.revature.messageboard.models;

public class MemberAccess {
	private int access_id;
	private boolean is_admin;
	private boolean is_moderator;
	private boolean is_member;
	private int member_id;

	public int getAccess_id() {
		return access_id;
	}

	public void setAccess_id(int access_id) {
		this.access_id = access_id;
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

	public boolean isIs_guest() {
		return is_member;
	}

	public void setIs_guest(boolean is_guest) {
		this.is_member = is_guest;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public MemberAccess(int access_id, boolean is_admin, boolean is_moderator, boolean is_member, int member_id) {
		super();
		this.access_id = access_id;
		this.is_admin = is_admin;
		this.is_moderator = is_moderator;
		this.is_member = is_member;
		this.member_id = member_id;
	}

}
