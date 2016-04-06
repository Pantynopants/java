import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class SendMessageThread extends Thread{

	private Socket socket = null;
	
	private String name = null;
	
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	
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
				dos = new DataOutputStream(socket.getOutputStream());
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
				 * 
				 * 101|name, pass -- login to server
				 * 102| 		   --ask server for user list
				 * 103|from, to, message -- talk to other user
				 * 104|from, to, file.getName()
				 */
				
				
				/* 
				 * 103@from@content@sendtime;
				 * 103@from@content@sendtime;
				 * 102@server@tom,jack,lucy,lily@sendtime
				 * 
				 */
				
				try {
					
				
					for (int i = 0; i < myMessage.size(); i++) {
						Message m = myMessage.get(i);
						if (m.getMessageType().equals("104")) {
							dos.writeUTF(m.getMessageType() + "@" + m.getFrom() + "@" + m.getContent() + "@" + m.getSendTime() + ";");
							System.out.println("即将接收文件是否接受消息");
							String string = dis.readUTF();
							System.out.println("已接受消息");
							if (string.equals("OK")) {
								System.out.println("即将启动发送线程");
								new FileSendThread(StartServer.fileSocket.accept(), m.getContent()).start();
							}else {
								//用户拒绝接收文件，将服务器文件删除
								
								continue;
							}
	//						filebuffer.append(m.getMessageType()+"@"+m.getFrom()+"@"+m.getContent()+"@"+m.getSendTime()+";");
						}else {
							buffer.append(m.getMessageType() + "@" + m.getFrom() + "@" + m.getContent() + "@" + m.getSendTime() + ";");
						}
						
						
					}
				} catch (Exception e) {
					// TODO: handle exception
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
