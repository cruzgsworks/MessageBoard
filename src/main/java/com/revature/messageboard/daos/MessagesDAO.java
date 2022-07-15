package com.revature.messageboard.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import com.revature.messageboard.dtos.ResponseDTO;
import com.revature.messageboard.models.Board;
import com.revature.messageboard.models.BoardMemberAccess;
import com.revature.messageboard.models.MemberAccess;
import com.revature.messageboard.models.Messages;
import com.revature.messageboard.models.UserMessage;
import com.revature.messageboard.models.Users;
import com.revature.messageboard.utils.ConnectionUtil;

public class MessagesDAO implements MessagesInterface {

	@Override
	public ResponseDTO createMessage(String board_name, Messages requestBody, Users curUser) {

		// at the top of every DAO method, a connection must be opened
		try (Connection conn = ConnectionUtil.getConnection()) {
			String SQL = "INSERT INTO messageboard.messages(message_timestamp, message_content, board_id, user_id)\r\n"
					+ "VALUES(?, ?, ?, ?) RETURNING *";

			// Instantiate a PreparedStatement to fill in the variables (?)
			PreparedStatement ps = conn.prepareStatement(SQL);

			ps.setTimestamp(1, new Timestamp((System.currentTimeMillis())));
			ps.setString(2, requestBody.getMessage_content());
			ps.setInt(3, new BoardDAO().getBoardByName(board_name).getBoard_id());
			ps.setInt(4, curUser.getUser_id());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				ArrayList<Messages> msgList = new ArrayList<Messages>();
				Messages createMessage = new Messages(
						rs.getInt("message_id"),
						rs.getTimestamp("message_timestamp"),
						rs.getString("message_content"),
						rs.getInt("board_id"),
						rs.getInt("user_id"));
				msgList.add(createMessage);
				return new ResponseDTO(201, "Posted new message", true, msgList);
			}

		} catch (SQLException e) {
			System.err.println("Error. createMessage() failed.");
			e.printStackTrace();
		}
		return new ResponseDTO(400, "Could not post message", false, null);
	}

	@Override
	public ResponseDTO viewMessage(String board_name) {
		// at the top of every DAO method, a connection must be opened
		try (Connection conn = ConnectionUtil.getConnection()) {
			String SQL = "SELECT m.message_id, m.message_timestamp, m.message_content, m.board_id, m.user_id, u.user_first_name, u.user_last_name\r\n"
					+ "FROM messageboard.messages m\r\n"
					+ "INNER JOIN messageboard.users u ON m.user_id = u.user_id\r\n"
					+ "WHERE board_id = ?";

			// Instantiate a PreparedStatement to fill in the variables (?)
			PreparedStatement ps = conn.prepareStatement(SQL);

			Board board = new BoardDAO().getBoardByName(board_name);

			ps.setInt(1, board.getBoard_id());

			ResultSet rs = ps.executeQuery();

			ArrayList<UserMessage> msgList = new ArrayList<UserMessage>();
			int size = 0;
			while (rs.next()) {
				size++;
				UserMessage viewMessages = new UserMessage(
						rs.getInt("message_id"),
						rs.getTimestamp("message_timestamp"),
						rs.getString("message_content"),
						rs.getInt("board_id"),
						rs.getInt("user_id"),
						rs.getString("user_first_name"),
						rs.getString("user_last_name"));
				msgList.add(viewMessages);
			}
			if (size > 0) {
				return new ResponseDTO(200, "Viewing all messages", true, msgList);
			}

		} catch (SQLException e) {
			System.err.println("Error. viewMessage() failed.");
			e.printStackTrace();
		}
		return new ResponseDTO(400, "Could not view messages", false, null);
	}

	@Override
	public MemberAccess getMemberAccessByMessageID(int message_id) {
		// at the top of every DAO method, a connection must be opened
		try (Connection conn = ConnectionUtil.getConnection()) {
			String SQL = "SELECT ma.* FROM messageboard.messages m\r\n"
					+ "LEFT JOIN messageboard.board b ON m.board_id = b.board_id \r\n"
					+ "LEFT JOIN messageboard.members m2 ON m.user_id = m2.user_id AND m.board_id = m2.board_id \r\n"
					+ "LEFT JOIN member_access ma ON m2.member_id = ma.member_id\r\n"
					+ "WHERE m.message_id = ?;";

			// Instantiate a PreparedStatement to fill in the variables (?)
			PreparedStatement ps = conn.prepareStatement(SQL);

			ps.setInt(1, message_id);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				MemberAccess memberAccess = new MemberAccess(
						rs.getInt("access_id"),
						rs.getBoolean("is_admin"),
						rs.getBoolean("is_moderator"),
						rs.getBoolean("is_member"),
						rs.getInt("member_id"));
				return memberAccess;
			}

		} catch (SQLException e) {
			System.err.println("Error. viewMessage() failed.");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResponseDTO searchMessage(String board_name, String searchText) {

		// at the top of every DAO method, a connection must be opened
		try (Connection conn = ConnectionUtil.getConnection()) {
			if (StringUtils.isNoneEmpty(searchText)) {
				String SQL = "SELECT m.message_id, m.message_timestamp, m.message_content, m.board_id, m.user_id, u.user_first_name, u.user_last_name\r\n"
						+ "FROM messageboard.messages m\r\n"
						+ "INNER JOIN messageboard.users u ON m.user_id = u.user_id\r\n"
						+ "WHERE board_id = ? AND lower(message_content) LIKE ?";

				// Instantiate a PreparedStatement to fill in the variables (?)
				PreparedStatement ps = conn.prepareStatement(SQL);

				Board board = new BoardDAO().getBoardByName(board_name);

				ps.setInt(1, board.getBoard_id());
				String searchStr = "%" + searchText.trim().replaceAll(" ", "%").toLowerCase() + "%";
				ps.setString(2, searchStr);

				System.out.println(ps.toString());

				ResultSet rs = ps.executeQuery();

				ArrayList<UserMessage> msgList = new ArrayList<UserMessage>();
				int size = 0;
				while (rs.next()) {
					size++;
					UserMessage viewMessages = new UserMessage(
							rs.getInt("message_id"),
							rs.getTimestamp("message_timestamp"),
							rs.getString("message_content"),
							rs.getInt("board_id"),
							rs.getInt("user_id"),
							rs.getString("user_first_name"),
							rs.getString("user_last_name"));
					msgList.add(viewMessages);
				}
				if (size > 0) {
					return new ResponseDTO(200, "Found messages", true, msgList);
				}
			}

		} catch (SQLException e) {
			System.err.println("Error. searchMessage() failed.");
			e.printStackTrace();
		}
		return new ResponseDTO(400, "Could not find messages", false, null);
	}

	@Override
	public ResponseDTO updateMessage(Messages requestBody, Users curUser) {
		// at the top of every DAO method, a connection must be opened
		try (Connection conn = ConnectionUtil.getConnection()) {
			// BoardMemberAccess myBMA = new BoardDAO().getMemberAccess(board_name, curUser.getUser_name());
			MemberAccess messageMemberAccess = this.getMemberAccessByMessageID(requestBody.getMessage_id());

			String SQL = "UPDATE messageboard.messages\r\n"
					+ "SET message_content = ? WHERE message_id = ? RETURNING *";

			// Instantiate a PreparedStatement to fill in the variables (?)
			PreparedStatement ps = conn.prepareStatement(SQL);

			ps.setString(1, requestBody.getMessage_content());
			ps.setInt(2, requestBody.getMessage_id());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Board board = new BoardDAO().getBoardByID(rs.getInt("board_id"));
				BoardMemberAccess myBMA = new BoardDAO().getMemberAccess(board.getBoard_name(), curUser.getUser_name());
				if (myBMA.isIs_admin()) {
					ArrayList<Messages> msgList = new ArrayList<Messages>();
					Messages updatedMessage = new Messages(
							rs.getInt("message_id"),
							rs.getTimestamp("message_timestamp"),
							rs.getString("message_content"),
							rs.getInt("board_id"),
							rs.getInt("user_id"));
					msgList.add(updatedMessage);
					return new ResponseDTO(200, "Updated the message", true, msgList);
				} else if (myBMA.isIs_moderator()) {
					if (!messageMemberAccess.isIs_admin()) {
						ArrayList<Messages> msgList = new ArrayList<Messages>();
						Messages updatedMessage = new Messages(
								rs.getInt("message_id"),
								rs.getTimestamp("message_timestamp"),
								rs.getString("message_content"),
								rs.getInt("board_id"),
								rs.getInt("user_id"));
						msgList.add(updatedMessage);
						return new ResponseDTO(200, "Updated the message", true, msgList);
					}
				} else if (curUser.getUser_id() == rs.getInt("user_id")) {
					ArrayList<Messages> msgList = new ArrayList<Messages>();
					Messages updatedMessage = new Messages(
							rs.getInt("message_id"),
							rs.getTimestamp("message_timestamp"),
							rs.getString("message_content"),
							rs.getInt("board_id"),
							rs.getInt("user_id"));
					msgList.add(updatedMessage);
					return new ResponseDTO(200, "Updated the message", true, msgList);
				}
			}

		} catch (SQLException e) {
			System.err.println("Error. updateMessage() failed.");
			e.printStackTrace();
		}
		return new ResponseDTO(400, "Could not update message", false, null);
	}

	@Override
	public ResponseDTO deleteMessage(int deleteID, Users curUser) {
		// at the top of every DAO method, a connection must be opened
		try (Connection conn = ConnectionUtil.getConnection()) {
			MemberAccess messageMemberAccess = this.getMemberAccessByMessageID(deleteID);

			String SQL = "DELETE\r\n"
					+ "FROM messageboard.messages m\r\n"
					+ "WHERE m.message_id = ? RETURNING *";

			// Instantiate a PreparedStatement to fill in the variables (?)
			PreparedStatement ps = conn.prepareStatement(SQL);

			ps.setInt(1, deleteID);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Board board = new BoardDAO().getBoardByID(rs.getInt("board_id"));
				BoardMemberAccess myBMA = new BoardDAO().getMemberAccess(board.getBoard_name(), curUser.getUser_name());
				if (myBMA.isIs_admin()) {
					ArrayList<Messages> msgList = new ArrayList<Messages>();
					Messages deletedMessage = new Messages(
							rs.getInt("message_id"),
							rs.getTimestamp("message_timestamp"),
							rs.getString("message_content"),
							rs.getInt("board_id"),
							rs.getInt("user_id"));
					msgList.add(deletedMessage);
					return new ResponseDTO(200, "Deleted the message", true, msgList);
				} else if (myBMA.isIs_moderator()) {
					if (!messageMemberAccess.isIs_admin()) {
						ArrayList<Messages> msgList = new ArrayList<Messages>();
						Messages deletedMessage = new Messages(
								rs.getInt("message_id"),
								rs.getTimestamp("message_timestamp"),
								rs.getString("message_content"),
								rs.getInt("board_id"),
								rs.getInt("user_id"));
						msgList.add(deletedMessage);
						return new ResponseDTO(200, "Deleted the message", true, msgList);
					}
				} else if (curUser.getUser_id() == rs.getInt("user_id")) {
					ArrayList<Messages> msgList = new ArrayList<Messages>();
					Messages deletedMessage = new Messages(
							rs.getInt("message_id"),
							rs.getTimestamp("message_timestamp"),
							rs.getString("message_content"),
							rs.getInt("board_id"),
							rs.getInt("user_id"));
					msgList.add(deletedMessage);
					return new ResponseDTO(200, "Deleted the message", true, msgList);
				}
			}

		} catch (SQLException e) {
			System.err.println("Error. deleteMessage() failed.");
			e.printStackTrace();
		}
		return new ResponseDTO(400, "Could not delete message", false, null);
	}

}
