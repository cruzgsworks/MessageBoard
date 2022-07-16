package com.revature.messageboard.routes;

import static io.javalin.apibuilder.ApiBuilder.delete;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.put;

import com.google.gson.Gson;
import com.revature.messageboard.controller.AuthController;
import com.revature.messageboard.controller.BoardController;
import com.revature.messageboard.controller.MessagesController;
import com.revature.messageboard.controller.UsersController;
import com.revature.messageboard.daos.AuthDAO;
import com.revature.messageboard.dtos.ResponseDTO;
import com.revature.messageboard.models.Roles;

import io.javalin.Javalin;

public class Routing {

	private Javalin app;

	public void initRoutes() {

		// Instance of Javalin
		this.app = Javalin.create(config -> {
			config.enableCorsForAllOrigins();
			config.accessManager((handler, ctx, routeRoles) -> {
				Roles role = new AuthDAO().checkAuthToken(ctx);
				System.out.println("Given role = " + role.name());
				if (routeRoles.contains(role)) {
					handler.handle(ctx);
				} else {
					if (role.equals(Roles.EXPIRED)) {
						ResponseDTO resp = new ResponseDTO(401, "Expired Auth Token. Please Login.", false, null);
						ctx.status(resp.getStatus()).contentType("application/json").result(new Gson().toJson(resp));
					} else {
						ResponseDTO resp = new ResponseDTO(401, "Unauthorized", false, null);
						ctx.status(resp.getStatus()).contentType("application/json").result(new Gson().toJson(resp));
					}
				}
			});
		}).start(8080);

		this.app.routes(() -> {
			path("/api/superadmin", () -> {
				// For initial setup on demo. This handler should not exist
				post(new UsersController().createSuperAdmin, Roles.ANYONE);
				// This method can also modify is_superadmin field
				put(new UsersController().superUpdateUser, new Roles[] { Roles.SUPER_ADMIN });
			});
			path("/api/superadmin/boards", () -> {
				// Superadmin can view all boards
				get(new BoardController().viewAllBoards, new Roles[] { Roles.SUPER_ADMIN });
			});
			path("/api/users", () -> {
				// Anybody can register
				post(new UsersController().createUser, Roles.ANYONE);
			});
			path("/api/users/{user_name}", () -> {
				// Only Super Admin, and Current User can update user
				put(new UsersController().updateUser, new Roles[] { Roles.SUPER_ADMIN, Roles.CURRENT_USER });
				// Only Super Admin, and Current User can delete user
				delete(new UsersController().deleteUser, new Roles[] { Roles.SUPER_ADMIN, Roles.CURRENT_USER });
				// Only Super Admin, and Current User can only read user info
				get(new UsersController().viewUser, new Roles[] { Roles.SUPER_ADMIN, Roles.CURRENT_USER });
			});
			path("/api/board", () -> {
				// Users can create their own boards
				post(new BoardController().createBoard, Roles.CURRENT_USER);
				// View owned boards
				get(new BoardController().viewMyBoards, new Roles[] { Roles.SUPER_ADMIN, Roles.CURRENT_USER });
			});
			path("/api/board/{board_name}", () -> {
				// Delete board
				delete(new BoardController().deleteBoard, new Roles[] { Roles.SUPER_ADMIN, Roles.ADMIN });
				// Update board
				put(new BoardController().updateBoard, new Roles[] { Roles.SUPER_ADMIN, Roles.ADMIN });
			});
			path("/api/board/member/{board_name}/{user_name}", () -> {
				// Get member access info
				get(new BoardController().getMemberAccess,
						new Roles[] { Roles.SUPER_ADMIN, Roles.ADMIN, Roles.MODERATOR, Roles.MEMBER });
				// Update access of member
				put(new BoardController().updateMemberAccess, new Roles[] { Roles.SUPER_ADMIN, Roles.ADMIN });
				// Delete member
				delete(new BoardController().deleteMember,
						new Roles[] { Roles.SUPER_ADMIN, Roles.ADMIN, Roles.MODERATOR, Roles.MEMBER });
			});
			path("/api/board/members/{board_name}", () -> {
				// Get board members
				get(new BoardController().listMembers,
						new Roles[] { Roles.SUPER_ADMIN, Roles.ADMIN, Roles.MODERATOR, Roles.MEMBER });
				// Add Member to board
				post(new BoardController().addMember, new Roles[] { Roles.SUPER_ADMIN, Roles.ADMIN, Roles.MODERATOR });
			});
			path("/api/login", () -> {
				// Anybody can login and get auth token cookie if successful
				post(new AuthController().login, Roles.ANYONE);
			});
			path("/api/messages", () -> {
				// update message by id
				put(new MessagesController().updateMessageById, new Roles[] { Roles.SUPER_ADMIN, Roles.CURRENT_USER });
				// delete message by id
				delete(new MessagesController().deleteMessageById,
						new Roles[] { Roles.SUPER_ADMIN, Roles.CURRENT_USER });
			});
			path("/api/messages/{board_name}", () -> {
				// create new message
				post(new MessagesController().createMessage,
						new Roles[] { Roles.SUPER_ADMIN, Roles.ADMIN, Roles.MODERATOR, Roles.MEMBER });
				// get all messages
				get(new MessagesController().viewAllMessages,
						new Roles[] { Roles.SUPER_ADMIN, Roles.ADMIN, Roles.MODERATOR, Roles.MEMBER });
			});
			path("/api/messages/search/{board_name}", () -> {
				// search messages
				get(new MessagesController().searchMessages,
						new Roles[] { Roles.SUPER_ADMIN, Roles.ADMIN, Roles.MODERATOR, Roles.MEMBER });
			});
		});

	}

}
