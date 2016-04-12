
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
			
			DataInputStream dis = new DataInputStream(datesocket.getInputStream());
			DataOutputStream dos = new DataOutputStream(datesocket.getOutputStream());

			
			RandomAccessFile inFile = new RandomAccessFile(filepath + (filepath.endsWith("\\") ? "" : "\\") + filename, "r");
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
			

			dos.close();
			dis.close();
			datesocket.close();
			System.out.println(filename + "server success send");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
