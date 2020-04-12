package com.webchat.model;

import java.io.IOException;
import java.sql.SQLException;

public class Message {

	private String message, source, destination;
	User user;
	Team team;

	public Message(String msg) throws SQLException, ClassNotFoundException, IOException {
		String[] parts = msg.split("\\|");
		message = parts[1];
		source = parts[0];
		destination = parts[2];
		user = new User(source);
		team = new Team(destination, user.getOrgName());
	}

	public String getMessage() { 
		return message;
	}

	public User getSource() {
		return user;
	}

	public Team getDestination() {
		return team;
	}

}