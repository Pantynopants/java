package chatclient;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;


public class ReceiveMessageThread extends Thread{

	public Socket s = null;
	
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	private BufferedReader reader = null;  
    private PrintWriter writer = null;
    
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
			 * 104@from@filename@sendtime;
			 * 103@from@content@sendtime;
			 * 102@server@tom,jack,lucy,lily@sendtime
			 * 
			 */
			System.out.println(name + " ready to receive");
			try {
				writer = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
				reader = new BufferedReader(new InputStreamReader(s.getInputStream())); 

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
//			if (message.startsWith("PORT")) {
//				MainFrame.PortOfFileTrans = Integer.parseInt(message.substring(5));
//				continue;
//			}
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
					Document receiveDoc = myMainFrame.messageContent.getDocument();
					
                    try {
//                        receiveDoc.insertString(receiveDoc.getLength(),  "(" +  m2[3] + ") " + m2[1] + ": \r\n" , Userinfo.header);
//                        receiveDoc.insertString(receiveDoc.getLength(), m2[2] + "\r\n", Userinfo.body);
                    	 String str = "(" +  m2[3] + ") ";		
                    	receiveDoc.insertString(receiveDoc.getLength(), str, MainFrame.timeattrset);
						str = m2[1]+" send you:\r\n"+m2[2]+"\r\n";						
						new ConvertExpression().recievemessage(myMainFrame, str);
						
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                    
//					myMainFrame.messageFrom.append("(" + m2[3] + ") " + "from " + m2[1] + " :\r\n" + m2[2] + "\r\n");
				
				}else if (m2[0].equals("104")) {
					int op = JOptionPane.showConfirmDialog(null,"ready toreceive a file named:\r\n" + m2[2] + " ?","File Transfer",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					
					if(op == JOptionPane.YES_OPTION){
						writer.flush();
						writer.println("YES");
						writer.flush();
						System.out.println(name + " send YES");
						new FileRecieveThread(s, m2[2], m2[1], myMainFrame).start();
					}
					else{
						writer.println("NO");
						writer.flush();
						
					}						
				}
			}
			
		}
	}
}
