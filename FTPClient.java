package ftpexercise;

import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.file.Files;

public class FTPClient{
	
	private static int PORT = 8021;
	private static String HOST = "127.0.0.1";
	
	private static boolean connected = false;
	private static boolean isLogin = false;
	private static boolean transPortConnect = false;
	
	private static String local = "G:\\ftp\\local\\";//download dir
	
	private static String fileSize;
	
	
	public FTPClient() {
	}
	
	public FTPClient(Socket temp) {
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException{
		
		int offset = 0;
		
		BufferedReader reader; 
		PrintWriter writer;
		
//		DataInputStream dis;
//		DataOutputStream dos;
		
		Socket commandSocket = null;//control socket
		Socket newTransferSocket = null;

		ServerSocket serverSocket = null;//for PORT model
		
		try {
			commandSocket = new Socket(HOST,PORT);
			reader = new BufferedReader(new InputStreamReader(commandSocket.getInputStream())); 
			String hello = reader.readLine();
			System.out.println(hello);
			connected = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (commandSocket == null) {
			connected = false;
		} else {
			System.out.println("connected!!!!");
			connected = true;
		}
		
		
		while (connected) {
			
			/*
			 * 1
			 * 
			 * 
			 * 
			 * */			
			 
			
			reader = new BufferedReader(new InputStreamReader(commandSocket.getInputStream())); 
			writer = new PrintWriter(new OutputStreamWriter(commandSocket.getOutputStream()));
			
			
			//DataOutputStream dos;
//			dis = new DataInputStream(commandSocket.getInputStream());
//			dos = new DataOutputStream(commandSocket.getOutputStream());
			
			//String msg = dis.readUTF();
			//System.out.println("server say:" + msg);		
			String command;
//	        String response;
			
			Scanner scanner = new Scanner(System.in);
			//String clientSent = scanner.next();
			
        	command = scanner.nextLine();
	        
	        StringTokenizer cutter = new StringTokenizer(command);
	        String commandHeader = cutter.nextToken();
	        String commandValue = cutter.hasMoreTokens() ? cutter.nextToken() : ""; 
			switch (commandHeader) {
				case "USER":
					if (!connected) {
						System.out.println("530 not connect yet	");
						break;
					}
					System.out.println(command);
					writer.println(command + "\r\n");
					writer.flush();
					
					String receive331 = reader.readLine();
					System.out.println(receive331);
					if (receive331.startsWith("331")) {
						commandHeader = "PASS";
					}else {
						break;
					}
					
					
				case "PASS":
					if (!connected) {
						System.out.println("530 not connect yet	");
						break;
					}
					System.out.println("please input password");
					command = scanner.nextLine();
//					String passwd = command.substring(5);
//					System.out.println(passwd);
					writer.println(command + "\r\n");
					writer.flush();
					
					String receive230 = reader.readLine();
					System.out.println(receive230);
					if (receive230.startsWith("230")) {
						isLogin = true;
					}
					break;
				case "LIST":
					if (false == connected || false == isLogin) {
						System.out.println("530 not login yet");
						break;
					}
					if (transPortConnect == false) {
						System.out.println("350 need a transport socket first");
						break;
					}
					writer.println("LIST\r\n");
					writer.flush();
					
					String responseList = reader.readLine();
					System.out.println(responseList);
					
					DataInputStream dis;
					
					dis = new DataInputStream(newTransferSocket.getInputStream());
					String s = "";
					int num = 0;
					try {
						while (!((s = dis.readUTF()).equals("\r\n"))) {
							System.out.println(s);
							num++;
						}
					} catch (Exception e) {
						System.out.println("EOF");
					}
					dis.close();
					System.out.println("\ntotal " + num + " items");
					
					break;
				case "SIZE":
					if (!connected || !isLogin) {
						System.out.println("530 not login yet");
						break;
					}
					
					writer.println(command);
					writer.flush();
					
					String responseSizestatus = reader.readLine();
					System.out.println(responseSizestatus);
					
					fileSize = reader.readLine();
					System.out.println(fileSize);
					
					break;
				
				case "PWD":
					
					if (false == connected || false == isLogin) {
						System.out.println("530 not login yet");
						break;
					}
					writer.println("PWD\r\n");
					writer.flush();
					
					String responsePwd = reader.readLine();
					System.out.println(responsePwd);
					
					String Pwd = reader.readLine();
					System.out.println(Pwd);
					break;
					
				case "CWD":
					//命令：CWD aaa 或CWD ..    即使用相对路径
					if (false == connected || false == isLogin) {
						System.out.println("530 not login yet");
						break;
					}
					
					writer.println(command);
					writer.flush();
					
					String responseCwd = reader.readLine();
					System.out.println(responseCwd);
					break;
					
				case "PASV":
					if (!connected || !isLogin) {
						System.out.println("530 not login yet");
						break;
					}
						
					/*
					 * 5
					 * 
					 * 
					 * 
					 * */
					
					writer.println("PASV\r\n");
					writer.flush();
					String response = reader.readLine();//then paused
					//System.out.println(response);
					
					if (!response.startsWith("227")) {
						break;
					}
					
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
					
					try {
							newTransferSocket = new Socket(HOST,newport);
							System.out.println("transfer.localport:" + newTransferSocket.getLocalPort());
							System.out.println("transfer.port:" + newTransferSocket.getPort());
							transPortConnect = true;
						} catch (IOException e) {
							
							e.printStackTrace();
						}
					break;
					//TODO
				case "PORT":
					if (!connected || !isLogin) {
						System.out.println("530 not login yet");
						break;
					}
					
					String PORTInfo = command.substring(5, command.length());  
					PORTInfo = PORTInfo.trim(); 
					String[] params = PORTInfo.split(",");
					
					
					
					String PORT_host = params[0] + "." + params[1] + "." + params[2] + "." + params[3];  
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
		            int PORT_port = Integer.parseInt(port1)*256 + Integer.parseInt(port2); 
		            
					writer.println(command);
					writer.flush();

					serverSocket = new ServerSocket(PORT_port);
					newTransferSocket = serverSocket.accept();
					
					String responsePORT = reader.readLine();
					System.out.println(responsePORT);
					
					
					transPortConnect = true;
						
					break;
				case "RETR":
					if (false == connected || false == isLogin) {
						System.out.println("530 not login yet");
						break;
					}
					if (transPortConnect == false) {
						System.out.println("350 need a transport socket first");
						break;
					}
					writer.println(command + "\r\n");
					writer.flush();
					
					String responsePetr = reader.readLine();
					System.out.println(responsePetr);
					
					DataInputStream dis11;
					dis11 = new DataInputStream(newTransferSocket.getInputStream());
					
						if(responsePetr.startsWith("150")) {
	                        byte[] inputByte = new byte[1024];
	                        int length = 0;
	                        FileOutputStream fout = null;
	                        fout = new FileOutputStream(new File(local + commandValue), true);
	                        System.out.println("Receiving...");
	                        boolean fileFlag = true;
	                        
	                        //for resume-file-upload-download
	                        //dis11.skipBytes(offset);
	                        
							while(fileFlag ) {
	                        	if(dis11 == null || (length = dis11.read(inputByte, 0, inputByte.length)) == -1) {
	                        		fileFlag = false;
	                                break;
	                            }
	                        	                          
	                            System.out.println(length);
	                            fout.write(inputByte, 0, length);
	                            fout.flush();
	                            System.out.println("writing...");
	                        }
							dis11.close();
							fout.close();
	                        System.out.println("Complete!");
	                        
	                    } else {
	                        System.out.println(responsePetr);
	                    }
					
					
					offset = 0;
					break;
				case "STOR":
					if (false == connected || false == isLogin) {
						System.out.println("530 not login yet");
						break;
					}
					if (transPortConnect == false) {
						System.out.println("350 need a transport socket first");
						break;
					}
					writer.println(command + "\r\n");
					writer.flush();
					
					String responseStor = reader.readLine();
					System.out.println(responseStor);
					
					
						if (responseStor.startsWith("150")) {
							
							DataOutputStream dos = null;//文件输出流
				            dos = new DataOutputStream(newTransferSocket.getOutputStream());
				            String uploadFile = commandValue;
				            
				            RandomAccessFile inFile = new RandomAccessFile(local + "/" + uploadFile, "r");
				            //OutputStream outSocket = new dataSocket_retr.getOutputStream();
				            byte byteBuffer[] = new byte[1024];  
				            int amount_retr;  
				            
				            //for resume-file-upload-download
				            int skipData = inFile.skipBytes(offset);
				            System.out.println("skip Data is " + skipData);
				            
				            try{  
				                while((amount_retr = inFile.read(byteBuffer, 0, byteBuffer.length)) != -1){
				                	dos.write(byteBuffer, 0, amount_retr);
				                } 
				                
				                dos.close();
				                System.out.println("client end transfer");
				                inFile.close();  
				                
				            }catch(IOException e){  
				            	System.out.println("550 ERROR:File not found or access denied.");
				                writer.println("550 ERROR:File not found or access denied.");  
				            }
						} else {
							offset = 0;
							break;
						}
					offset = 0;
					break;
				
				case "REST":
					if (!connected || !isLogin) {
						System.out.println("530 not login yet");
						break;
					}
					writer.println(command);
					writer.flush();
					
					String responseRest = reader.readLine();
					System.out.println(responseRest);
					
					String numOfRest = reader.readLine();
					System.out.println(Integer.parseInt(numOfRest));
					
					if (responseRest.startsWith("350")) {
						offset = Integer.parseInt(numOfRest);
					}
					System.out.println(offset);
					break;
					
				
				case "QUIT":
					if (!connected || !isLogin) {
						System.out.println("530 not login yet");
						break;
					}
					writer.println(commandHeader);
					writer.flush();
					
					String responseQuit = reader.readLine();
					
					if (newTransferSocket != null) {
						newTransferSocket.close();
					} 
					if (commandSocket != null) {
						commandSocket.close();
					}	
					
					
					System.out.println(responseQuit);
					System.out.println("closed client socket");
					connected = false;
					
					break;
					
				default:
					break;
			}
			
			
			
		}
			
	}
	

	
	
}
