package com.webchat.conroller;

import com.webchat.model.Team;
import com.webchat.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.SocketException;
import java.util.*;

@ServerEndpoint("/chat")
public class Server {

	private static Map<String, User> loggedInUsers = Collections.synchronizedMap(new LinkedHashMap<>());
	private static final Logger logger = LogManager.getLogger("Server");

	private synchronized void initialize(String username, Session session) throws Exception {
		User user = User.findUser(username);
		logger.info("User " + username + " successfully logged in");
		user.setSession(session);
		loggedInUsers.put(username, user);
		List<Team> teams = user.getTeams();
		String msg = "init";
		for (Team team : teams) {
			msg = msg.concat("|" + team.getTeamName());
		}
		session.getBasicRemote().sendText(msg);
		logger.info("Sent message " + msg + " to " + username);
	}

	private synchronized void sendMessage(String msg, Session s) throws Exception {
		String[] parts = msg.split("\\|");
		User source = loggedInUsers.get(parts[0]);
		String message = parts[1];
		Team destination = source.findTeamByName(parts[2]);
		List<String> receivers = destination.getMembers();
		for (String receiver : receivers) {
			if (loggedInUsers.containsKey(receiver)) {
				Session recipientSession = loggedInUsers.get(receiver).getSession();
				recipientSession.getBasicRemote().sendText("message|" + msg);
				logger.info("Sent message " + msg + " to " + receiver);
			}
		}
	}

	@OnMessage
	public synchronized void onMessage(Session session, String msg) throws IOException {
		logger.info("Received message " + msg + " from client");
		String eventName = msg.substring(0, msg.indexOf("|"));
		String data = msg.substring(msg.indexOf("|") + 1);
		try {
			switch (eventName) {
				case "newUser":
					initialize(data, session);
					break;
				case "chat":
					sendMessage(data, session);
					break;
				default:
					logger.error("Unknown message: " + msg);
					throw new SocketException();
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
