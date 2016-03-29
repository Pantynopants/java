package ftpexercise;



import java.util.*;
import java.io.*;
import java.net.*;

public class FTPServer extends Thread{
	
	public static String root = "G:\\ftp\\";//当前服务器的根目录  
    private String currentDir = root;//当前服务器上的工作目录  
     
    private BufferedReader reader = null;//命令socket使用的 字符输入流  
    private PrintWriter writer = null;//命令socket使用的 字符输出流  
    
//    private DataInputStream dis = null;//文件输入流
//    private DataOutputStream dos = null;//文件输出流
	
    private Socket newTransferSocket;
    private Socket clientCommandSocket = null;//客户端命令socket
    private ServerSocket serverSocket = null;//服务端传输 server socket
    
    private String clientIP = null;//客户端IP地址  
    private int clientPort = 0;//客户端端口  
    private int port = -1;//客户端端口  
    String user;  
    
	private static final int enteringPassiveMode = 227;
	private static final int loggedOn = 230;
	private static final int CWDSuccessful = 250;
	
	
	private boolean isLogedin = false;
	private boolean isTransport = false;
	
	private int port_high = 0;
	private int port_low = 0;
	
	private boolean flag = true;
	
	public FTPServer() {
		
	}
	
	public FTPServer(Socket temp) {
		System.out.println("welcome to ftp server");
		clientCommandSocket = temp;
		clientPort = temp.getPort();
		clientIP = temp.getInetAddress().getHostAddress();
		System.out.println("remote port"+temp.getPort());
		System.out.println("local port"+temp.getLocalPort());
		System.out.println("clientIP" + clientIP);
		
		try {
			writer = new PrintWriter(new OutputStreamWriter(clientCommandSocket.getOutputStream()));
			writer.println("login successful");
			writer.flush();
			reader = new BufferedReader(new InputStreamReader(clientCommandSocket.getInputStream())); 
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}

	/*
	 * 3
	 * client login successful and server requestPort
	 * 
	 * 
	 * */

	public ServerSocket requestPort() {
	
		
		int conectPort = 0;
		
		boolean requestflag = true;
		ServerSocket serverSocket = null;
		Random generator = new Random();
		
		
			while (requestflag) {
				port_high = 1 + generator.nextInt(20);
				port_low = 100 + generator.nextInt(1000);
				try {
					
					conectPort = port_high*256 + port_low;
					serverSocket = new ServerSocket(conectPort);
					
//					dos.writeUTF("new port is" + conectPort);
//					dos.flush();
					requestflag = false;
					break;
					
				} catch (IOException e) {
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
		if (serverSocket == null) {
			return false;
		}
		InetAddress inetAddress = null;
		try {
			inetAddress =  InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
		
			e.printStackTrace();
		}
		
		
		try {
			writer = new PrintWriter(new OutputStreamWriter(clientCommandSocket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		};
		writer.println("227 entering passive mode (" + inetAddress.getHostAddress().replace(".", ",") + "," + port_high + "," + port_low + ")");
		//to 5
		writer.flush();
			
		
		try {
			System.out.println("wait for new client transfer request");
			newTransferSocket = serverSocket.accept();
			System.out.println("transfer.localport:"+newTransferSocket.getLocalPort());
			System.out.println("transfer.port:"+newTransferSocket.getPort());
			System.out.println("FTPserver new thread for next client");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
	    DataInputStream dis = null;//文件输入流
	    DataOutputStream dos = null;//文件输出流
		
//		try {
//			dis = new DataInputStream(newTransferSocket.getInputStream());
//			dos = new DataOutputStream(newTransferSocket.getOutputStream());
//			return true;
//			
//		} catch (IOException e1) {
//			System.err.println("input error");
//			e1.printStackTrace();
//		}
//		
		System.out.println("PASV end ");
		
		return false;
	}
	
	
	public void selectMenu(String command) throws IOException {
		//USER
		if (command.startsWith("USER")) {
			if (command.substring(5).equals("a")) {
				System.out.println("user login");
				System.out.println(command.substring(5));
				writer.println("331 User name okay, need password.");
				writer.flush();
			} else {	
				writer.println("530 User name wrong, please login again");
				writer.flush();
			}
			
			
		}//PASS 
		else if(command.startsWith("PASS")){
			
			if (command.substring(5).equals("a")) {
				//System.out.println("user login");
				//System.out.println(command.substring(5));
				writer.println("230 User logged in, proceed.");
				writer.flush();
				isLogedin = true;
			} else {	
				writer.println("530 password wrong, please login again");
				writer.flush();
			}
			
		}//SIZE
		else if (command.equals("SIZE")) {
			if (isLogedin == false) {
				writer.println("202 can not turn to PASV model. Please login first");
				writer.flush();
				return;
			}
			
			
		}//CWD
		else if(command.startsWith("CWD")){
			
			if (isLogedin == false) {
				System.out.println("202 can not turn to PASV model. Please login first");
				writer.println("202 can not turn to PASV model. Please login first");
				writer.flush();
				return;
			}
 
            if(newTransferSocket == null){
            	System.out.println("202 can not turn to PASV model. Please login first");
            	writer.println("202 can not turn to PASV model. Please login first");  
            	serverSocket = requestPort();    			
    			returnPort(serverSocket);
    			isTransport = true;
    			System.out.println("PASV model");
            }
            System.out.println("150 opening ascii mode data connection.");
            writer.println("150 opening ascii mode data connection.");  
            writer.flush();
            
            currentDir = command.substring(4);
            //TODO is a dir or a file
            File file=new File(currentDir);  
            String[] dirStructrue=new String[80];  
            String strType="";  
            
            DataOutputStream dos = null;//文件输出流
			dos = new DataOutputStream(newTransferSocket.getOutputStream());
			
            try{  
                dirStructrue=file.list();  
                for(int i = 0; i < dirStructrue.length; i++){  
                    if(dirStructrue[i].indexOf(".") == -1){  
                        strType = "/";  
                    }  
                    else{  
                        strType = "";  
                    }  
                    dos.writeUTF(dirStructrue[i] + strType);
                    
                }  
                dos.writeUTF("\r\n");
                dos.flush();
                dos.close();
                System.out.println("list showed");
               
            }catch(IOException e){  
                
            	dos.writeUTF(e.getMessage());
                dos.flush();
            }  
		
			
		}//PWD
		else if(command.equals("PWD")){
			if (isLogedin == false) {
				System.out.println("202 can not turn to PASV model. Please login first");
				writer.println("202 can not turn to PASV model. Please login first");
				writer.flush();
				return;
			}
 
            if(serverSocket == null){
            	System.out.println("202 can not turn to PASV model. Please login first");
            	serverSocket = requestPort();
    			
    			returnPort(serverSocket);
    			isTransport = true;
    			System.out.println("PASV model");
            }
            System.out.println("150 opening ascii mode data connection.");
            writer.println("150 opening ascii mode data connection.");  
            writer.flush();
            
            File file=new File(currentDir);  
            String dirStructrue=new String();
            
            DataOutputStream dos = null;//文件输出流
            dos = new DataOutputStream(newTransferSocket.getOutputStream());
            
            try{  
                dirStructrue=file.getPath();  
                
                dos.writeUTF(dirStructrue);
                dos.flush();

            }catch(IOException e){  
                
                dos.writeUTF(e.getMessage());
                dos.flush();
            }  
            dos.close();
		}//PASV
		else if (command.equals("PASV")) {
			
			if (isLogedin == false) {
				writer.println("202 can not turn to PASV model. Please login first");
				writer.flush();
				return;
			}
			
			serverSocket = requestPort();
			
			returnPort(serverSocket);
			isTransport = true;
			System.out.println("PASV model");
			
		}//LIST
		else if(command.equals("LIST") || command.equals("ls")){
			if (isLogedin == false) {
				System.out.println("202 can not turn to PASV model. Please login first");
				writer.println("202 can not turn to PASV model. Please login first");
				writer.flush();
				return;
			}
 
            if(newTransferSocket == null){
            	System.out.println("202 can not turn to PASV model. Please login first");
            	writer.println("202 can not turn to PASV model. Please login first");  
            	serverSocket = requestPort();    			
    			returnPort(serverSocket);
    			isTransport = true;
    			System.out.println("PASV model");
            }
            System.out.println("150 opening ascii mode data connection.");
            writer.println("150 opening ascii mode data connection.");  
            writer.flush();
            
            File file=new File(currentDir);  
            String[] dirStructrue=new String[80];  
            String strType="";  
            //writer.println("150 opening ascii mode data connection.");  
            //writer.flush();
            
            DataOutputStream dos = null;//文件输出流
			dos = new DataOutputStream(newTransferSocket.getOutputStream());
			
            try{  
                dirStructrue=file.list();  
                for(int i = 0; i < dirStructrue.length; i++){  
                    if(dirStructrue[i].indexOf(".") == -1){  
                        strType = "/";  
                    }  
                    else{  
                        strType = "";  
                    }  
                    dos.writeUTF(dirStructrue[i] + strType);
                    
                }  
                dos.writeUTF("\r\n");
                dos.flush();
                dos.close();
                System.out.println("list showed");
               
            }catch(IOException e){  
                
            	dos.writeUTF(e.getMessage());
                dos.flush();
            }  
		}
		//RETR
		
		else if (command.startsWith("RETR")) {
			if (isLogedin == false) {
				System.out.println("202 can not turn to PASV model. Please login first");
				writer.println("202 can not turn to PASV model. Please login first");
				writer.flush();
				return;
			}
 
            if(newTransferSocket == null){
            	System.out.println("202 can not turn to PASV model. Please start PASV first");
            	writer.println("202 can not turn to PASV model. Please start PASV first");  
            	writer.flush();
				return;
//            	serverSocket = requestPort();    			
//    			returnPort(serverSocket);
//    			isTransport = true;
    			
            }
            
            writer.println("150 Opening data connection.");
            writer.flush();
            
            String downloadFile = command.substring(5);  
            System.out.println(downloadFile);
            FileInputStream fin = null;
//            try {
//            	fin = new FileInputStream(downloadFile);
//			} catch (IOException e) {
//				System.out.println("no such file, input again");
//				return;
//			}
            
            DataOutputStream dos = null;//文件输出流
            dos = new DataOutputStream(newTransferSocket.getOutputStream());
            
            RandomAccessFile inFile=new RandomAccessFile(root+"/"+downloadFile, "r");//随机访问文件  
            //OutputStream outSocket=new dataSocket_retr.getOutputStream();//输出流  
            byte byteBuffer[]=new byte[1024];  
            int amount_retr;  
            try{  
                while((amount_retr=inFile.read(byteBuffer)) != -1){//通过随机访问文件，在服务器上读文件  
                	dos.write(byteBuffer, 0, amount_retr);//通过输出流，发送到客户端  
                } 
                dos.close();
                
                System.out.println("server end transfer");
                inFile.close();  
            }catch(IOException e){  
                writer.println("550 ERROR:File not found or access denied.");  
            } 
            
//            byte sendByte[] = new byte[1024];  
//            int length;  
//            //System.out.println(inFile.read());
//            
//            try{  
//            	while((length = fin.read(sendByte, 0, sendByte.length)) >= 0) {
//            		dos.write(sendByte, 0, length);
//            		dos.flush();
//                }
//            	System.out.println("end transfer");
//            	fin.close();
//            }catch(IOException e){  
//            	writer.println("550 ERROR:File not found or access denied.");  
//            	writer.flush();
//            } 
            
            
            
		}//QUIT
		else if (command.equals("QUIT")) {
			
			if (serverSocket != null) {
				serverSocket.close();
			}
			if (newTransferSocket != null) {
				newTransferSocket.close();
			}
			if (clientCommandSocket != null) {
				clientCommandSocket.close();
			}
			
					
			System.out.println("closed server socket");
			flag = false;
		}
		return;
	}
	
	
	@Override
	public void run() {
		
		while (flag) {
			
			
			try {
				//dis = new DataInputStream(clientCommandSocket.getInputStream());
				//dos = new DataOutputStream(clientCommandSocket.getOutputStream());
				System.out.println("wait for control socket's msg");
				//String msg = dis.readUTF();
				reader = new BufferedReader(new InputStreamReader(clientCommandSocket.getInputStream())); 
				writer = new PrintWriter(new OutputStreamWriter(clientCommandSocket.getOutputStream()));
				String msg = reader.readLine();
				System.out.println("in control socket, client say:" + msg);
				selectMenu(msg);
				System.out.println("receive msg");
			} catch (IOException e1) {
				System.err.println("input error");
				e1.printStackTrace();
			}
			
				
			
		}
		
		
		
		
		
	}
	
}

