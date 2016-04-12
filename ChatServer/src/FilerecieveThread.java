import java.io.*;
import java.net.Socket;

public class FilerecieveThread extends Thread {
	private String filepath = "G:\\ftp\\";
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	private String filename;
	private FileOutputStream fos = null;

	public FilerecieveThread(Socket socket, String filename) {
		try {
			this.dis = new DataInputStream(socket.getInputStream());
			this.dos = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.filename = filename;
	}

	public void run() {
		try {
			dos.writeUTF("offline");
			dos.flush();
			
			byte[] inputByte = new byte[1024];
            int length = 0;
//            FileOutputStream fout = null;
//            if (!currentDir.endsWith("\\")) {
//				currentDir += "\\";
//			}
            File fileInServer = new File(filepath + filename);
            if (!fileInServer.exists()) {
				fileInServer.createNewFile();
			}
            
            fos = new FileOutputStream(fileInServer, true);
            System.out.println("Server receiving");
            boolean fileFlag = true;
			while(fileFlag) {
            	if(dis == null || (length = dis.read(inputByte, 0, inputByte.length)) == -1) {
            		fileFlag = false;
                    break;
                }
            	
             
                System.out.println(length);
                fos.write(inputByte, 0, length);
                fos.flush();
            }
			
			fos.close();
			dos.close();
			dis.close();
			System.out.println("server success receive£º" + filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
