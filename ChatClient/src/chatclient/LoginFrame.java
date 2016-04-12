package chatclient;
  
import javax.swing.*;

import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class LoginFrame extends javax.swing.JFrame {

    /**
     * Creates new form LoginFrame
     */
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    public JButton jButton1 = new JButton("login");
    public JTextField jTextField1;
    public JPasswordField jPasswordField1;
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField1.setText("tom");

        jPasswordField1.setText("tom");

        jButton1.setText("login");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addComponent(jButton1)
                .addContainerGap(175, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(105, Short.MAX_VALUE))
        );

        jTextField1.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>                        
/**/


	
	
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	
	public LoginFrame() {
            //TODO 
            
            initComponents();
            System.out.println("new login frame");
            LoginFrame.this.setVisible(true);
            jButton1.addActionListener(new ActionListener() {
			
			

			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				 * make a communication protocol between server and client
				 * 
				 * 101|name, pass -- login to server
				 * 102| 		   --ask server for user list
				 * 103|from, to, message -- talk to other user
				 * 
				 */
				try {
					
					Socket s = new Socket("localhost", 4001);
					
					dis = new DataInputStream(s.getInputStream());
					dos = new DataOutputStream(s.getOutputStream());
					System.out.println(jTextField1.getText() + jPasswordField1.getText());
					
					dos.writeUTF("101|" + jTextField1.getText() +  "," + jPasswordField1.getText());
					dos.flush();
					
					//get feed back from server
					String feedback = dis.readUTF();
					System.out.println(feedback);
					if (feedback.equals("success")) {
						
						//show mainframe, hide login frame
						MainFrame main = new MainFrame(s, jTextField1.getText());
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
	
	

}

/*
    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration                   

*/