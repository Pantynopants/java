import java.io.*;
import java.net.*;
import java.util.*;


public class ReceiveMessageThread extends Thread{

	public Socket s = null;
	
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	
	private List<Message> myMessage = null;

	private String name;
	
	public ReceiveMessageThread(Socket s) {
		this.s = s;
		
	}
	
	
	
	@Override
	public void run() {
		
		try {
			dos = new DataOutputStream(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while (true) {
			/* 
			 * 103@from@content@sendtime;
			 * 103@from@content@sendtime;
			 * 102@server@tom,jack,lucy,lily@sendtime
			 * 
			 */
			
			String message = null;
			try {
				message = dis.readUTF();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String[] m1 = message.split(";");
			
			for (int i = 0; i < m1.length; i++) {
				String[] m2 = m1[i].split("@");
				
				if (m2[0].equals("102")) {
					String[] m3 = m2[2].split(",");
					
					//update comboBox
					//remove all and add new
					
					MainFrame.comboBox.removeAllItems();
					
					for (int j = 0; j < m3.length; j++) {
						MainFrame.comboBox.addItem(m3[j]);
						
					}
				}else if (m2[0].equals("103")) {
					MainFrame.messageFrom.append(m2[3] + " " + m2[1] + " send you:" + m2[2] + "\r\n");
				}
			}
			
		}
	}
}
