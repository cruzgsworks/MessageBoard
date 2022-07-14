package com.revature.messageboard.daos;

import com.revature.messageboard.dtos.ResponseDTO;
import com.revature.messageboard.models.Users;

public interface UsersDAOInterface {
	ResponseDTO registerNewUser(Users user);

	ResponseDTO createSuperAdmin(Users user);

	Users getUserByUserName(String user_name);
	
	Users getUserByUserID(int user_id);

	ResponseDTO updateUser(String user_name, Users user);
	
	ResponseDTO deleteUser(String user_name);

	Users getUserByAuthToken(String user_auth_token);

	ResponseDTO superUpdateUser(Users user);
}
