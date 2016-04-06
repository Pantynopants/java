//import java.io.*;
//import java.net.*;
//import java.util.*;
//
//
//public class ReceiveMessageThread extends Thread{
//
//	public Socket s = null;
//	
//	private DataInputStream dis = null;
//	private DataOutputStream dos = null;
//	
//	private List<Message> myMessage = null;
//
//	private String name;
//	
//	public ReceiveMessageThread(Socket s) {
//		this.s = s;
//		
//	}
//	
//	
//	
//	@Override
//	public void run() {
//		
//		try {
//			dos = new DataOutputStream(s.getOutputStream());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		while (true) {
//			//get message
//			myMessage = MessageDAO.getMyMessage(name);
//			
//			StringBuffer buffer = new StringBuffer();
//			
//			for (int i = 0; i < myMessage.size(); i++) {
//				Message m = myMessage.get(i);
//				buffer.append(m.getMessageType() + "@" + m.getFrom() + "@" + m.getContent() + "@" + m.getSendTime() + ";");
//				
//			}
//			
//			if (buffer.length() > 0) {
//				String strMessage = buffer.substring(0, buffer.length()-1);
//				
//				//send message to client
//				try {
//					dos.writeUTF(strMessage);
//					dos.flush();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				
//			} 
//			
//			//after all message is sent, delete message from message list
//			MessageDAO.removeMyMessage(myMessage);
//			
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//}
