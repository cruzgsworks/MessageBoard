package com.revature.messageboard.controller;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.revature.messageboard.daos.BoardDAO;
import com.revature.messageboard.daos.UsersDAO;
import com.revature.messageboard.dtos.ResponseDTO;
import com.revature.messageboard.models.Board;
import com.revature.messageboard.models.BoardMemberAccess;
import com.revature.messageboard.models.Users;

import io.javalin.http.Handler;

public class BoardController {

	public Handler createBoard = (ctx) -> {
		Gson gson = new Gson();

		BoardDAO boardDAO = new BoardDAO();
		// Convert JSON data to Board Object
		Board requestBody = gson.fromJson(ctx.body(), Board.class);
		Users curUser = new UsersDAO().getUserByAuthToken(ctx.cookie("user_auth_token"));

		ResponseDTO createBoard = boardDAO.createBoard(requestBody, curUser);
		String output = gson.toJson(createBoard);

		ctx.contentType("application/json");
		ctx.status(createBoard.getStatus());
		ctx.result(output);
	};

	public Handler viewMyBoards = (ctx) -> {
		Gson gson = new Gson();

		BoardDAO boardDAO = new BoardDAO();
		Users curUser = new UsersDAO().getUserByAuthToken(ctx.cookie("user_auth_token"));

		ResponseDTO myBoards = boardDAO.viewMyBoards(curUser);

		String output = gson.toJson(myBoards);

		ctx.contentType("application/json").status(myBoards.getStatus()).result(output);

	};

	public Handler viewAllBoards = (ctx) -> {
		Gson gson = new Gson();

		BoardDAO boardDAO = new BoardDAO();
		ResponseDTO myBoards = boardDAO.viewAllBoards();

		String output = gson.toJson(myBoards);

		ctx.contentType("application/json").status(myBoards.getStatus()).result(output);

	};

	public Handler deleteBoard = (ctx) -> {
		Gson gson = new Gson();

		BoardDAO boardDAO = new BoardDAO();
		// Convert JSON data to Board Object
//		Users curUser = new UsersDAO().getUserByAuthToken(ctx.cookie("user_auth_token"));

		ResponseDTO deleteBoard = boardDAO.deleteBoard(ctx.pathParam("board_name"));
		String output = gson.toJson(deleteBoard);

		ctx.contentType("application/json").status(deleteBoard.getStatus()).result(output);
	};

	public Handler addMember = (ctx) -> {
		Gson gson = new Gson();

		BoardDAO boardDAO = new BoardDAO();
		// Convert JSON data to Board Object
		Users newMember = gson.fromJson(ctx.body(), Users.class);

		ResponseDTO addMember = boardDAO.addMember(ctx.pathParam("board_name"), newMember);
		String output = gson.toJson(addMember);

		ctx.contentType("application/json").status(addMember.getStatus()).result(output);
	};

	public Handler updateBoard = (ctx) -> {
		Gson gson = new Gson();

		BoardDAO boardDAO = new BoardDAO();
		// Convert JSON data to Board Object
		Board requestBody = gson.fromJson(ctx.body(), Board.class);

		ResponseDTO updateBoard = boardDAO.updateBoard(ctx.pathParam("board_name"), requestBody);
		String output = gson.toJson(updateBoard);

		ctx.contentType("application/json").status(updateBoard.getStatus()).result(output);
	};

	public Handler listMembers = (ctx) -> {
		Gson gson = new Gson();

		BoardDAO boardDAO = new BoardDAO();
		// Users curUser = new
		// UsersDAO().getUserByAuthToken(ctx.cookie("user_auth_token"));

		ResponseDTO listMembers = boardDAO.listMembers(ctx.pathParam("board_name"));

		String output = gson.toJson(listMembers);

		ctx.contentType("application/json").status(listMembers.getStatus()).result(output);

	};

	public Handler getMemberAccess = (ctx) -> {
		Gson gson = new Gson();

		BoardDAO boardDAO = new BoardDAO();
		// Convert JSON data to Board Object
		// BoardMemberAccess myBMA = gson.fromJson(ctx.body(), BoardMemberAccess.class);

		BoardMemberAccess getMemberAccess = boardDAO.getMemberAccess(ctx.pathParam("board_name"),
				ctx.pathParam("user_name"));

		if (getMemberAccess != null) {
			ArrayList<BoardMemberAccess> bmaList = new ArrayList<BoardMemberAccess>();
			bmaList.add(getMemberAccess);
			ResponseDTO response = new ResponseDTO(200, "Retrieved member access information", true, bmaList);
			String output = gson.toJson(response);
			ctx.contentType("application/json").status(response.getStatus()).result(output);
		} else {
			ResponseDTO response = new ResponseDTO(400, "Member not found", false, null);
			String output = gson.toJson(response);
			ctx.contentType("application/json");
			ctx.status(response.getStatus());
			ctx.result(output);
		}
	};

	public Handler updateMemberAccess = (ctx) -> {
		Gson gson = new Gson();

		BoardDAO boardDAO = new BoardDAO();
		// Convert JSON data to Board Object
		BoardMemberAccess myBMA = gson.fromJson(ctx.body(), BoardMemberAccess.class);

		ResponseDTO updateBMA = boardDAO.updateMemberAccess(ctx.pathParam("board_name"), ctx.pathParam("user_name"),
				myBMA);
		String output = gson.toJson(updateBMA);

		ctx.contentType("application/json").status(updateBMA.getStatus()).result(output);
	};
	
	public Handler deleteMember = (ctx) -> {
		Gson gson = new Gson();

		BoardDAO boardDAO = new BoardDAO();
		
		Users curUser = new UsersDAO().getUserByAuthToken(ctx.cookie("user_auth_token"));

		ResponseDTO deleteMember = boardDAO.deleteMember(ctx.pathParam("board_name"), ctx.pathParam("user_name"), curUser);
		String output = gson.toJson(deleteMember);

		ctx.contentType("application/json").status(deleteMember.getStatus()).result(output);
	};

}
