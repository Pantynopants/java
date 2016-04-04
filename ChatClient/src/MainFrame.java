import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class MainFrame extends JFrame{

	public static JButton btnSend = new JButton("login");
	public static JTextField messageContent;
	public static JTextArea messageFrom;
	public static JComboBox<String> comboBox;
    
	private Socket s = null;
	
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	
	protected String name;
	
	public MainFrame() {
		
	}
	public MainFrame(Socket s, String text) {
		
		ReceiveMessageThread userReceiveMessageThread = new ReceiveMessageThread(s);
		
		try {
			//dis = new DataInputStream(s.getInputStream());
			dos = new DataOutputStream(MainFrame.this.s.getOutputStream());
			
			dos.writeUTF("102|");
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		btnSend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				 * make a communication protocol between server and client
				 * 
				 * 101|name, pass -- login to server
				 * 102| 		   --ask server for user list
				 * 102|from, to, message -- talk to other user
				 * 
				 */
				
				String from = MainFrame.this.name;
				String to = MainFrame.this.comboBox.getSelectedItem().toString();
				String content = MainFrame.this.messageContent.getText();
				
				try {
					//dis = new DataInputStream(s.getInputStream());
					dos = new DataOutputStream(MainFrame.this.s.getOutputStream());
					
					dos.writeUTF("103|" + from + "," + to + "," + content);
					dos.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
 	}

}
