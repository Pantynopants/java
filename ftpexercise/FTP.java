package ftpexercise;

import java.net.ServerSocket;
import java.util.*;
import java.io.*;
import java.net.*;
import java.math.*;

public class FTP {

	private static int PORT = 8021;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("welcome to ftp server");
		
		ServerSocket s = null;
		boolean flag = true;
		
			try {
				s = new ServerSocket(PORT);
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
				
				Socket client = s.accept();
				FTPServer threadServer = new FTPServer(client);
				threadServer.start();
				
			} catch (Exception e) {
				
				// TODO: handle exception
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

