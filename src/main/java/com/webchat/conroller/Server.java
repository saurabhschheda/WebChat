//package com.webchat.conroller;
//
//import com.webchat.model.Message;
//import com.webchat.model.Team;
//import com.webchat.model.User;
//
//import javax.websocket.CloseReason;
//import javax.websocket.OnClose;
//import javax.websocket.OnMessage;
//import javax.websocket.Session;
//import javax.websocket.server.ServerEndpoint;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//@ServerEndpoint("/chat")
//public class Server {
//
//	private static Map<String, Session> clients = Collections.synchronizedMap(new LinkedHashMap<String, Session>());
//
//	private synchronized void initUser(String username, Session s) throws Exception {
//		User user = new User(username);
//		clients.put(username, s);
//		ArrayList<Team> rooms = user.getTeams();
//		String msg = "init";
//		for (Team room : rooms) {
//			msg = msg.concat("|" + room.getName());
//		}
//		s.getBasicRemote().sendText(msg);
//	}
//
//	private synchronized void sendMessage(String msg, Session s) throws Exception {
//		Message message = new Message(msg);
//		ArrayList<User> receivers = message.getDestination().getUsers();
//		for (User receiver : receivers) {
//			if (clients.containsKey(receiver.getUsername())) {
//				clients.get(receiver.getUsername()).getBasicRemote().sendText("message|" + msg);
//			}
//		}
//	}
//
//	@OnMessage
//	public synchronized void onMessage(Session s, String msg) throws Exception {
//		String eventName = msg.substring(0, msg.indexOf("|"));
//		String data = msg.substring(msg.indexOf("|") + 1);
//		try {
//			switch (eventName) {
//				case "newUser":
//					initUser(data, s);
//					break;
//				case "chat":
//					sendMessage(data, s);
//					break;
//				default:
//					System.out.println(msg);
//			}
//		} catch (Exception e) {
//			// e.printStackTrace();
//			s.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, e.toString()));
//		}
//	}
//
//	@OnClose
//	public synchronized void onClose(CloseReason reason) {
//		System.out.println(reason);
//	}
//
//}
