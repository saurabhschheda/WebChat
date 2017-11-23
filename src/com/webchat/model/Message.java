package com.webchat.model;

public class Message {

	private String message, source, destination;

	public Message(String msg) {
		String[] parts = msg.split("|");
		message = parts[1];
		source = parts[0];
		destination = parts[2];
	}

	public String getMessage() { 
		return message;
	}

	public String getSource() {
		return source;
	}

	public String getDestination() {
		return destination;
	}

}