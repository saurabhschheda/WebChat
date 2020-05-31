package com.webchat.conroller;

import com.webchat.model.Team;
import com.webchat.model.User;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

@ServerEndpoint("/chat")
public class Server {

	private static Map<String, Session> clients = Collections.synchronizedMap(new LinkedHashMap<String, Session>());

	private synchronized void initialize(String username, Session session) throws Exception {
		User user = User.findUser(username);
		clients.put(username, session);
		List<Team> teams = user.getTeams();
		String msg = "init";
		for (Team team : teams) {
			msg = msg.concat("|" + team.getTeamName());
		}
		session.getBasicRemote().sendText(msg);
	}

//	private synchronized void sendMessage(String msg, Session s) throws Exception {
//		Message message = new Message(msg);
//		ArrayList<User> receivers = message.getDestination().getUsers();
//		for (User receiver : receivers) {
//			if (clients.containsKey(receiver.getUsername())) {
//				clients.get(receiver.getUsername()).getBasicRemote().sendText("message|" + msg);
//			}
//		}
//	}

	@OnMessage
	public synchronized void onMessage(Session session, String msg) throws IOException {
		String eventName = msg.substring(0, msg.indexOf("|"));
		String data = msg.substring(msg.indexOf("|") + 1);
		try {
			switch (eventName) {
				case "newUser":
					initialize(data, session);
					break;
//				case "chat":
//					sendMessage(data, session);
//					break;
				default:
					System.out.println(msg);
			}
		} catch (Exception e) {
			session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, e.toString()));
		}
	}

	@OnClose
	public synchronized void onClose(CloseReason reason) {
		System.out.println(reason);
	}

}
