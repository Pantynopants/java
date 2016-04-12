package chatclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.Socket;

import javax.swing.JFileChooser;

public class FileRecieveThread extends Thread {
	
	private Socket socket;
	private Socket filesocket;
	private String filename;
	private String name;
	
	private MainFrame mainFrame;
	
	private FileOutputStream fos = null;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;

	public FileRecieveThread(Socket socket, String filename, String name, MainFrame mainFrame) {
		
		this.socket = socket;
		this.filename = filename;
		this.name = name;
		this.mainFrame = mainFrame;
	}

	public void run() {
		try {
			JFileChooser addChooser = new JFileChooser();
			addChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			addChooser.setMultiSelectionEnabled(false);
			int returnval = addChooser.showOpenDialog(mainFrame);
			
			if (returnval == JFileChooser.APPROVE_OPTION) {
				
				Socket filesocket = new Socket(this.socket.getInetAddress(), 4002);
				
				dis = new DataInputStream(filesocket.getInputStream());
				
				File file = addChooser.getSelectedFile();
				File fileSave = new File(file.getAbsolutePath() + (file.getAbsolutePath().endsWith("\\") ? "" : "\\") + filename);
				
				if (!fileSave.exists()) {
					fileSave.createNewFile();
				}
				
				fos = new FileOutputStream(fileSave.getPath());
				

//				
				byte[] inputByte = new byte[1024];
                int length = 0;
//                FileOutputStream fout = null;
//                if (!currentDir.endsWith("\\")) {
//					currentDir += "\\";
//				}
                //fout = new FileOutputStream(new File(currentDir + uploadFile), true);
                System.out.println("Client receiving");
                
//                dis = new DataInputStream(filesocket.getInputStream());
//				dos = new DataOutputStream(filesocket.getOutputStream());
//				dos.writeUTF("YES");
//				dos.flush();
				
				
				
                boolean fileFlag = true;
				while(fileFlag ) {
                	if(dis == null || (length = dis.read(inputByte, 0, inputByte.length)) == -1) {
                		fileFlag = false;
                        break;
                    }
                	
                 
                    System.out.println(length);
                    fos.write(inputByte, 0, length);
                    fos.flush();
                }
				
				fos.close();
				dis.close();
				//filesocket.close();
				System.out.println("success receive file" + filename);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
