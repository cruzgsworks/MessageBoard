package com.revature.messageboard.daos;

import com.revature.messageboard.dtos.ResponseDTO;
import com.revature.messageboard.models.MemberAccess;
import com.revature.messageboard.models.Messages;
import com.revature.messageboard.models.Users;

public interface MessagesInterface {

	ResponseDTO createMessage(String board_name, Messages requestBody, Users curUser);

	ResponseDTO viewMessage(String board_name);

	MemberAccess getMemberAccessByMessageID(int message_id);

	ResponseDTO searchMessage(String board_name, String searchText);

	ResponseDTO deleteMessage(int deleteID, Users curUser);

	ResponseDTO updateMessage(Messages requestBody, Users curUser);
	
}
