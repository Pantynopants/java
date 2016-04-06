
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.Socket;

public class FileSendThread extends Thread {
	private String filepath = "G:\\ftp\\";
	private Socket datesocket = null;
	private String filename = null;

	public FileSendThread(Socket socket, String filename) {
		this.filename = filename;
		this.datesocket = socket;
	}

	public void run() {
		try {
			System.out.println("已启动发送线程");
			FileInputStream fis = null;
			DataInputStream dis = new DataInputStream(datesocket.getInputStream());
			DataOutputStream dos = new DataOutputStream(datesocket.getOutputStream());

			fis = new FileInputStream(filepath + (filepath.endsWith("\\") ? "" : "\\") + filename);
			
			RandomAccessFile inFile = new RandomAccessFile(filepath + (filepath.endsWith("\\") ? "" : "\\") + filename, "r");
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
			
//			/* 开始正式发送数据: */
//			byte[] buffer = new byte[20480];
//			int num = 0; // 发送一次读取的字节数
//			int count = 0;
//			do {
//				num = fis.read(buffer);
//				if (num != (-1)) {
//					// 发送：
//
//					count += num;
//					datedos.write(buffer, 0, num);
//					datedos.flush();
//					System.out.println("已发送：" + count);
//
//				}
//			} while (num != (-1));
			fis.close();
			dos.close();
			dis.close();
			datesocket.close();
			System.out.println(filename + "从服务器文件发送完毕");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
