package com.revature.messageboard;

import com.revature.messageboard.routes.Routing;

public class Launcher {

	public static void main(String[] args) {
		
		// Run Javalin app
		Routing mainRoute = new Routing();
		mainRoute.initRoutes();

	}

}
