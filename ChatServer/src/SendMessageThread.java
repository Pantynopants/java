import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class SendMessageThread extends Thread{

	private Socket socket = null;
	
	private String name = null;
	
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	private BufferedReader reader = null;  
    private PrintWriter writer = null;
	
	private ArrayList<Message> myMessage = new ArrayList<Message>();
	
	public SendMessageThread() {
	}
	/*
	 * receive message from user to server, and send it to target user
	 * 
	 * socket: target user s
	 * name: target user s
	 * 
	 */
	public SendMessageThread(Socket s, String name) {
		this.socket = s;
		this.name = name;
	}

	@Override
	public void run() {
		
		
		System.out.println(name + "'s send thread ");
		while (true) {
			try {
				writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
				
				dos = new DataOutputStream(socket.getOutputStream());
				dis = new DataInputStream(socket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (MessageDAO.isEmpty()) {
				//System.out.println("null message list");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}
			//get message
			myMessage = new ArrayList<Message>(MessageDAO.getMyMessage(name));
			//System.out.println("get my message");
			
			//myMessage.addAll(MessageDAO.getMyMessage(name));
			//System.out.println("get " + name + "'s message" + myMessage);
			
				
			if (myMessage != null) {
				StringBuffer buffer = new StringBuffer();
				
				/*
				 * make a communication protocol between server and client
				 * c2s
				 * 101|name, pass -- login to server
				 * 102| 		   --ask server for user list
				 * 103|from, to, message -- talk to other user
				 * 104|from, to, file.getName()
				 */
				
				
				/* s2c
				 * 104@from@filename@sendtime;
				 * 103@from@content@sendtime;
				 * 102@server@tom,jack,lucy,lily@sendtime
				 * 
				 */
				
				try {
					
				
					for (int i = 0; i < myMessage.size(); i++) {
						Message m = myMessage.get(i);
						if (m.getMessageType().equals("104")) {
							dos.writeUTF(m.getMessageType() + "@" + m.getFrom() + "@" + m.getContent() + "@" + m.getSendTime() + ";");
							dos.flush();
//							buffer.append(m.getMessageType() + "@" + m.getFrom() + "@" + m.getContent() + "@" + m.getSendTime() + ";");

							String string = reader.readLine();
							System.out.println(name + " receive " + string);
							
							if (string.startsWith("ES")) {
								System.out.println("ready to start FileSendThread");
								new FileSendThread(StartServer.fileSocket.accept(), m.getContent()).start();
							}else {
								//if user refuse to accept file, the file should be delete
							
								continue;
							}
	//						filebuffer.append(m.getMessageType()+"@"+m.getFrom()+"@"+m.getContent()+"@"+m.getSendTime()+";");
						}else{
							buffer.append(m.getMessageType() + "@" + m.getFrom() + "@" + m.getContent() + "@" + m.getSendTime() + ";");

						}
						
						
						
					}
				} catch (Exception e) {
				}
				
				if (buffer.length() > 0) {
					String strMessage = buffer.substring(0, buffer.length()-1);
					
					System.out.println("ready to send");
					//send message to client
					try {
						dos.writeUTF(strMessage);
						System.out.println(strMessage + ";");
						dos.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				} 
				
				//after all message is sent, delete message from message list
				MessageDAO.removeMyMessage(myMessage);
				

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			} else {
				System.out.println("null message list");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			
			
		}
	
	}
}
