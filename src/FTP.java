
import java.net.ServerSocket;
import java.util.*;
import java.io.*;
import java.net.*;
import java.math.*;

public class FTP {

	private static int PORT = 8021;
	
	public static void main(String[] args) throws IOException {
		FTPServer.root = "G:\\ftp\\";  
		
		
		ServerSocket s = null;
		boolean flag = true;
		
			try {
				s = new ServerSocket(PORT);
			} catch (IOException e) {
				e.printStackTrace();
			}

			/*
			 * 2
			 * 
			 * 
			 * 
			 * */
			
		while (flag) {		
			try {
				System.out.println("wait for new client connect request");
				Socket client = s.accept();
				FTPServer threadServer = new FTPServer(client);
				threadServer.start();
				System.out.println("FTP new thread for next client");
			} catch (Exception e) {
				
				System.err.println(e.getMessage());
				for (StackTraceElement ste : e.getStackTrace()) {
					System.err.println(ste.toString());
				}
				flag = false;
			}
		}
		
		s.close();
	}

}

