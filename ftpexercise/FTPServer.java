package ftpexercise;



import java.util.*;
import java.io.*;
import java.net.*;

public class FTPServer extends Thread{
	
	public static String root = null;//当前服务器的根目录  
    private String currentDir = "/";//当前服务器上的工作目录  
     
    private BufferedReader reader = null;//字符输入流  
    private PrintWriter writer = null;//字符输出流  
    private String clientIP = null;//客户端IP地址  
    private int clientPort = 0;//客户端端口  
    private int port = -1;//客户端端口  
    String user;  
    
	private static final int enteringPassiveMode = 227;
	private static final int loggedOn = 230;
	private static final int CWDSuccessful = 250;
	
	private Socket FTPs = null;
	private boolean isLogedin = false;
	
	private int port_high = 0;
	private int port_low = 0;
	
	public FTPServer() {
		// TODO Auto-generated constructor stub
		
	}
	
	public FTPServer(Socket temp) {
		// TODO Auto-generated constructor stub
		FTPs = temp;
		clientPort = temp.getPort();
		clientIP = temp.getInetAddress().getHostAddress();
		System.out.println("remote port"+temp.getPort());
		System.out.println("local port"+temp.getLocalPort());
	}

	/*
	 * 3
	 * client login successful and server requestPort
	 * 
	 * 
	 * */

	public ServerSocket requestPort() {
	
		
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
	 * server returnPort to client
	 * 
	 * 
	 * */

	
	public boolean returnPort(ServerSocket serverSocket) {
		InetAddress inetAddress = null;
		try {
			inetAddress =  InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		try {
			writer = new PrintWriter(new OutputStreamWriter(FTPs.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		};
		writer.println("227 entering passive mode (" + inetAddress.getHostAddress().replace(".", ",") + "," + port_high + "," + port_low + ")");
		//to 5
		writer.flush();
			
		Socket newTransferSocket;
		try {
			System.out.println("wait for new client transfer request");
			newTransferSocket = serverSocket.accept();
			System.out.println("transfer.localport:"+newTransferSocket.getLocalPort());
			System.out.println("transfer.port:"+newTransferSocket.getPort());
			System.out.println("FTPserver new thread for next client");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		DataInputStream dis = null;
		DataOutputStream dos = null ;
		
		try {
			dis = new DataInputStream(newTransferSocket.getInputStream());
			dos = new DataOutputStream(newTransferSocket.getOutputStream());
			String msg = dis.readUTF();
			System.out.println("in transfer socket, client say:" + msg);
			return true;
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.err.println("input error");
			e1.printStackTrace();
		}
		
		
		
		return false;
	}
	
	
	public void selectMenu(String command) {
		switch (command) {
		case "USER":
			
			break;

		default:
			break;
		}
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean flag = true;
		while (true) {
			DataInputStream dis = null;
			DataOutputStream dos = null ;
			
			try {
				//dis = new DataInputStream(FTPs.getInputStream());
				//dos = new DataOutputStream(FTPs.getOutputStream());
				System.out.println("wait for control socket's msg");
				//String msg = dis.readUTF();
				reader = new BufferedReader(new InputStreamReader(FTPs.getInputStream())); 
				writer = new PrintWriter(new OutputStreamWriter(FTPs.getOutputStream()));
				String msg = reader.readLine();
				System.out.println("in control socket, client say:" + msg);
				selectMenu(msg);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.err.println("input error");
				e1.printStackTrace();
			}
			
				
			//ServerSocket serverSocket = requestPort();
			
			//flag = returnPort(serverSocket);
		}
		
		
		
		
		
	}
	
}

