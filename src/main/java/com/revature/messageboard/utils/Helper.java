package com.revature.messageboard.utils;

import java.util.ArrayList;

import com.revature.messageboard.models.Users;

public class Helper {

	public static ArrayList<Users> convertToUsersArrayList(ArrayList<?> list) {
		ArrayList<Users> convertedArrayList = new ArrayList<Users>();
		Users curUser;
		for (Object user : list) {
			curUser = (Users) user;
			convertedArrayList.add(curUser);
		}
		return convertedArrayList;
	}

//	public static ArrayList<BoardMemberAccess> convertToBoardMemberAccess(ArrayList<?> list) {
//		ArrayList<BoardMemberAccess> convertedArrayList = new ArrayList<BoardMemberAccess>();
//		BoardMemberAccess boardMemberAccess;
//		for (Object user : list) {
//			boardMemberAccess = (BoardMemberAccess) user;
//			convertedArrayList.add(boardMemberAccess);
//		}
//		return convertedArrayList;
//	}
}
