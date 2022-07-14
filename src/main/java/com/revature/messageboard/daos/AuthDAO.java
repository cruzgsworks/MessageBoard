package com.revature.messageboard.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.revature.messageboard.dtos.ResponseDTO;
import com.revature.messageboard.models.Roles;
import com.revature.messageboard.models.Users;
import com.revature.messageboard.services.AuthService;
import com.revature.messageboard.utils.ConnectionUtil;

import io.javalin.http.Context;

public class AuthDAO {

	public ResponseDTO doLogin(Users user) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String SQL = "SELECT user_id, user_pass FROM messageboard.users WHERE user_name = ?";

			// Instantiate a PreparedStatement to fill in the variables (?)
			PreparedStatement ps = conn.prepareStatement(SQL);

			ps.setString(1, user.getUser_name());

			ResultSet checkUser = ps.executeQuery();

			if (checkUser.next()) {
				boolean checkPassword = new AuthService().comparePasswords(user.getUser_pass(),
						checkUser.getString("user_pass"));
				if (checkPassword) {
					String token = new AuthService().createAuthToken();
					SQL = "UPDATE messageboard.users SET user_auth_token = ?, user_auth_expiration = ? WHERE user_id = ? RETURNING *";
					ps = conn.prepareStatement(SQL);

					// Store new token and expiration timestamp to user's record
					ps.setString(1, token);
					long duration = (24 * 60 * 60 * 1000);
					ps.setTimestamp(2, new Timestamp((System.currentTimeMillis() + duration)));
					ps.setInt(3, checkUser.getInt("user_id"));
					ResultSet updateResult = ps.executeQuery();
					if (updateResult.next()) {
						Users currentUser = new Users(
								updateResult.getInt("user_id"),
								updateResult.getString("user_name"),
								updateResult.getString("user_pass"),
								updateResult.getString("user_first_name"),
								updateResult.getString("user_last_name"),
								updateResult.getString("user_email"),
								updateResult.getString("user_auth_token"),
								updateResult.getTimestamp("user_auth_expiration"),
								updateResult.getBoolean("is_superadmin"));
						ArrayList<Users> loggedUser = new ArrayList<Users>();
						loggedUser.add(currentUser);
						return new ResponseDTO(200, "Login successful", true, loggedUser);
					}
				}
			} else {
				throw new SQLException("Wrong login credentials");
			}
//			return new DAOResponse(400, "Wrong login credentials", false, null);

		} catch (SQLException e) {
			System.err.println("Error in doLogin().");
			e.printStackTrace();
		}
		return new ResponseDTO(400, "Could not login", false, null);
	}

	public Roles checkAuthToken(Context ctx) {
		String authToken = ctx.cookie("user_auth_token");

		if (authToken != null) {
			try (Connection conn = ConnectionUtil.getConnection()) {

				// Find user based on given user_auth_token cookie
				String SQL = "SELECT * FROM messageboard.users WHERE user_auth_token = ?";

				PreparedStatement ps = conn.prepareStatement(SQL);

				ps.setString(1, authToken);

				ResultSet rs = ps.executeQuery();

				String ctxPath = ctx.path().toLowerCase();
				System.out.println("ctxPath = " + ctxPath);

				if (rs.next()) {
					Users user = new Users(
							rs.getInt("user_id"),
							rs.getString("user_name"),
							rs.getString("user_pass"),
							rs.getString("user_first_name"),
							rs.getString("user_last_name"),
							rs.getString("user_email"),
							rs.getString("user_auth_token"),
							rs.getTimestamp("user_auth_expiration"),
							rs.getBoolean("is_superadmin"));

					if (ctxPath.contains("/api/users")) {

						// Check auth token expiration
						if (user.getUser_auth_expiration() != null) {
							if (user.getUser_auth_expiration().before(new Timestamp(System.currentTimeMillis()))) {
								return Roles.EXPIRED;
							}
						} else {
							return Roles.EXPIRED;
						}

						String httpMethod = ctx.method().toLowerCase();

						if (httpMethod.equals("get") || httpMethod.equals("put") || httpMethod.equals("delete")) {
							// Only current User should have access to their own info in this path.
							Users getUser = new UsersDAO().getUserByUserName(ctx.pathParam("user_name"));
							if (getUser != null) {
								if (getUser.getUser_id() == user.getUser_id()) {

									return Roles.CURRENT_USER;
								}
							}
						}

						if (httpMethod.equals("post")) {
							return Roles.ANYONE;
						}

						// immediately return superadmin
						if (user.isIs_superadmin()) {
							return Roles.SUPER_ADMIN;
						}
					} else if (ctxPath.equals("/api/board")) {

						// Check auth token expiration
						if (user.getUser_auth_expiration() != null) {

							if (user.getUser_auth_expiration().before(new Timestamp(System.currentTimeMillis()))) {
								return Roles.EXPIRED;
							}
						} else {
							return Roles.EXPIRED;
						}

						String httpMethod = ctx.method().toLowerCase();

						if (httpMethod.equals("get") || httpMethod.equals("post")) {
							return Roles.CURRENT_USER;
						}

					} else if (ctxPath.contains("/api/board/members")) {

						// Check auth token expiration
						if (user.getUser_auth_expiration() != null) {

							if (user.getUser_auth_expiration().before(new Timestamp(System.currentTimeMillis()))) {
								return Roles.EXPIRED;
							}
						} else {
							return Roles.EXPIRED;
						}

						String httpMethod = ctx.method().toLowerCase();

						if (user.isIs_superadmin()) {
							return Roles.SUPER_ADMIN;
						}

						if (httpMethod.equals("post") || httpMethod.equals("get")) {

							return new BoardDAO().checkBoardAccessLevel(ctx.pathParam("board_name"), user);
						}

					} else if (ctxPath.contains("/api/board/member")) {

						// Check auth token expiration
						if (user.getUser_auth_expiration() != null) {

							if (user.getUser_auth_expiration().before(new Timestamp(System.currentTimeMillis()))) {
								return Roles.EXPIRED;
							}
						} else {
							return Roles.EXPIRED;
						}

						String httpMethod = ctx.method().toLowerCase();

						if (user.isIs_superadmin()) {
							return Roles.SUPER_ADMIN;
						}

						if (httpMethod.equals("get") || httpMethod.equals("put") || httpMethod.equals("delete")) {

							return new BoardDAO().checkBoardAccessLevel(ctx.pathParam("board_name"), user);

						}

					} else if (ctxPath.contains("/api/board")) {

						// Check auth token expiration
						if (user.getUser_auth_expiration() != null) {

							if (user.getUser_auth_expiration().before(new Timestamp(System.currentTimeMillis()))) {
								return Roles.EXPIRED;
							}
						} else {
							return Roles.EXPIRED;
						}

						String httpMethod = ctx.method().toLowerCase();

						if (user.isIs_superadmin()) {
							return Roles.SUPER_ADMIN;
						}

						if (httpMethod.equals("delete") || httpMethod.equals("put")) {

							return new BoardDAO().checkBoardAccessLevel(ctx.pathParam("board_name"), user);
						}

					} else if (ctxPath.contains("/api/messages/search/")) {

						// Check auth token expiration
						if (user.getUser_auth_expiration() != null) {

							if (user.getUser_auth_expiration().before(new Timestamp(System.currentTimeMillis()))) {
								return Roles.EXPIRED;
							}
						} else {
							return Roles.EXPIRED;
						}

						String httpMethod = ctx.method().toLowerCase();

						if (user.isIs_superadmin()) {
							return Roles.SUPER_ADMIN;
						}

						if (httpMethod.equals("get")) {

							return new BoardDAO().checkBoardAccessLevel(ctx.pathParam("board_name"), user);
						}

					} else if (ctxPath.equals("/api/messages")) {

						// Check auth token expiration
						if (user.getUser_auth_expiration() != null) {

							if (user.getUser_auth_expiration().before(new Timestamp(System.currentTimeMillis()))) {
								return Roles.EXPIRED;
							}
						} else {
							return Roles.EXPIRED;
						}

						String httpMethod = ctx.method().toLowerCase();

						if (user.isIs_superadmin()) {
							return Roles.SUPER_ADMIN;
						}

						if (httpMethod.equals("put") || httpMethod.equals("delete")) {

							return Roles.CURRENT_USER;
						}

					} else if (ctxPath.contains("/api/messages")) {

						// Check auth token expiration
						if (user.getUser_auth_expiration() != null) {

							if (user.getUser_auth_expiration().before(new Timestamp(System.currentTimeMillis()))) {
								return Roles.EXPIRED;
							}
						} else {
							return Roles.EXPIRED;
						}

						String httpMethod = ctx.method().toLowerCase();

						if (user.isIs_superadmin()) {
							return Roles.SUPER_ADMIN;
						}

						if (httpMethod.equals("post") || httpMethod.equals("get")) {

							return new BoardDAO().checkBoardAccessLevel(ctx.pathParam("board_name"), user);
						}

					} else if (ctxPath.contains("/api/superadmin")) {
						if (user.isIs_superadmin()) {
							return Roles.SUPER_ADMIN;
						}
					}

				} else {
					return Roles.ANYONE;
				}
				return Roles.ANYONE;

			} catch (SQLException e) {
				System.err.println("Error. checkAuthToken() failed.");
				e.printStackTrace();
			}
		}

		// TODO Auto-generated method stub
		return Roles.ANYONE;
	}
}
