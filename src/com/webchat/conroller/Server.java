package com.webchat.conroller;

import javax.websocket.OnMessage;
import javax.websocket.OnClose;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;
import javax.websocket.CloseReason;

import java.util.Map;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.ArrayList;

import com.webchat.model.User;
import com.webchat.model.Team;
import com.webchat.model.Message;

@ServerEndpoint("/chat")
public class Server {

	private static Map<User, Session> clients = Collections.synchronizedMap(new LinkedHashMap<User, Session>());

	private synchronized void initUser(String username, Session s) throws Exception {
		User user = new User(username);
		clients.put(user, s);
		ArrayList<Team> rooms = user.getTeams();
		String msg = "init";
		for (Team room : rooms) {
			msg = msg.concat("|" + room.getName());
		}
		s.getBasicRemote().sendText(msg);
	}

	private synchronized void sendMessage(String msg, Session s) throws Exception {
		// Send Message to those sessions that contain the destined Room
	}

	@OnMessage
	public synchronized void onMessage(Session s, String msg) throws Exception {
		String eventName = msg.substring(0, msg.indexOf("|"));
		String data = msg.substring(msg.indexOf("|") + 1);
		try {
			switch (eventName) {
				case "newUser":
					initUser(data, s);
					break;
				case "chat":
					sendMessage(data, s);
					break;
				default:
					System.out.println(msg);
			}
		} catch (Exception e) {
			s.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, e.toString()));
		}
	}

	@OnClose
	public synchronized void onClose(Session s, CloseReason reason) {
		System.out.println(reason);
	}
   
}
