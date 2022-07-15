package com.revature.messageboard.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import com.revature.messageboard.dtos.ResponseDTO;
import com.revature.messageboard.models.Board;
import com.revature.messageboard.models.BoardMemberAccess;
import com.revature.messageboard.models.Roles;
import com.revature.messageboard.models.Users;
import com.revature.messageboard.utils.ConnectionUtil;

public class BoardDAO implements BoardDAOInterface {

	@Override
	public ResponseDTO createBoard(Board board, Users curUser) {

		// at the top of every DAO method, a connection must be opened
		try (Connection conn = ConnectionUtil.getConnection()) {
			String SQL = "SELECT COUNT(*) ct FROM messageboard.board WHERE board_name = ?";

			// Instantiate a PreparedStatement to fill in the variables (?)
			PreparedStatement ps = conn.prepareStatement(SQL);

			ps.setString(1, board.getBoard_name().toLowerCase());

			ResultSet checkBoard = ps.executeQuery();

			if (checkBoard.next()) {
				if (checkBoard.getInt("ct") < 1) {
					SQL = "WITH Z AS (\r\n" + "  INSERT INTO messageboard.board(board_name)\r\n" + "  VALUES (?)\r\n"
							+ "  RETURNING *\r\n" + "), Y AS (\r\n"
							+ "  INSERT INTO messageboard.members(board_id, user_id)\r\n" + "  SELECT board_id, ?\r\n"
							+ "  FROM Z\r\n" + "  RETURNING member_id\r\n" + "), X AS (\r\n"
							+ "INSERT INTO messageboard.member_access(is_admin, is_moderator, is_member, member_id)\r\n"
							+ "SELECT TRUE, FALSE, FALSE, member_id\r\n" + "FROM Y\r\n" + ")\r\n" + "SELECT * FROM Z";

					ps = conn.prepareStatement(SQL);

					int userID = curUser.getUser_id();

					ps.setString(1, board.getBoard_name());
					ps.setInt(2, userID);

					// System.out.print(ps.toString());

					ResultSet insertedRows = ps.executeQuery();
					ArrayList<Board> boards = new ArrayList<Board>();
					if (insertedRows.next()) {

						Board myboard = new Board(insertedRows.getInt("board_id"),
								insertedRows.getString("board_name"));
						boards.add(myboard);

						return new ResponseDTO(201, "Created a new board and assigned you as the admin.", true, boards);

//						SQL = "SELECT b.board_id, m.user_id, b.board_name, ma.is_admin, ma.is_moderator, ma.is_member\r\n"
//								+ "FROM messageboard.board b\r\n"
//								+ "INNER JOIN messageboard.members m ON\r\n"
//								+ "b.board_id = m.board_id\r\n"
//								+ "INNER JOIN messageboard.member_access ma ON\r\n"
//								+ "m.member_id = ma.member_id\r\n"
//								+ "WHERE b.user_id = ? AND b.board_name = ?";
//
//						ps = conn.prepareStatement(SQL);
//						ps.setInt(1, userID);
//						ps.setString(2, board.getBoard_name());
//
//						ResultSet createdBoard = ps.executeQuery();
//						if (createdBoard.next()) {
//							ArrayList<BoardMemberAccess> myBoards = new ArrayList<BoardMemberAccess>();
//							BoardMemberAccess bma = new BoardMemberAccess(
//									createdBoard.getString("board_name"),
//									createdBoard.getInt("board_id"),
//									createdBoard.getInt("user_id"),
//									createdBoard.getBoolean("is_admin"),
//									createdBoard.getBoolean("is_moderator"),
//									createdBoard.getBoolean("is_member"));
//							myBoards.add(bma);
//							return new DAOResponse(200, "Created a new board and assigned you as the owner/admin.",
//									true,
//									myBoards);
//						}

					}

				} else {
					return new ResponseDTO(400, "Board name already exists", false, null);
				}

			}

		} catch (SQLException e) {
			System.err.println("Error. createBoard() failed.");
			e.printStackTrace();
		}
		return new ResponseDTO(400, "Failed. Board was not added", false, null);
	}

	@Override
	public ResponseDTO viewMyBoards(Users curUser) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			String SQL = "SELECT b.board_id, b.board_name, m.user_id, u.user_name, ma.access_id, ma.is_admin, ma.is_moderator, ma.is_member\r\n"
					+ "FROM messageboard.board b\r\n" + "INNER JOIN messageboard.members m ON\r\n"
					+ "b.board_id = m.board_id\r\n" + "INNER JOIN messageboard.users u ON\r\n"
					+ "m.user_id = u.user_id\r\n" + "INNER JOIN messageboard.member_access ma ON\r\n"
					+ "m.member_id = ma.member_id\r\n" + "WHERE m.user_id = ?";

			// Instantiate a PreparedStatement to fill in the variables (?)
			PreparedStatement ps = conn.prepareStatement(SQL);

			ps.setInt(1, curUser.getUser_id());

			ResultSet rs = ps.executeQuery();

			ArrayList<BoardMemberAccess> myBoards = new ArrayList<BoardMemberAccess>();
			while (rs.next()) {
				BoardMemberAccess board = new BoardMemberAccess(rs.getInt("board_id"), rs.getString("board_name"),
						rs.getInt("user_id"), rs.getString("user_name"), rs.getInt("access_id"),
						rs.getBoolean("is_admin"), rs.getBoolean("is_moderator"), rs.getBoolean("is_member"));
				System.out.println(board.toString());
				myBoards.add(board);
			}
			return new ResponseDTO(200, "Displaying all boards", true, myBoards);
		} catch (SQLException e) {
			System.err.println("viewMyBoards() failed");
			e.printStackTrace();
		}
		return new ResponseDTO(400, "Failed to display your boards", false, null);
	}

	@Override
	public ResponseDTO updateBoard(String board_name, Board newName) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			String SQL = "UPDATE messageboard.board SET board_name = ? WHERE lower(board_name) = ? RETURNING *";

			// Instantiate a PreparedStatement to fill in the variables (?)
			PreparedStatement ps = conn.prepareStatement(SQL);

			ps.setString(1, newName.getBoard_name());
			ps.setString(2, board_name.toLowerCase());

			ResultSet rs = ps.executeQuery();

			ArrayList<Board> updatedBoards = new ArrayList<Board>();
			if (rs.next()) {
				Board updatedBoard = new Board(rs.getInt("board_id"), rs.getString("board_name"));
				updatedBoards.add(updatedBoard);
			}
			return new ResponseDTO(200, "Updated Board", true, updatedBoards);
		} catch (SQLException e) {
			System.err.println("updateBoard() failed");
			e.printStackTrace();
		}
		return new ResponseDTO(400, "Failed to update board", false, null);
	}

	@Override
	public ResponseDTO viewAllBoards() {

		try (Connection conn = ConnectionUtil.getConnection()) {

			String SQL = "SELECT * FROM board";

			// Instantiate a PreparedStatement to fill in the variables (?)
			PreparedStatement ps = conn.prepareStatement(SQL);

			ResultSet rs = ps.executeQuery();

			ArrayList<Board> myBoards = new ArrayList<Board>();
			while (rs.next()) {
				Board board = new Board(rs.getInt("board_id"), rs.getString("board_name"));
//				System.out.println(board.toString());
				myBoards.add(board);
			}
			return new ResponseDTO(200, "Displaying all boards", true, myBoards);
		} catch (SQLException e) {
			System.err.println("viewAllBoards() failed");
			e.printStackTrace();
		}
		return new ResponseDTO(400, "Failed displaying all boards", false, null);
	}

	@Override
	public ResponseDTO deleteBoard(String board_name) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			String SQL = "DELETE FROM messageboard.board WHERE board_name = ? RETURNING *";

			// Instantiate a PreparedStatement to fill in the variables (?)
			PreparedStatement ps = conn.prepareStatement(SQL);

			ps.setString(1, board_name);

			ResultSet rs = ps.executeQuery();

			ArrayList<Board> deletedBoards = new ArrayList<Board>();
			if (rs.next()) {
				Board deletedBoard = new Board(rs.getInt("board_id"), rs.getString("board_name"));
//				System.out.println(board.toString());
				deletedBoards.add(deletedBoard);
			}
			return new ResponseDTO(200, "Deleted Board", true, deletedBoards);
		} catch (SQLException e) {
			System.err.println("deleteBoard() failed");
			e.printStackTrace();
		}
		return new ResponseDTO(400, "Failed to delete board", false, null);
	}

	@Override
	public Roles checkBoardAccessLevel(String board_name, Users curUser) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String SQL = "SELECT ma.is_admin, ma.is_moderator, ma.is_member \r\n" + "FROM messageboard.board b\r\n"
					+ "INNER JOIN messageboard.members m ON\r\n" + "b.board_id = m.board_id\r\n"
					+ "INNER JOIN messageboard.member_access ma ON\r\n" + "m.member_id = ma.member_id\r\n"
					+ "WHERE lower(b.board_name) = ? AND m.user_id = ?";
			// Instantiate a PreparedStatement to fill in the variables (?)
			PreparedStatement ps = conn.prepareStatement(SQL);

			ps.setString(1, board_name.toLowerCase());
			ps.setInt(2, curUser.getUser_id());

			System.out.println(ps.toString());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				if (rs.getBoolean("is_admin")) {
					return Roles.ADMIN;
				} else if (rs.getBoolean("is_moderator")) {
					return Roles.MODERATOR;
				} else if (rs.getBoolean("is_member")) {
					return Roles.MEMBER;
				}
			}

		} catch (SQLException e) {
			System.err.println("checkBoardAdmin() failed");
			e.printStackTrace();
		}
		return Roles.CURRENT_USER;
	}

//	@Override
//	public ResponseDTO addMember(String pathParam, Users newMember) {
//		try (Connection conn = ConnectionUtil.getConnection()) {
//			String SQL = "";
//			// Instantiate a PreparedStatement to fill in the variables (?)
//			PreparedStatement ps = conn.prepareStatement(SQL);
//
//			ps.setString(1, "");
//
//			ResultSet rs = ps.executeQuery();
//			
//			if(rs.next()) {
//				return true;
//			}
//
//		} catch (SQLException e) {
//			System.err.println("checkBoardAdmin() failed");
//			e.printStackTrace();
//		}
//		return false;
//	}

	@Override
	public Board getBoardByName(String board_name) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String SQL = "SELECT * FROM messageboard.board WHERE lower(board_name) = ? ";
			// Instantiate a PreparedStatement to fill in the variables (?)
			PreparedStatement ps = conn.prepareStatement(SQL);

			ps.setString(1, board_name.toLowerCase());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Board board = new Board(rs.getInt("board_id"), rs.getString("board_name"));
				return board;
			}

		} catch (SQLException e) {
			System.err.println("getBoardByName() failed");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Board getBoardByID(int board_id) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String SQL = "SELECT * FROM messageboard.board WHERE board_id = ? ";
			// Instantiate a PreparedStatement to fill in the variables (?)
			PreparedStatement ps = conn.prepareStatement(SQL);

			ps.setInt(1, board_id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Board board = new Board(rs.getInt("board_id"), rs.getString("board_name"));
				return board;
			}

		} catch (SQLException e) {
			System.err.println("getBoardByName() failed");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getBoardMemberID(int board_id, int user_id) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String SQL = "SELECT member_id FROM messageboard.members m \r\n" + "WHERE m.board_id = ? AND m.user_id = ?";
			// Instantiate a PreparedStatement to fill in the variables (?)
			PreparedStatement ps = conn.prepareStatement(SQL);

			ps.setInt(1, board_id);
			ps.setInt(2, user_id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("member_id");
			}

		} catch (SQLException e) {
			System.err.println("getBoardByName() failed");
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public ResponseDTO addMember(String pathParam, Users newMember) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			Board board = this.getBoardByName(pathParam);
			Users user = new UsersDAO().getUserByUserName(newMember.getUser_name());

			if (user != null) {

				int checkMember = this.getBoardMemberID(board.getBoard_id(), user.getUser_id());

				if (checkMember < 1) {
					String SQL = "WITH Y AS (\r\n" + "  INSERT INTO messageboard.members(board_id, user_id)\r\n"
							+ "  VALUES (?, ?)\r\n" + "  RETURNING *\r\n" + "), X AS (\r\n"
							+ "INSERT INTO messageboard.member_access(is_admin, is_moderator, is_member, member_id)\r\n"
							+ "  SELECT FALSE, FALSE, TRUE, member_id\r\n" + "  FROM Y\r\n" + "  RETURNING *"
							+ ") SELECT * FROM X";
					// Instantiate a PreparedStatement to fill in the variables (?)
					PreparedStatement ps = conn.prepareStatement(SQL);

					ps.setInt(1, board.getBoard_id());
					ps.setInt(2, user.getUser_id());

					ResultSet rs = ps.executeQuery();

					ArrayList<BoardMemberAccess> bmaAL = new ArrayList<BoardMemberAccess>();
					if (rs.next()) {
						BoardMemberAccess bma = new BoardMemberAccess(board.getBoard_id(), board.getBoard_name(),
								user.getUser_id(), user.getUser_name(), rs.getInt("access_id"),
								rs.getBoolean("is_admin"), rs.getBoolean("is_moderator"), rs.getBoolean("is_member"));
						bmaAL.add(bma);
						return new ResponseDTO(200, "Added member successfully", true, bmaAL);
					}
				}
			}

		} catch (SQLException e) {
			System.err.println("addMember() failed");
			e.printStackTrace();
		}
		return new ResponseDTO(400, "Add member failed", false, null);
	}

	@Override
	public ResponseDTO listMembers(String pathParam) {

		try (Connection conn = ConnectionUtil.getConnection()) {

			String SQL = "SELECT b.board_id, b.board_name, m.user_id, u.user_name, ma.access_id, ma.is_admin, ma.is_moderator, ma.is_member\r\n"
					+ "FROM messageboard.board b\r\n" + "INNER JOIN messageboard.members m ON\r\n"
					+ "b.board_id = m.board_id\r\n" + "INNER JOIN messageboard.users u ON\r\n"
					+ "m.user_id = u.user_id\r\n" + "INNER JOIN messageboard.member_access ma ON\r\n"
					+ "m.member_id = ma.member_id\r\n" + "WHERE lower(b.board_name) = ?";

			// Instantiate a PreparedStatement to fill in the variables (?)
			PreparedStatement ps = conn.prepareStatement(SQL);

			ps.setString(1, pathParam.toLowerCase());

			ResultSet rs = ps.executeQuery();

			ArrayList<BoardMemberAccess> myBMA = new ArrayList<BoardMemberAccess>();
			while (rs.next()) {
				BoardMemberAccess curBMA = new BoardMemberAccess(rs.getInt("board_id"), rs.getString("board_name"),
						rs.getInt("user_id"), rs.getString("user_name"), rs.getInt("access_id"),
						rs.getBoolean("is_admin"), rs.getBoolean("is_moderator"), rs.getBoolean("is_member"));
				myBMA.add(curBMA);
			}
			return new ResponseDTO(200, "Displaying members for " + pathParam + " board", true, myBMA);
		} catch (SQLException e) {
			System.err.println("listMembers() failed");
			e.printStackTrace();
		}
		return new ResponseDTO(400, "Failed to retrieve members of the messageboard", false, null);
	}

	@Override
	public BoardMemberAccess getMemberAccess(String board_name, String user_name) {

		try (Connection conn = ConnectionUtil.getConnection()) {

			String SQL = "SELECT b.board_id, b.board_name, m.user_id, u.user_name, ma.access_id, ma.is_admin, ma.is_moderator, ma.is_member\r\n"
					+ "FROM messageboard.board b\r\n" + "INNER JOIN messageboard.members m ON\r\n"
					+ "b.board_id = m.board_id\r\n" + "INNER JOIN messageboard.users u ON\r\n"
					+ "m.user_id = u.user_id\r\n" + "INNER JOIN messageboard.member_access ma ON\r\n"
					+ "m.member_id = ma.member_id\r\n" + "WHERE lower(b.board_name) = ? AND lower(u.user_name) = ?";

			// Instantiate a PreparedStatement to fill in the variables (?)
			PreparedStatement ps = conn.prepareStatement(SQL);
			ps.setString(1, board_name.toLowerCase());
			ps.setString(2, user_name.toLowerCase());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				BoardMemberAccess curBMA = new BoardMemberAccess(rs.getInt("board_id"), rs.getString("board_name"),
						rs.getInt("user_id"), rs.getString("user_name"), rs.getInt("access_id"),
						rs.getBoolean("is_admin"), rs.getBoolean("is_moderator"), rs.getBoolean("is_member"));
				return curBMA;
			}

		} catch (SQLException e) {
			System.err.println("getMemberAccess() failed");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResponseDTO updateMemberAccess(String board_name, String user_name, BoardMemberAccess newAccess) {
		BoardMemberAccess myBMA = this.getMemberAccess(board_name, user_name);

		if (myBMA != null) {
			try (Connection conn = ConnectionUtil.getConnection()) {
				String SQL;
				if (newAccess.isIs_admin()) {
					SQL = "UPDATE messageboard.member_access SET is_admin = ?, is_moderator = ?, is_member = ? WHERE access_id = ? RETURNING member_id";
				} else if (newAccess.isIs_moderator()) {
					SQL = "UPDATE messageboard.member_access SET is_moderator = ?, is_admin = ?, is_member = ? WHERE access_id = ? RETURNING member_id";
				} else {
					SQL = "UPDATE messageboard.member_access SET is_member = ?, is_moderator = ?, is_admin = ? WHERE access_id = ? RETURNING member_id";
				}

				if (StringUtils.isNotEmpty(SQL)) {
					PreparedStatement ps = conn.prepareStatement(SQL);
					ps.setBoolean(1, true);
					ps.setBoolean(2, false);
					ps.setBoolean(3, false);
					ps.setInt(4, myBMA.getAccess_id());

					ResultSet rs = ps.executeQuery();

					if (rs.next()) {
						ArrayList<BoardMemberAccess> bmaList = new ArrayList<BoardMemberAccess>();
						myBMA = this.getMemberAccess(board_name, user_name);
						bmaList.add(myBMA);
						return new ResponseDTO(200, "Updated member access", true, bmaList);
					}

				}
			} catch (SQLException e) {
				System.err.println("updateMemberAccess() failed");
				e.printStackTrace();
			}
		}

		return new ResponseDTO(400, "Failed to update member access", false, null);
	}

	@Override
	public ResponseDTO deleteMember(String board_name, String user_name, Users curUser) {

		try (Connection conn = ConnectionUtil.getConnection()) {
			BoardMemberAccess curBMA = this.getMemberAccess(board_name, curUser.getUser_name());
			BoardMemberAccess delBMA = this.getMemberAccess(board_name, user_name);

			String SQL = "DELETE FROM messageboard.members WHERE member_id = ? RETURNING *";

			PreparedStatement ps = conn.prepareStatement(SQL);

			ps.setInt(1, this.getBoardMemberID(delBMA.getBoard_id(), delBMA.getUser_id()));

			System.out.println(ps);

			if (curUser.isIs_superadmin() | curBMA.isIs_admin()) {
				ArrayList<BoardMemberAccess> delBMAList = new ArrayList<BoardMemberAccess>();
				delBMAList.add(delBMA);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					return new ResponseDTO(200, "Deleted member", true, delBMAList);
				}
			}

			if (curBMA.isIs_moderator() & !delBMA.isIs_admin()) {
				ArrayList<BoardMemberAccess> delBMAList = new ArrayList<BoardMemberAccess>();
				delBMAList.add(delBMA);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					return new ResponseDTO(200, "Deleted member", true, delBMAList);
				}
			}

		} catch (SQLException e) {
			System.err.println("deleteMember() failed");
			e.printStackTrace();
		}

		return new ResponseDTO(200, "Failed to delete member", false, null);
	}

}
