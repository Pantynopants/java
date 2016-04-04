
import javax.swing.*;

import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class LoginFrame extends JFrame{

	private JButton btnLogin = new JButton("login");
	private JTextField username;
    private JPasswordField password;
	
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	
	public LoginFrame() {
		btnLogin.addActionListener(new ActionListener() {
			
			

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
				
				try {
					
					Socket s = new Socket("localhost", 4001);
					
					dis = new DataInputStream(s.getInputStream());
					dos = new DataOutputStream(s.getOutputStream());
					
					dos.writeUTF("101|" + username.getText() + password.getPassword());
					dos.flush();
					
					//get feed back from server
					String feedback = dis.readUTF();
					
					if (feedback.equals("success")) {
						
						//show mainframe, hide login frame
						MainFrame main = new MainFrame(s, username.getText());
						LoginFrame.this.setVisible(false);
					}else {
						JOptionPane.showMessageDialog(LoginFrame.this, feedback);
					}
					
				} catch (UnknownHostException e2) {
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	
	public static void loginWindow() {
		
	}

}
