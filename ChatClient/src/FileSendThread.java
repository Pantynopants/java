import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.Socket;

public class FileSendThread extends Thread {
	private File file;
	private Socket socket;

	public FileSendThread(File file, Socket socket) {
		this.file = file;
		this.socket = socket;
	}

	public void run() {
		try {
			System.out.println("ready to send");
			if (!file.exists()) {
				System.out.println("file not exist");
				return;
			}
			FileInputStream fis = null;
			Socket datesocket = new Socket(socket.getInetAddress(), 4002);
			DataInputStream dis = new DataInputStream(datesocket.getInputStream());
			DataOutputStream dos = new DataOutputStream(datesocket.getOutputStream());
			String str = dis.readUTF();
			System.out.println("接收到" + str);
			if (str.equals("offline")) {
				
				RandomAccessFile inFile = new RandomAccessFile(file.getPath(), "r");
	            //OutputStream outSocket = new dataSocket_retr.getOutputStream();
	            byte byteBuffer[] = new byte[1024];  
	            int amount_retr;  
	            
	            
	            try{  
	                while((amount_retr = inFile.read(byteBuffer, 0, byteBuffer.length)) != -1){
	                	dos.write(byteBuffer, 0, amount_retr);
	                } 
	                
	                dos.close();
	                System.out.println("client end transfer");
	                inFile.close();  
	                
	            }catch(IOException e){  
	            	e.printStackTrace(); 
	            }
	            
	            
//				fis = new FileInputStream(file);
//				/* 开始正式发送数据: */
//				byte[] buffer = new byte[20480];
//				int num = 0; // 发送一次读取的字节数
//				int count = 0;
//				do {
//					num = fis.read(buffer);
//					if (num != (-1)) {
//						// 发送：
//
//						count += num;
//						dos.write(buffer, 0, num);
//						dos.flush();
//						System.out.println("已发送：" + count);
//					}
//				} while (num != (-1));
				
				
				
				fis.close();
				dos.close();
				dis.close();
				//datesocket.close();
				System.out.println(file.getName()+"文件发送完毕");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
