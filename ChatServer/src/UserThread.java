import java.io.*;
import java.net.*;
import java.util.*;


public class UserThread extends Thread{

	private Socket s = null;
	
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	
	private String name = null;
	private String pass = null;
	
	public UserThread() {
		// TODO Auto-generated constructor stub
	}
	
	public UserThread(Socket s) {
		// TODO Auto-generated constructor stub
	}

	public boolean UserLogin() throws IOException {
		
		dis = new DataInputStream(s.getInputStream());
		dos = new DataOutputStream(s.getOutputStream());
		/*
		 * make a communication protocol between server and client
		 * 
		 * 101|name, pass -- login to server
		 * 102| 		   --ask server for user list
		 * 102|from, to, message -- talk to other user
		 * 
		 */
		
		String message = dis.readUTF();
		if (StringUtils.getCode(message).equals("101")) {
			//log in request name, pass 
			
			String content = StringUtils.getContent(message);
			name = content.split(",")[0];
			String pass = content.split(",")[1];
			
			boolean flag = UserinfoDAO.validateUser(name, pass);
			
			if (flag) {
				
				dos.writeUTF("success");
				SendMessageThread send = new SendMessageThread(s, name);
				send.start();
				
			} else {
				dos.writeUTF("failure");
			}
			
			dos.flush();
			
		}else if (StringUtils.getCode(message).equals("102")) {
			
			//get all user list
			List<Userinfo> userList = UserinfoDAO.userList;
			
			StringBuffer buffer = new StringBuffer();
			
			for (int i = 0; i < userList.size(); i++) {
				
				Userinfo u = userList.get(i);
				buffer.append(u.getName() + ",");
				
			}
			
			String userstr = buffer.substring(0, buffer.length()-1);
			Message m = new Message();
			
			m.setFrom("server");//user less
			m.setTo(name);
			m.setContent(userstr);
			m.setMessageType("102");
			m.setSendTime(new Date());
			
			//save message to a place
			MessageDAO.messageList.add(m);
			
		}else if (StringUtils.getCode(message).equals("103")) {
			//from, to, message
			
			String content = StringUtils.getContent(message);
			Message m = new Message();
			
			m.setFrom(content.split(",")[0]);
			m.setTo(content.split(",")[1]);
			m.setContent(content.split(",")[2]);
			m.setMessageType("103");
			m.setSendTime(new Date());
			
			//save message to a place
			MessageDAO.messageList.add(m);
		}
		
		return false;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}
	
}
