

import java.util.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;

public class FTPServer extends Thread{
	
	public static String root = "G:\\ftp\\";//��ǰ�������ĸ�Ŀ¼  
    private String currentDir = root;//��ǰ�������ϵĹ���Ŀ¼  
     
    private BufferedReader reader = null;//����socketʹ�õ� �ַ�������  
    private PrintWriter writer = null;//����socketʹ�õ� �ַ������  
    
//    private DataInputStream dis = null;//�ļ�������
//    private DataOutputStream dos = null;//�ļ������
	
    private Socket newTransferSocket;
    private Socket clientCommandSocket = null;//�ͻ�������socket
    private ServerSocket serverSocket = null;//����˴��� server socket
    
    private String clientIP = null;//�ͻ���IP��ַ  
    private int clientPort = 0;//�ͻ��˶˿�  
    private int port = -1;//�ͻ��˶˿�  
    
    private String user;  
    
    private int fileSize = 0;
    private int offset = 0;
    		
	private boolean isLogedin = false;
	private boolean isTransport = false;
	
	private int port_high = 0;
	private int port_low = 0;
	
	//for port model
	private String PORT_host = "";
	private int PORT_port = 0;
	
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
			writer.println("please login");
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
			
			System.out.println("transfer.localport:" + newTransferSocket.getLocalPort());
			System.out.println("transfer.port:" + newTransferSocket.getPort());
			System.out.println("FTPserver new thread for next client");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
	    DataInputStream dis = null;//�ļ�������
	    DataOutputStream dos = null;//�ļ������
		
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
	
	
	public void selectMenu(String command) throws IOException, InterruptedException {
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
		else if (command.startsWith("SIZE")) {

			if (isLogedin == false) {
				System.out.println("202 can not turn to PASV model. Please login first");
				writer.println("202 can not turn to PASV model. Please login first");
				writer.flush();
				return;
			}
 
            
            writer.println("150 Opening data connection.");
            writer.flush();
            
            String getFileSize = command.substring(5);  
            System.out.println(getFileSize);
            FileInputStream fin = null;
            
            if (!currentDir.endsWith("\\")) {
            	currentDir += "\\";
			}
            
            fin = new FileInputStream(currentDir + getFileSize);
			//System.out.println(fin.available());
            fileSize = fin.available();
            writer.println(fileSize);
            writer.flush();
			fin.close();
			
		}//CWD
		else if(command.startsWith("CWD")){
			
			if (isLogedin == false) {
				System.out.println("202 Please login first");
				writer.println("202 Please login first");
				writer.flush();
				return;
			}
 
            System.out.println("150 opening ascii mode data connection.");
            writer.println("150 opening ascii mode data connection.");  
            writer.flush();
            
            String newDir = new String();
            newDir = command.substring(4);
            File file = new File(currentDir);  
            if (!file.isDirectory()) {
				System.out.println("is not a dir,please input again");
				return;
			}
            String[] dirStructrue = new String[80];  
            String strType = "";  
            
            StringTokenizer paths = new StringTokenizer(newDir, "\\");
            String path = "";
            String tmpDir  = currentDir;
            
            while(paths.hasMoreTokens()) {
                path = paths.nextToken();
                if(path.equals("..")) {
                   
                	currentDir = currentDir.substring(0, currentDir.lastIndexOf("\\"));
                    System.out.println(currentDir);
                   
                } else if(!path.equals(".")) {
                	currentDir += "\\" + path;
                }
            }
            System.out.println(currentDir);
            File currentDirectory;
            currentDirectory = new File(currentDir);
            
            if(!currentDirectory.isDirectory()) {
//                instWriter.println("Error: Is Not A Directory");
//                instWriter.flush();
            	System.out.println("Error: Is Not A Directory");
            	currentDir = tmpDir;
                currentDirectory = new File(currentDir);
            } else {
//                instWriter.println("OK");
//                instWriter.flush();
            	System.out.println("OK");
            }
                System.out.println("cwd changed");
               
            
		
			
		}//PWD
		else if(command.equals("PWD")){
			if (isLogedin == false) {
				System.out.println("202 Please login first");
				writer.println("202 Please login first");
				writer.flush();
				return;
			}
 
            
            System.out.println("150 opening ascii mode data connection.");
            writer.println("150 opening ascii mode data connection.");  
            writer.flush();
            
            File file = new File(currentDir);  
            String dirStructrue = new String();
            
            dirStructrue=file.getPath();  
           
			writer.println(dirStructrue);
			writer.flush();
            
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
			
		}
		//PORT
		else if (command.startsWith("PORT")) {
			
			String PORTInfo = command.substring(5, command.length());  
			PORTInfo = PORTInfo.trim(); 
			String[] params = PORTInfo.split(",");
			
			PORT_host = params[0] + "." + params[1] + "." + params[2] + "." + params[3];  
            String port1 = null;  
            String port2 = null;  
            if(params.length == 6){  
                port1 = params[4];  
                port2 = params[5];  
            }  
            else{  
                port1 = "0";  
                port2 = params[4];  
            }  
            PORT_port = Integer.parseInt(port1)*256 + Integer.parseInt(port2);  
            
            newTransferSocket = new Socket(PORT_host, PORT_port);
            isTransport = true;
            
            writer.println("200 command successful.");
            writer.flush();
            
		}
		//LIST
		else if(command.equals("LIST") || command.equals("ls")){
			if (isLogedin == false) {
				System.out.println("202 Please login first");
				writer.println("202 Please login first");
				writer.flush();
				return;
			}
 
            if(isTransport == false){
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
            String dirType="";  
            //writer.println("150 opening ascii mode data connection.");  
            //writer.flush();
            
            DataOutputStream dos = null;//�ļ������
			dos = new DataOutputStream(newTransferSocket.getOutputStream());
			
			
	        
            try{  
                dirStructrue = file.list();  
                for(int i = 0; i < dirStructrue.length; i++){  
                	Calendar cal = Calendar.getInstance();  
        	        long time = file.lastModified();  
        	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");         
        	        cal.setTimeInMillis(time);
        	        
                    if(dirStructrue[i].indexOf(".") == -1){  
                    	dirType = "/";  
                    }  
                    else{  
                    	dirType = "";  
                    }  
                    dos.writeUTF(dirStructrue[i] + dirType + "\t\t" + formatter.format(cal.getTime()));
                    
                }  
                dos.writeUTF("\r\n");
                dos.flush();
                dos.close();
                System.out.println("list showed");
               
            }catch(IOException e){  
                
            	dos.writeUTF(e.getMessage());
                dos.flush();
            }  
		}//REST
		else if (command.startsWith("REST")) {
			if (isLogedin == false) {
				System.out.println("202 Please login first");
				writer.println("202 Please login first");
				writer.flush();
				return;
			}
			
			offset = Integer.parseInt(command.substring(5));
            writer.println("350 Restarting at " + offset + ". Send STOR or RETR to initiate transfer.");
            writer.flush();
            
            writer.println( offset );
            writer.flush();
            
		}//TODO
		//RETR		
		else if (command.startsWith("RETR")) {
			if (isLogedin == false) {
				System.out.println("202  Please login first");
				writer.println("202  Please login first");
				writer.flush();
				return;
			}
 
            if(isTransport == false){
            	System.out.println("202 can not turn to PASV model. Please start PASV first");
            	writer.println("202 can not turn to PASV model. Please start PASV first");  
            	writer.flush();
				return;
            }
            
            writer.println("150 Opening data connection.");
            writer.flush();
            
            String downloadFile = command.substring(5).trim();  
            System.out.println(downloadFile);
            
            if (!currentDir.endsWith("\\")) {
            	currentDir += "\\";
			}
            
            File file = new File(currentDir + downloadFile);
            
            System.out.println(file);
             
            if(file.isDirectory() && file.exists()){
            	
            	System.out.println("�����ļ���");
                String [] filenames = file.list();
                int numOfFile = filenames.length;
                String DownloadDirPath = currentDir + downloadFile;// now Without "\\"
                System.out.println("�����ļ������ļ�������" + numOfFile);
                 

                DataOutputStream dos = null;//�ļ������
                /*
                 *  1
                 */
                dos = new DataOutputStream(newTransferSocket.getOutputStream());
                dos.write(numOfFile);
                System.out.println(numOfFile);
                numOfFile = 1;
                for(int i = 0; i < numOfFile; i++){
                	
                	//��ÿ���ļ���Ҫ����������������
//                	writer.println("111 transport start");
//                	writer.flush();
//                	String transDirStart = reader.readLine();
//                    System.out.println("\n\n\n" + transDirStart + "\n\n\n");
//                    while (!transDirStart.startsWith("222")) {
//						Thread.sleep(1000);
//						transDirStart = reader.readLine();
//	                    System.out.println("\n\n\n" + transDirStart + "\n\n\n");
//					}
//                    
//                    String transDirStart1 = reader.readLine();
//                    System.out.println("\n\n\n" + transDirStart1 + "\n\n\n");
                    
                    dos = new DataOutputStream(newTransferSocket.getOutputStream());
                    /*
                     *  2
                     */
                    writer.println(filenames[i]); //����ÿ���ļ���
                    writer.flush();
                    
                    System.out.println(filenames[i]);
                    //file = new File(DownloadDirPath +"\\" + filenames[i]);
//                    String transDirStart = reader.readLine();
//                    System.out.println("\n\n\n" + transDirStart + "\n\n\n");
                    
                    FileInputStream fin = null;
                    
                    RandomAccessFile inFile = new RandomAccessFile(DownloadDirPath +"\\" + filenames[i], "r");//��������ļ�  
                    
                    int skipData = inFile.skipBytes(offset);
                    System.out.println("skip Data is " + skipData);
                    
                    byte byteBuffer[] = new byte[1024];  
                    int amount_retr;  
                    try{  
                        while((amount_retr = inFile.read(byteBuffer)) != -1){//ͨ����������ļ����ڷ������϶��ļ�  
                        	dos.write(byteBuffer, 0, amount_retr);//ͨ������������͵��ͻ���  
                        	dos.flush();
                        } 
                        dos.close();
                        
                        System.out.println("server end transfer");
                        inFile.close();  
                    }catch(IOException e){  
                        writer.println("550 ERROR:File not found or access denied.");  
                        writer.flush();
                    }
                     offset = 0;
                     
                     
//                     String transDirSuccess = reader.readLine();
//                     System.out.println(transDirSuccess);
//                     
//                     writer.println("complete");
//                 	 writer.flush();
                }
                System.out.println("complete");
//                dos.writeUTF("\\");
                
            }else if(file.exists()){
            	
            	FileInputStream fin = null;
                
                DataOutputStream dos = null;//�ļ������
                dos = new DataOutputStream(newTransferSocket.getOutputStream());
                
                int numOfFile = 0;
                dos.write(numOfFile);
                //dos.flush();
                
                if (!currentDir.endsWith("\\")) {
                	currentDir += "\\";
    			}
                
                RandomAccessFile inFile = new RandomAccessFile(currentDir + downloadFile, "r");//��������ļ�  
                //OutputStream outSocket = new dataSocket_retr.getOutputStream();//�����  
                
                int skipData = inFile.skipBytes(offset);
                System.out.println("skip Data is " + skipData);
                
                byte byteBuffer[] = new byte[1024];  
                int amount_retr;  
                
                try{  
                    while((amount_retr = inFile.read(byteBuffer)) != -1){//ͨ����������ļ����ڷ������϶��ļ�  
                    	dos.write(byteBuffer, 0, amount_retr);//ͨ������������͵��ͻ���  
                    } 
                    dos.close();
                    
                    System.out.println("server end transfer");
                    inFile.close();  
                }catch(IOException e){  
                    writer.println("550 ERROR:File not found or access denied.");  
                }
                offset = 0;
                
			}else{
				writer.println("550 ERROR:File not found or access denied.");  
				return;			
			}
             
            
		}
		//STOR
		else if (command.startsWith("STOR")) {
			if (isLogedin == false) {
				System.out.println("202 Please login first");
				writer.println("202 Please login first");
				writer.flush();
				return;
			}
 
            if(isTransport == false){
            	System.out.println("202 can not turn to PASV model. Please start PASV first");
            	writer.println("202 can not turn to PASV model. Please start PASV first");  
            	writer.flush();
				return;
    			
            }
            
            writer.println("150 Opening data connection.");
            writer.flush();
            
            String uploadFile = command.substring(5);  
            System.out.println(uploadFile);
            
            FileInputStream fin = null;
            DataInputStream dis111;
			dis111 = new DataInputStream(newTransferSocket.getInputStream());
			
//			String responseStor;
//			responseStor = reader.readLine();
//			System.out.println(responseStor);
			
			
//			if(responseStor.startsWith("150")) {
                byte[] inputByte = new byte[1024];
                int length = 0;
                FileOutputStream fout = null;
                if (!currentDir.endsWith("\\")) {
					currentDir += "\\";
				}
                fout = new FileOutputStream(new File(currentDir + uploadFile), true);
                System.out.println("Receiving...");
                boolean fileFlag = true;
				while(fileFlag ) {
                	if(dis111 == null || (length = dis111.read(inputByte, 0, inputByte.length)) == -1) {
                		fileFlag = false;
                        break;
                    }
                	
                 
                    System.out.println(length);
                    fout.write(inputByte, 0, length);
                    fout.flush();
                    System.out.println("writing...");
                }
				dis111.close();
				fout.close();
                System.out.println("Complete!");
                
//            } else {
//                System.out.println(responseStor);
//            }
			
             
            
		}//QUIT
		else if (command.equals("QUIT")) {
			
			writer.println("221 goodbye.");
            writer.flush();
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
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
				
			
		}
		
		
		
		
		
	}
	
}

