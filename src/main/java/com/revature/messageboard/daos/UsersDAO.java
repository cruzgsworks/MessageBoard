package com.revature.messageboard.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import com.revature.messageboard.dtos.ResponseDTO;
import com.revature.messageboard.models.Users;
import com.revature.messageboard.services.AuthService;
import com.revature.messageboard.utils.ConnectionUtil;

public class UsersDAO implements UsersDAOInterface {

	@Override
	public ResponseDTO createSuperAdmin(Users user) {
		// at the top of every DAO method, a connection must be opened
		try (Connection conn = ConnectionUtil.getConnection()) {
			String SQL;

			SQL = "SELECT * FROM messageboard.users WHERE is_superadmin = ?";

			// Instantiate a PreparedStatement to fill in the variables (?)
			PreparedStatement ps = conn.prepareStatement(SQL);

			ps.setBoolean(1, true);

			ResultSet checkSuper = ps.executeQuery();

			if (!checkSuper.next()) {
				ps.setString(1, user.getUser_name());

				SQL = "SELECT COUNT(*) ct FROM messageboard.users WHERE user_name = ?";

				// Instantiate a PreparedStatement to fill in the variables (?)
				ps = conn.prepareStatement(SQL);

				ps.setString(1, user.getUser_name());

				ResultSet checkUser = ps.executeQuery();

				if (checkUser.next()) {
					if (checkUser.getInt("ct") < 1) {
						SQL = "INSERT INTO messageboard.users"
								+ "(user_name, user_pass, user_first_name, user_last_name, user_email, user_auth_token, user_auth_expiration, is_superadmin) "
								+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?) RETURNING *";

						ps = conn.prepareStatement(SQL);

						// Fill in values using PreparedStatement
						ps.setString(1, user.getUser_name().toLowerCase());
						ps.setString(2, user.getUser_pass());
						ps.setString(3, user.getUser_first_name());
						ps.setString(4, user.getUser_last_name());
						ps.setString(5, user.getUser_email());
						ps.setNull(6, Types.VARCHAR); // Cookie String Null
						ps.setNull(7, Types.TIMESTAMP_WITH_TIMEZONE); // Cookie Expiration Null
						ps.setBoolean(8, user.isIs_superadmin());

						System.out.println(ps.toString());

						ResultSet rs = ps.executeQuery();
						if (rs.next()) {
							Users newUser = new Users(
									rs.getInt("user_id"),
									rs.getString("user_name"),
									rs.getString("user_pass"),
									rs.getString("user_first_name"),
									rs.getString("user_last_name"),
									rs.getString("user_email"),
									rs.getString("user_auth_token"),
									rs.getTimestamp("user_auth_expiration"),
									rs.getBoolean("is_superadmin"));
							ArrayList<Users> insertedUsers = new ArrayList<Users>();
							insertedUsers.add(newUser);

							return new ResponseDTO(201, "Created new superadmin.", true, insertedUsers);
						}
					} else {
						return new ResponseDTO(409, "Username already exists.", false, null);
					}
				} else {
					throw new SQLException("Failed creating new superadmin.");
				}
			} else {
				return new ResponseDTO(400, "A superadmin already exists.", false, null);
			}

		} catch (SQLException e) {
			System.err.println("SQL error in createSuperAdmin().");
			e.printStackTrace();
		}
		return new ResponseDTO(400, "Failed in creating superadmin.", false, null);
	}

	@Override
	public ResponseDTO registerNewUser(Users user) {
		// at the top of every DAO method, a connection must be opened
		try (Connection conn = ConnectionUtil.getConnection()) {
			String SQL = "SELECT COUNT(*) ct FROM messageboard.users WHERE user_name = ?";

			// Instantiate a PreparedStatement to fill in the variables (?)
			PreparedStatement ps = conn.prepareStatement(SQL);

			ps.setString(1, user.getUser_name());

			ResultSet checkUser = ps.executeQuery();

			if (checkUser.next()) {
				if (checkUser.getInt("ct") < 1) {
					SQL = "INSERT INTO messageboard.users"
							+ "(user_name, user_pass, user_first_name, user_last_name, user_email, user_auth_token, user_auth_expiration, is_superadmin) "
							+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?) RETURNING *";

					ps = conn.prepareStatement(SQL);

					// Fill in values using PreparedStatement
					ps.setString(1, user.getUser_name().toLowerCase());
					ps.setString(2, user.getUser_pass());
					ps.setString(3, user.getUser_first_name());
					ps.setString(4, user.getUser_last_name());
					ps.setString(5, user.getUser_email());
					ps.setNull(6, Types.VARCHAR); // Cookie String
					ps.setNull(7, Types.TIMESTAMP_WITH_TIMEZONE); // Cookie Expiration
					ps.setBoolean(8, user.isIs_superadmin());

					ResultSet rs = ps.executeQuery();
					if (rs.next()) {
						Users newUser = new Users(
								rs.getInt("user_id"),
								rs.getString("user_name"),
								rs.getString("user_pass"),
								rs.getString("user_first_name"),
								rs.getString("user_last_name"),
								rs.getString("user_email"),
								rs.getString("user_auth_token"),
								rs.getTimestamp("user_auth_expiration"),
								rs.getBoolean("is_superadmin"));
						ArrayList<Users> insertedUsers = new ArrayList<Users>();
						insertedUsers.add(newUser);

						return new ResponseDTO(201, "Created new user.", true, insertedUsers);
					}
				} else {
					return new ResponseDTO(409, "Username already exists.", false, null);
				}
			} else {
				throw new SQLException("Failed creating new user.");
			}

		} catch (SQLException e) {
			System.err.println("SQL error in registerNewUser().");
			e.printStackTrace();
		}
		return new ResponseDTO(400, "Failed in creating new user.", false, null);
	}

	@Override
	public Users getUserByUserName(String user_name) {
		if (StringUtils.isNotEmpty(user_name)) {
			// at the top of every DAO method, a connection must be opened
			try (Connection conn = ConnectionUtil.getConnection()) {
				String SQL = "SELECT * FROM messageboard.users WHERE user_name = ?";

				// Instantiate a PreparedStatement to fill in the variables (?)
				PreparedStatement ps = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);

				// Fill in values using PreparedStatement
				System.out.println(user_name);
				ps.setString(1, user_name);

				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					Users user = new Users(rs.getInt("user_id"),
							rs.getString("user_name"),
							rs.getString("user_pass"),
							rs.getString("user_first_name"),
							rs.getString("user_last_name"),
							rs.getString("user_email"),
							rs.getString("user_auth_token"),
							rs.getTimestamp("user_auth_expiration"),
							rs.getBoolean("is_superadmin"));
//					ArrayList<Users> myUser = new ArrayList<Users>();
//					myUser.add(user);
					return user;
				}

//				int size = 0;
//				if (rs != null) {
//					rs.last();
//					size = rs.getRow();
//					if (size > 0) {
//						user = new Users(rs.getInt("user_id"), rs.getString("user_name"), rs.getString("user_pass"),
//								rs.getString("user_first_name"), rs.getString("user_last_name"), rs.getString("user_email"),
//								rs.getString("user_auth_token"), rs.getTimestamp("user_auth_expiration"),
//								rs.getBoolean("is_superadmin"));
//					}
//				}
				//
//				if (size > 0) {
//					System.out.println("User found");
//					return user;
//				} else {
//					System.out.println("User not found");
//					return null;
//				}

			} catch (SQLException e) {
				System.err.println("Error in viewUserByUserName().");
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Users getUserByUserID(int user_id) {
		// at the top of every DAO method, a connection must be opened
		try (Connection conn = ConnectionUtil.getConnection()) {
			String SQL = "SELECT * FROM messageboard.users WHERE user_id = ?";

			// Instantiate a PreparedStatement to fill in the variables (?)
			PreparedStatement ps = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);

			// Fill in values using PreparedStatement
			System.out.println(user_id);
			ps.setInt(1, user_id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Users user = new Users(rs.getInt("user_id"),
						rs.getString("user_name"),
						rs.getString("user_pass"),
						rs.getString("user_first_name"),
						rs.getString("user_last_name"),
						rs.getString("user_email"),
						rs.getString("user_auth_token"),
						rs.getTimestamp("user_auth_expiration"),
						rs.getBoolean("is_superadmin"));
//				ArrayList<Users> myUser = new ArrayList<Users>();
//				myUser.add(user);
				return user;
			} else {
				throw new SQLException("User not found.");
			}

//			int size = 0;
//			if (rs != null) {
//				rs.last();
//				size = rs.getRow();
//				if (size > 0) {
//					user = new Users(rs.getInt("user_id"), rs.getString("user_name"), rs.getString("user_pass"),
//							rs.getString("user_first_name"), rs.getString("user_last_name"), rs.getString("user_email"),
//							rs.getString("user_auth_token"), rs.getTimestamp("user_auth_expiration"),
//							rs.getBoolean("is_superadmin"));
//				}
//			}
//
//			if (size > 0) {
//				System.out.println("User found");
//				return user;
//			} else {
//				System.out.println("User not found");
//				return null;
//			}

		} catch (SQLException e) {
			System.err.println("Error in viewUserByUserID().");
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public ResponseDTO updateUser(String user_name, Users user) {
		if (StringUtils.isNotEmpty(user_name)) {
			Users foundUser = this.getUserByUserName(user_name);
			// ArrayList<Users> foundUser =
			// Helper.convertToUsersArrayList(fullUserInfo.getResults());
			try (Connection conn = ConnectionUtil.getConnection()) {
				String SQL = "UPDATE messageboard.users "
						+ "SET user_name = ?, user_pass = ?, user_first_name = ?, user_last_name = ?, user_email = ? "
						+ "WHERE user_id = ? RETURNING *";

				// Instantiate a PreparedStatement to fill in the variables (?)
				PreparedStatement ps = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);

				ps.setString(1, StringUtils.isNotEmpty(user.getUser_name()) ? user.getUser_name()
						: foundUser.getUser_name());
				ps.setString(2,
						StringUtils.isNotEmpty(user.getUser_pass())
								? new AuthService().doEncrypt(user.getUser_pass())
								: foundUser.getUser_pass());
				ps.setString(3, StringUtils.isNotEmpty(user.getUser_first_name()) ? user.getUser_first_name()
						: foundUser.getUser_first_name());
				ps.setString(4, StringUtils.isNotEmpty(user.getUser_last_name()) ? user.getUser_last_name()
						: foundUser.getUser_last_name());
				ps.setString(5, StringUtils.isNotEmpty(user.getUser_email()) ? user.getUser_email()
						: foundUser.getUser_email());
				ps.setInt(6, foundUser.getUser_id());

				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					Users updatedUser = new Users(rs.getInt("user_id"),
							rs.getString("user_name"),
							rs.getString("user_pass"),
							rs.getString("user_first_name"),
							rs.getString("user_last_name"),
							rs.getString("user_email"),
							rs.getString("user_auth_token"),
							rs.getTimestamp("user_auth_expiration"),
							rs.getBoolean("is_superadmin"));
					ArrayList<Users> updatedUsers = new ArrayList<Users>();
					updatedUsers.add(updatedUser);
					return new ResponseDTO(200, "User data updated.", true, updatedUsers);
				} else {
					throw new SQLException("Failed in updating user data.");
				}
			} catch (SQLException e) {
				System.err.println("Error in updateUser().");
				e.printStackTrace();
			}
			// System.err.println("Error. updateUser() failed.");
		}
		return new ResponseDTO(406, "Please specify username.", false, null);
	}

	@Override
	public ResponseDTO superUpdateUser(Users user) {
		if (StringUtils.isNotEmpty(user.getUser_name())) {
			Users foundUser = this.getUserByUserName(user.getUser_name());
			// ArrayList<Users> foundUser =
			// Helper.convertToUsersArrayList(fullUserInfo.getResults());
			try (Connection conn = ConnectionUtil.getConnection()) {
				String SQL = "UPDATE messageboard.users "
						+ "SET user_name = ?, user_pass = ?, user_first_name = ?, user_last_name = ?, user_email = ?, is_superadmin = ? "
						+ "WHERE user_id = ? RETURNING *";

				// Instantiate a PreparedStatement to fill in the variables (?)
				PreparedStatement ps = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);

				ps.setString(1, StringUtils.isNotEmpty(user.getUser_name()) ? user.getUser_name()
						: foundUser.getUser_name());
				ps.setString(2,
						StringUtils.isNotEmpty(user.getUser_pass())
								? new AuthService().doEncrypt(user.getUser_pass())
								: foundUser.getUser_pass());
				ps.setString(3, StringUtils.isNotEmpty(user.getUser_first_name()) ? user.getUser_first_name()
						: foundUser.getUser_first_name());
				ps.setString(4, StringUtils.isNotEmpty(user.getUser_last_name()) ? user.getUser_last_name()
						: foundUser.getUser_last_name());
				ps.setString(5, StringUtils.isNotEmpty(user.getUser_email()) ? user.getUser_email()
						: foundUser.getUser_email());
				ps.setBoolean(6, user.isIs_superadmin());
				ps.setInt(7, foundUser.getUser_id());

				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					Users updatedUser = new Users(rs.getInt("user_id"),
							rs.getString("user_name"),
							rs.getString("user_pass"),
							rs.getString("user_first_name"),
							rs.getString("user_last_name"),
							rs.getString("user_email"),
							rs.getString("user_auth_token"),
							rs.getTimestamp("user_auth_expiration"),
							rs.getBoolean("is_superadmin"));
					ArrayList<Users> updatedUsers = new ArrayList<Users>();
					updatedUsers.add(updatedUser);
					return new ResponseDTO(200, "User data updated.", true, updatedUsers);
				} else {
					throw new SQLException("Failed in updating user data.");
				}
			} catch (SQLException e) {
				System.err.println("Error in updateUser().");
				e.printStackTrace();
			}
			// System.err.println("Error. updateUser() failed.");
		}
		return new ResponseDTO(406, "Please specify username.", false, null);
	}

	@Override
	public ResponseDTO deleteUser(String user_name) {

		if (StringUtils.isNotEmpty(user_name)) {
			try (Connection conn = ConnectionUtil.getConnection()) {
				String SQL = "DELETE FROM messageboard.users WHERE user_name = ? RETURNING *";

				// Instantiate a PreparedStatement to fill in the variables (?)
				PreparedStatement ps = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);

				ps.setString(1, user_name);

				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					Users deletedUser = new Users(rs.getInt("user_id"),
							rs.getString("user_name"),
							rs.getString("user_pass"),
							rs.getString("user_first_name"),
							rs.getString("user_last_name"),
							rs.getString("user_email"),
							rs.getString("user_auth_token"),
							rs.getTimestamp("user_auth_expiration"),
							rs.getBoolean("is_superadmin"));
					ArrayList<Users> deletedUsers = new ArrayList<Users>();
					deletedUsers.add(deletedUser);
					return new ResponseDTO(200, "Deleted user.", true, deletedUsers);
				} else {
					throw new SQLException("Could not delete user.");
				}

			} catch (SQLException e) {
				System.err.println("Error in deleteUser().");
				e.printStackTrace();
			}

			return new ResponseDTO(400, "Could not delete user.", false, null);
		}
		return new ResponseDTO(406, "Please specify username", false, null);
	}

	@Override
	public Users getUserByAuthToken(String user_auth_token) {
		if (user_auth_token != null) {
			try (Connection conn = ConnectionUtil.getConnection()) {
				String SQL = "SELECT * FROM messageboard.users WHERE user_auth_token = ?";
				PreparedStatement ps = conn.prepareStatement(SQL);
				ps.setString(1, user_auth_token);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					Users user = new Users(rs.getInt("user_id"),
							rs.getString("user_name"),
							rs.getString("user_pass"),
							rs.getString("user_first_name"),
							rs.getString("user_last_name"),
							rs.getString("user_email"),
							rs.getString("user_auth_token"),
							rs.getTimestamp("user_auth_expiration"),
							rs.getBoolean("is_superadmin"));

					return user;
				}
			} catch (SQLException e) {
				System.err.println("Error in getUserId().");
				e.printStackTrace();
			}
		}
		return null;
	}

}
