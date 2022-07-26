package com.revature.messageboard.controller;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.revature.messageboard.daos.AuthDAO;
import com.revature.messageboard.dtos.ResponseDTO;
import com.revature.messageboard.models.Users;
import com.revature.messageboard.utils.Helper;

import io.javalin.http.Handler;

public class AuthController {

	public Handler login = (ctx) -> {
		Gson gson = new Gson();

		AuthDAO auth = new AuthDAO();
		Users requestBody = gson.fromJson(ctx.body(), Users.class);

		ResponseDTO doLogin = auth.doLogin(requestBody);
		String output = gson.toJson(doLogin);
		if (doLogin.getResults() != null) {
			ArrayList<Users> userList = Helper.convertToUsersArrayList(doLogin.getResults());

			// Store token as cookie. Max-Age is 24 hrs.
			ctx.contentType("application/json")
					.status(doLogin.getStatus())
					.result(output)
					.cookie("user_auth_token", userList.get(0).getUser_auth_token(), (24 * 60 * 60));
		} else {
			ctx.contentType("application/json")
					.status(doLogin.getStatus())
					.result(output);
		}

	};

}
