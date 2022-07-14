package com.revature.messageboard.models;

public class Board {
	private int board_id;
	private String board_name;
	
	public Board(int board_id, String board_name) {
		super();
		this.board_id = board_id;
		this.board_name = board_name;
	}

	public int getBoard_id() {
		return board_id;
	}

	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}

	public String getBoard_name() {
		return board_name;
	}

	public void setBoard_name(String board_name) {
		this.board_name = board_name;
	}

	@Override
	public String toString() {
		return "Board [board_id=" + board_id + ", board_name=" + board_name + ", getBoard_id()=" + getBoard_id()
				+ ", getBoard_name()=" + getBoard_name() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
}
