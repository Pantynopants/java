package ftpexercise;



import java.util.*;
import java.io.*;
import java.net.*;

public class FTPServer extends Thread{
	
	private static final int enteringPassiveMode = 227;
	private static final int loggedOn = 230;
	private static final int CWDSuccessful = 250;
	
	private static Socket FTPs = null;
	private boolean isLogedin = false;
	
	private int port_high = 0;
	private int port_low = 0;
	
	public FTPServer() {
		// TODO Auto-generated constructor stub
		
	}
	
	public FTPServer(Socket temp) {
		// TODO Auto-generated constructor stub
		FTPs = temp;
		System.out.println("remote port"+temp.getPort());
		System.out.println("local port"+temp.getLocalPort());
	}

	/*
	 * 3
	 * 
	 * 
	 * 
	 * */

	public synchronized ServerSocket requestPort() {
	
		
		int conectPort = 0;
		
		boolean flag = true;
		ServerSocket serverSocket = null;
		Random generator = new Random();
		
		
			while (flag) {
				port_high = 1 + generator.nextInt(20);
				port_low = 100 + generator.nextInt(1000);
				try {
					
					conectPort = port_high*256 + port_low;
					serverSocket = new ServerSocket(conectPort);
					
//					dos.writeUTF("new port is" + conectPort);
//					dos.flush();
					flag = false;
					break;
					
				} catch (IOException e) {
					// TODO: handle exception
					System.err.println("requst port now");
					e.printStackTrace();
					
				}
		}
			return serverSocket;
	}
	
	/*
	 * 4
	 * 
	 * 
	 * 
	 * */

	
	public synchronized boolean returnPort(ServerSocket serverSocket) {
		InetAddress inetAddress = null;
		try {
			inetAddress =  InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new OutputStreamWriter(FTPs.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		};
		pw.println("227 entering passive mode (" + inetAddress.getHostAddress().replace(".", ",") + "," + port_high + "," + port_low + ")");
		
		pw.flush();
		
		
		
		
		try {
			Socket commandClient = serverSocket.accept();
			//System.out.println(commandClient.getLocalPort());
			//System.out.println(commandClient.getPort());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		DataInputStream dis = null;
		DataOutputStream dos = null ;
		
		try {
			dis = new DataInputStream(FTPs.getInputStream());
			dos = new DataOutputStream(FTPs.getOutputStream());
			String msg = dis.readUTF();
			System.out.println("client say:" + msg);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.err.println("input error");
			e1.printStackTrace();
		}
		
			
		ServerSocket serverSocket = requestPort();
		
		returnPort(serverSocket);
		
		
		
		
	}
	
}

