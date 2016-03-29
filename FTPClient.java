package ftpexercise;

import java.util.*;
import java.io.*;
import java.net.*;

public class FTPClient{
	
	private static int PORT = 8021;
	private static String HOST = "127.0.0.1";
	
	private static boolean connected = false;
	private static boolean isLogin = false;
	private static boolean transPortConnect = false;
	
	private static String local = "G:\\ftp\\local\\";
	
	public FTPClient() {
	}
	
	public FTPClient(Socket temp) {
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException{
		
		BufferedReader reader; 
		PrintWriter writer;
		
//		DataInputStream dis;
//		DataOutputStream dos;
		
		Socket commandSocket = null;//control socket
		Socket newTransferSocket = null;

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
					break;
				
				case "PWD":
					
					if (false == connected || false == isLogin) {
						System.out.println("530 not login yet");
						break;
					}
					if (transPortConnect == false) {
						System.out.println("350 need a transport socket first");
						break;
					}
					writer.println("PWD\r\n");
					writer.flush();
					
					String responsePwd = reader.readLine();
					System.out.println(responsePwd);
					
					DataInputStream dis1;
					dis1 = new DataInputStream(newTransferSocket.getInputStream());
					String spwd = "";

					spwd = dis1.readUTF();
					System.out.println(spwd);
					dis1.close();
					break;
					
				case "CWD":
					if (false == connected || false == isLogin) {
						System.out.println("530 not login yet");
						break;
					}
					if (transPortConnect == false) {
						System.out.println("350 need a transport socket first");
						break;
					}
					writer.println(command);
					writer.flush();
					
					String responseCwd = reader.readLine();
					System.out.println(responseCwd);
					
					DataInputStream dis2;
					dis2 = new DataInputStream(newTransferSocket.getInputStream());
					String scwd = "";

					scwd = dis2.readUTF();
					System.out.println(scwd);
					dis2.close();
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
							System.out.println("transfer.localport:"+newTransferSocket.getLocalPort());
							System.out.println("transfer.port:"+newTransferSocket.getPort());
							transPortConnect = true;
						} catch (IOException e) {
							
							e.printStackTrace();
						}
//					dos = new DataOutputStream(newTransferSocket.getOutputStream());
//					dis = new DataInputStream(newTransferSocket.getInputStream());
					//dos.writeUTF("client transfer socket sent it ");
					break;
					
				case "PORT":
					if (!connected || !isLogin) {
						System.out.println("530 not login yet");
						break;
					}
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
                        fout = new FileOutputStream(new File(local + commandValue));
                        System.out.println("Receiving...");
                        boolean fileFlag = true;
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
					break;
					
				case "STOR":
					if (!connected || !isLogin) {
						System.out.println("530 not login yet");
						break;
					}
					break;
				
				case "REST":
					if (!connected || !isLogin) {
						System.out.println("530 not login yet");
						break;
					}
					break;
					
				
				case "QUIT":
					if (!connected || !isLogin) {
						System.out.println("530 not login yet");
						break;
					}
					writer.println(commandHeader);
					writer.flush();
					
					if (newTransferSocket != null) {
						newTransferSocket.close();
					} 
					if (commandSocket != null) {
						commandSocket.close();
					}	
					
					System.out.println("closed client socket");
					connected = false;
					break;
					
				default:
					break;
			}
			
			
			
		}
			
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
