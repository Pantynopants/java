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
			
//			fos = new FileOutputStream(filepath + (filepath.endsWith("\\") ? "" : "\\") + filename);
//			byte[] buffer = new byte[20480]; // 接收缓冲 20k
//			int num = 0; // 接收一次读取的字节数
//			int count = 0;
//			do {
//				num = dis.read(buffer);
//				if (num != (-1)) {
//					// 接收：
//					count += num;
//					fos.write(buffer, 0, num);
//					fos.flush();
//				}
//			} while (num != (-1));
			
			
			
			fos.close();
			dos.close();
			dis.close();
			System.out.println("服务器已成功接收文件：" + filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
