package com.revature.messageboard.controller;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.revature.messageboard.daos.UsersDAO;
import com.revature.messageboard.dtos.ResponseDTO;
import com.revature.messageboard.models.Users;
import com.revature.messageboard.services.AuthService;

import io.javalin.http.Handler;

public class UsersController {

	public Handler createSuperAdmin = (ctx) -> {
		Gson gson = new Gson();

		UsersDAO userDAO = new UsersDAO();
		Users user = gson.fromJson(ctx.body(), Users.class);

		// System.out.println(requestBody.toString());

		user.setUser_pass(new AuthService().doEncrypt(user.getUser_pass()));
		user.setIs_superadmin(true);

		ResponseDTO createSuperAdmin = userDAO.createSuperAdmin(user);
		String output = gson.toJson(createSuperAdmin);

		ctx.contentType("application/json").status(createSuperAdmin.getStatus()).result(output);

	};
	
	public Handler superUpdateUser = (ctx) -> {
		Gson gson = new Gson();

		UsersDAO userDAO = new UsersDAO();
		Users requestBody = gson.fromJson(ctx.body(), Users.class);

//		System.out.println(requestBody.toString());

		ResponseDTO superUpdateUser = userDAO.superUpdateUser(requestBody);
		String output = gson.toJson(superUpdateUser);

		ctx.contentType("application/json");
		ctx.status(superUpdateUser.getStatus());
		ctx.result(output);
	};

	public Handler createUser = (ctx) -> {
		Gson gson = new Gson();

		UsersDAO userDAO = new UsersDAO();
		Users user = gson.fromJson(ctx.body(), Users.class);

		// System.out.println(requestBody.toString());

		user.setUser_pass(new AuthService().doEncrypt(user.getUser_pass()));
		user.setIs_superadmin(false);

		ResponseDTO createUser = userDAO.registerNewUser(user);
		String output = gson.toJson(createUser);

		ctx.contentType("application/json").status(createUser.getStatus()).result(output);

	};

	public Handler viewUser = (ctx) -> {
		Gson gson = new Gson();

		Users user = new UsersDAO().getUserByUserName(ctx.pathParam("user_name"));

		if (user != null) {
			ArrayList<Users> userList = new ArrayList<Users>();
			userList.add(user);
			ResponseDTO response = new ResponseDTO(200, "User found", true, userList);
			String output = gson.toJson(response);
			ctx.contentType("application/json").status(response.getStatus()).result(output);
		} else {
			ResponseDTO response = new ResponseDTO(400, "User not found", false, null);
			String output = gson.toJson(response);
			ctx.contentType("application/json");
			ctx.status(response.getStatus());
			ctx.result(output);
		}
	};

	public Handler updateUser = (ctx) -> {
		Gson gson = new Gson();

		UsersDAO userDAO = new UsersDAO();
		Users requestBody = gson.fromJson(ctx.body(), Users.class);

//		System.out.println(requestBody.toString());

		ResponseDTO updateUser = userDAO.updateUser(ctx.pathParam("user_name"), requestBody);
		String output = gson.toJson(updateUser);

		ctx.contentType("application/json");
		ctx.status(updateUser.getStatus());
		ctx.result(output);
	};

	public Handler deleteUser = (ctx) -> {
		Gson gson = new Gson();

		UsersDAO userDAO = new UsersDAO();

		ResponseDTO deleteUser = userDAO.deleteUser(ctx.pathParam("user_name"));
		String output = gson.toJson(deleteUser);

		ctx.contentType("application/json");
		ctx.status(deleteUser.getStatus());
		ctx.result(output);
	};

}
