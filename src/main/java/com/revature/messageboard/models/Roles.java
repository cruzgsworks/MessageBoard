package com.revature.messageboard.models;

import io.javalin.core.security.RouteRole;

public enum Roles implements RouteRole{
	SUPER_ADMIN, ADMIN, MODERATOR, MEMBER, CURRENT_USER, ANYONE, EXPIRED
}
