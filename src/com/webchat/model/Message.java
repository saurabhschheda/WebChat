package com.webchat.model;

public class Message {

	String message;
	User source;
	Team destination;

	public Message(String m, User s, Team d) {
		message = m;
		source = s;
		destination = d;
	}

}