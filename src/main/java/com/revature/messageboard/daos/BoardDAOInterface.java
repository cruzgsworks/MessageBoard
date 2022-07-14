package com.revature.messageboard.daos;

import com.revature.messageboard.dtos.ResponseDTO;
import com.revature.messageboard.models.Board;
import com.revature.messageboard.models.BoardMemberAccess;
import com.revature.messageboard.models.Roles;
import com.revature.messageboard.models.Users;

public interface BoardDAOInterface {
	ResponseDTO createBoard(Board board, Users curUser);

	ResponseDTO viewMyBoards(Users curUser);
	
	ResponseDTO viewAllBoards();

	ResponseDTO updateBoard(String board_name, Board newName);

	ResponseDTO deleteBoard(String board_name);

	// boolean checkBoardAdmin(String board_name, Users curUser);

	ResponseDTO addMember(String pathParam, Users newMember);

	Board getBoardByName(String board_name);

	Roles checkBoardAccessLevel(String board_name, Users curUser);

	ResponseDTO listMembers(String board_name);
	
	BoardMemberAccess getMemberAccess(String board_name, String user_name);

	ResponseDTO updateMemberAccess(String board_name, String user_name, BoardMemberAccess newAccess);

	int getBoardMemberID(int board_id, int user_id);

	ResponseDTO deleteMember(String board_name, String user_name, Users curUser);

	Board getBoardByID(int board_id);
}
