package com.revature.messageboard.controller;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.revature.messageboard.daos.MessagesDAO;
import com.revature.messageboard.daos.UsersDAO;
import com.revature.messageboard.dtos.ResponseDTO;
import com.revature.messageboard.models.Messages;
import com.revature.messageboard.models.Users;

import io.javalin.http.Handler;

public class MessagesController {

	public Handler createMessage = (ctx) -> {
		Gson gson = new Gson();

		MessagesDAO messagesDAO = new MessagesDAO();
		// Convert JSON data to Board Object
		Messages requestBody = gson.fromJson(ctx.body(), Messages.class);
		Users curUser = new UsersDAO().getUserByAuthToken(ctx.cookie("user_auth_token"));

		ResponseDTO createMessage = messagesDAO.createMessage(ctx.pathParam("board_name"), requestBody, curUser);
		String output = gson.toJson(createMessage);

		ctx.contentType("application/json");
		ctx.status(createMessage.getStatus());
		ctx.result(output);
	};

	public Handler viewAllMessages = (ctx) -> {
		Gson gson = new Gson();

		MessagesDAO messagesDAO = new MessagesDAO();

		ResponseDTO viewMessage = messagesDAO.viewMessage(ctx.pathParam("board_name"));
		String output = gson.toJson(viewMessage);

		ctx.contentType("application/json");
		ctx.status(viewMessage.getStatus());
		ctx.result(output);
	};

	public Handler searchMessages = (ctx) -> {
		Gson gson = new Gson();

		MessagesDAO messagesDAO = new MessagesDAO();

		// Convert JSON data to Board Object
		// Messages requestBody = gson.fromJson(ctx.body(), Messages.class);

		String strSearch = ctx.queryParam("content");
		System.out.println(strSearch);

		ResponseDTO searchMessage = messagesDAO.searchMessage(ctx.pathParam("board_name"), strSearch);
		String output = gson.toJson(searchMessage);

		ctx.contentType("application/json");
		ctx.status(searchMessage.getStatus());
		ctx.result(output);
	};

	public Handler updateMessageById = (ctx) -> {
		Gson gson = new Gson();

		MessagesDAO messagesDAO = new MessagesDAO();
		// Convert JSON data to Board Object
		Messages requestBody = gson.fromJson(ctx.body(), Messages.class);
		Users curUser = new UsersDAO().getUserByAuthToken(ctx.cookie("user_auth_token"));

		ResponseDTO updateMessage = messagesDAO.updateMessage(requestBody, curUser);
		String output = gson.toJson(updateMessage);

		ctx.contentType("application/json");
		ctx.status(updateMessage.getStatus());
		ctx.result(output);
	};

	public Handler deleteMessageById = (ctx) -> {
		Gson gson = new Gson();

		MessagesDAO messagesDAO = new MessagesDAO();

		// Convert JSON data to Board Object
		// Messages requestBody = gson.fromJson(ctx.body(), Messages.class);
		Users curUser = new UsersDAO().getUserByAuthToken(ctx.cookie("user_auth_token"));

		String idParam = ctx.queryParam("id");
		int deleteID = 0;
		if (StringUtils.isNoneEmpty(idParam)) {
			deleteID = Integer.parseInt(idParam);
		}
		System.out.println(deleteID);

		ResponseDTO deleteMessage = messagesDAO.deleteMessage(deleteID, curUser);
		String output = gson.toJson(deleteMessage);

		ctx.contentType("application/json");
		ctx.status(deleteMessage.getStatus());
		ctx.result(output);
	};
}
