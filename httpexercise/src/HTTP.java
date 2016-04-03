import java.io.*;
import java.util.*;
import java.net.*;


public class HTTP {

	private static int SERVER_PORT = 8888;
	private static String SERVER_IP = "127.0.0.1";
	
	public static void main(String[] args) {
		ServerSocket ssServerSocket = null;
		
		try {
			ssServerSocket = new ServerSocket(SERVER_PORT);
		} catch (IOException e) {
			System.out.println("have not build a serversocket");
			e.printStackTrace();
		}
		
		while (true) {
			try {
				Socket socket = ssServerSocket.accept();
				HTTPserver s = new HTTPserver(socket);
				
				s.start();
			} catch (Exception e) {
				System.out.println("server not run");
				e.printStackTrace();
			}
		}
	}

}
