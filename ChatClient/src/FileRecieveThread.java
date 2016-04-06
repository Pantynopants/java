import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.Socket;

import javax.swing.JFileChooser;

public class FileRecieveThread extends Thread {
	private Socket socket;
	private String filename;
	private MainFrame mainFrame;
	private FileOutputStream fos = null;
	private DataInputStream dis = null;

	public FileRecieveThread(Socket socket, String filename, MainFrame mainFrame) {
		this.socket = socket;
		this.filename = filename;
		this.mainFrame = mainFrame;
	}

	public void run() {
		try {
			JFileChooser addChooser = new JFileChooser();
			addChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			// �÷�������Ϊtrue����ѡ�����ļ�
			addChooser.setMultiSelectionEnabled(false);
			int returnval = addChooser.showOpenDialog(mainFrame);
			if (returnval == JFileChooser.APPROVE_OPTION) {
				Socket filesocket = new Socket(this.socket.getInetAddress(), 4002);
				System.out.println("�ѽ����ļ���Socket");
				dis = new DataInputStream(filesocket.getInputStream());
				File file = addChooser.getSelectedFile();
				File fileSave = new File(file.getAbsolutePath() + (file.getAbsolutePath().endsWith("\\") ? "" : "\\") + filename);
				
				if (!fileSave.exists()) {
					fileSave.createNewFile();
				}
				
				fos = new FileOutputStream(fileSave.getPath());
				
				
//				byte[] buffer = new byte[20480]; // ���ջ��� 20k
//				int num = 0; // ����һ�ζ�ȡ���ֽ���
//				int count = 0;
//
//				do {
//					num = dis.read(buffer);
//					if (num != (-1)) {
//						// ���գ�
//						count += num;
//						fos.write(buffer, 0, num);
//						fos.flush();
//					}
//				} while (num != (-1));
//				
				byte[] inputByte = new byte[1024];
                int length = 0;
//                FileOutputStream fout = null;
//                if (!currentDir.endsWith("\\")) {
//					currentDir += "\\";
//				}
                //fout = new FileOutputStream(new File(currentDir + uploadFile), true);
                System.out.println("Receiving...");
                boolean fileFlag = true;
				while(fileFlag ) {
                	if(dis == null || (length = dis.read(inputByte, 0, inputByte.length)) == -1) {
                		fileFlag = false;
                        break;
                    }
                	
                 
                    System.out.println(length);
                    fos.write(inputByte, 0, length);
                    fos.flush();
                    System.out.println("writing...");
                }
				
				fos.close();
				dis.close();
				//filesocket.close();
				System.out.println("�ѳɹ������ļ���" + filename);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
