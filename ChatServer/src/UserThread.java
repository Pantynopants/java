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
	}

	public UserThread(Socket s2) {
		this.s = s2;
		
	}

	public void UserAction() throws IOException {
		
		while(true){
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
				/*
				 * make a communication protocol between server and client
				 * 
				 * 101|name, pass -- login to server
				 * 102| 		   --ask server for user list
				 * 103|from, to, message -- talk to other user
				 * 104|from, to, file.getName()
				 * 
				 */
				
				String message = dis.readUTF();
				if (StringUtils.getCode(message).equals("101")) {
					//log in request name, pass 
					
					String content = StringUtils.getContent(message);
					System.out.println(content);
					name = content.split(",")[0];
					String pass = content.split(",")[1];
					
					System.out.println(name + " is login" + ", pass is " + pass);
					
					boolean flag = UserinfoDAO.validateUser(name, pass);
					//UserinfoDAO.getUserList();
					if (flag) {
						
						dos.writeUTF("success");
						SendMessageThread send = new SendMessageThread(s, name);
						send.start();
						System.out.println("start SendMessageThread");
						
						
					} else {
						dos.writeUTF("failure");
					}
					
					dos.flush();
					
				}else if (StringUtils.getCode(message).equals("102")) {
					
					//get all user list
					ArrayList<Userinfo> userList = UserinfoDAO.userList;
					
					StringBuffer buffer = new StringBuffer();
					
					for (int i = 0; i < userList.size(); i++) {
						
						Userinfo u = userList.get(i);
						buffer.append(u.getName() + ",");
						
					}
					System.out.println(buffer);
					
					String userstr = buffer.substring(0, buffer.length()-1);
					Message m = new Message();
					
					m.setFrom("server");//user less
					m.setTo(name);
					m.setContent(userstr);
					m.setMessageType("102");
					m.setSendTime(new Date());
					
					//save message to a place
					MessageDAO.messageList.add(m);
					System.out.println(name + " say 102");
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
					//System.out.println("message is: " + m.getFrom() + m.getTo() + m.getContent());
					
				}else if (message.startsWith("104")){
					
					String content = message.substring(message.indexOf('|')+1);
					Message m =new Message();
					m.setFrom(content.split(",")[0]);
					m.setTo(content.split(",")[1]);
					m.setContent(content.split(",")[2]);				
					m.setSendTime(new Date());;
					m.setMessageType("104");
					MessageDAO.messageList.add(m);
					new FilerecieveThread(StartServer.fileSocket.accept(), content.split(",")[2]).start();

				}
				//dis.close();
				
		}
		
	}
	public ServerSocket requestPort() {
	
		
		int conectPort = 0;
		
		boolean requestflag = true;
		ServerSocket serverSocket = null;
		Random generator = new Random();
		
		
			while (requestflag) {
				int port_high = 1 + generator.nextInt(20);
				int port_low = 100 + generator.nextInt(1000);
				try {
					
					conectPort = port_high*256 + port_low;
					serverSocket = new ServerSocket(conectPort);
					
//					dos.writeUTF("new port is" + conectPort);
//					dos.flush();
					requestflag = false;
					
					break;
					
				} catch (IOException e) {
					System.err.println("requst port now");
					e.printStackTrace();
					
				}
		}
			return serverSocket;
	}
	
	@Override
	public void run() {
		
		
		
		try {
			//for every user, it should get another unique port
			//fileSocket = requestPort();
			
			UserAction();
				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
