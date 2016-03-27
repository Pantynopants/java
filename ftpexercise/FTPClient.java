package ftpexercise;

import java.util.*;
import java.io.*;
import java.net.*;

import java.net.ServerSocket;

public class FTPClient{
	
	private static int PORT = 8021;
	private static String HOST = "127.0.0.1";
	
	private static boolean connected = false;
	
	public FTPClient() {
		// TODO Auto-generated constructor stub
	}
	
	public FTPClient(Socket temp) {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
//		try {
//			ServerSocket serverSocket = new ServerSocket(PORT);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		while (true) {
		
			/*
			 * 1
			 * 
			 * 
			 * 
			 * */
			
			Socket newSocket = null;
			try {
				newSocket = new Socket(HOST,PORT);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 
			if (newSocket == null) {
				connected = false;
			} else {
				System.out.println("connected!!!!");
				connected = true;
			}
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(newSocket.getInputStream())); 
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(newSocket.getOutputStream()));
			
			DataInputStream dis = new DataInputStream(newSocket.getInputStream());
			DataOutputStream dos = new DataOutputStream(newSocket.getOutputStream());
			//String msg = dis.readUTF();
			//System.out.println("server say:" + msg);
			
			Scanner scanner = new Scanner(System.in);
			String clientSent = scanner.next();
	            
			dos.writeUTF(clientSent);
			dos.flush();
	
			
		
			
			
			
			
			String response = reader.readLine();
			System.out.println(response);
			
			String h1 = response.split(",")[4];
			System.out.println(h1);
			String h2 = response.split(",")[5];
			String h3 = h2.substring(0, h2.length() - 1);
			System.out.println(h3);
			
			Integer highPort = null;
			Integer lowPort = null;
			try {
				highPort = Integer.parseInt(h1);
				lowPort = Integer.parseInt(h3);
			} catch (NumberFormatException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
			int hp = 0,lp = 0;
			hp = highPort.intValue();
			lp = lowPort.intValue();
			System.out.println(hp);
			System.out.println(lp);
			int newport = hp*256 + lp;
			//String msg = dis.readUTF();
			System.out.println("port is");
			System.out.println(newport);
			
			
			/*
			 * 5
			 * 
			 * 
			 * 
			 * */
			
			
			Socket newContorlSocket = null;
			try {
				newContorlSocket = new Socket(HOST,newport);
				System.out.println("localport"+newContorlSocket.getLocalPort());
				System.out.println("remote port"+newContorlSocket.getPort());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
					
			//String command;
	        //String response;
		}
//        while (true) {
//        	command = scanner.nextLine();
//	        
//	        StringTokenizer cutter = new StringTokenizer(command);
//	        String commandHeader = cutter.nextToken();
//	        String commandValue = cutter.hasMoreTokens() ? cutter.nextToken() : ""; 
//			switch (commandHeader) {
//				case "USER":
//					//System.out.println(commandValue);
//					writer.println(cutter);
//					writer.flush();
//					break;
//					
//				case "PASS":
//					System.out.println(commandValue);
//					break;
//					
//				case "SIZE":
//					break;
//					
//				case "CWD":
//					break;
//					
//				case "PASV":
//					break;
//					
//				case "PORT":
//					break;
//					
//				case "PETR":
//					break;
//					
//				case "STOR":
//					break;
//				
//				case "REST":
//					break;
//					
//				
//				case "quit":
//					newSocket.close();
//					connected = false;
//					break;
//					
//				default:
//					break;
//			}
//		}
	        
		
		
	}
	
	public String[] fileList() {
		//注意字符流与字节流的不同
		String[] currentFileList = null;
		return currentFileList;
	}
	
	public boolean isConnected() {
		return connected;
	}
	

	
	
}
