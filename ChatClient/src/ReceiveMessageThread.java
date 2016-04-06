import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.JOptionPane;


public class ReceiveMessageThread extends Thread{

	public Socket s = null;
	
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	
//	private List<Message> myMessage = null;

	private String name;
	private MainFrame myMainFrame;
	
	
	public ReceiveMessageThread(Socket s, MainFrame myMainFrame, String name) {
		this.s = s;
		this.myMainFrame = myMainFrame;
		this.name = name;
	}
	
	
	
	@Override
	public void run() {
		
		
		
		while (true) {
			/* 
			 * 103@from@content@sendtime;
			 * 103@from@content@sendtime;
			 * 102@server@tom,jack,lucy,lily@sendtime
			 * 
			 */
			System.out.println(name + " ready to receive");
			try {
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String message = null;
			
			
			try {
				message = dis.readUTF();
				System.out.println(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (message.startsWith("PORT")) {
				MainFrame.PortOfFileTrans = Integer.parseInt(message.substring(5));
				continue;
			}
			String[] m1 = message.split(";");
			
			for (int i = 0; i < m1.length; i++) {
				String[] m2 = m1[i].split("@");
				
				if (m2[0].equals("102")) {
					String[] m3 = m2[2].split(",");
					
					//update comboBox
					//remove all and add new friends list
					
					myMainFrame.comboBox.removeAllItems();
					
					for (int j = 0; j < m3.length; j++) {
						System.out.println(m3[j]);
						myMainFrame.comboBox.addItem(m3[j]);
						
					}
				}else if (m2[0].equals("103")) {
					myMainFrame.messageFrom.append("(" + m2[3] + ") " + "from " + m2[1] + " :\r\n" + m2[2] + "\r\n");
				}else if (m2[0].equals("104")) {
					int op = JOptionPane.showConfirmDialog(null,"对方正在发送文件，确定接收吗?","文件传输",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					
					if(op == JOptionPane.YES_OPTION){
						try {
							dos.writeUTF("OK");
							dos.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						new FileRecieveThread(s, m2[2], myMainFrame).start();
					}
					else{
						try {
							dos.writeUTF("NO");
							dos.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}
						
					}						
			}
			}
			
		}
	}
}
