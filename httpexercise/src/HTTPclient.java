import java.io.*;
import java.util.*;
import java.net.*;
/*
 * if use this client for get website's content, also 
 * read head(maybe different) until a blank line
 * 
 */
public class HTTPclient {
	
	private static String savelocation = "G:/ftp/local";
	
//	private static int SERVER_PORT = 8888;
//	private static String SERVER_IP = "127.0.0.1";
//	private static String filename = "dog.jpg";
//	private static String localFilename = "dog.jpg";
	
	
	private static int SERVER_PORT = 8888;
	private static String SERVER_IP = "127.0.0.1";
	private static String filename = "wmv.wmv";
	private static String localFilename = "wmv.wmv";
	
	
//	private static int SERVER_PORT = 80;
//	private static String SERVER_IP = "www.baidu.com";
//	private static String filename = "img/bd_logo1.png";
//	private static String localFilename = "bd_logo1.png";
	
	
	public static void main(String[] args) throws IOException {

		Socket s = new Socket(SERVER_IP, SERVER_PORT);
		PrintStream writer = new PrintStream(s.getOutputStream());
		//filename = "111.jpg";
		writer.println("GET /" + filename + " HTTP/1.1");
		writer.println("Host:www.baidu.com");
		writer.println("connection:keep-alive");
		writer.println();
		writer.flush();
		
		InputStream in = s.getInputStream();
//		DataInputStream in = new DataInputStream(ins);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
		String statecode = null;
//		String firstLineOfResponse = reader.readLine();//HTTP/1.1 200 OK
//		System.out.println(firstLineOfResponse);
//		String secondLineOfResponse = reader.readLine();//Content-Type:text/html
//		System.out.println(secondLineOfResponse);
//		String threeLineOfResponse = reader.readLine();//Content-Length:
//		System.out.println(threeLineOfResponse);
//		String fourLineOfResponse = reader.readLine();//blank line
		
		
		statecode = reader.readLine();
		while (!statecode.equals("")) {
			System.out.println(statecode);
			statecode = reader.readLine();
			
		}
		
		//success
		File savefile = new File(savelocation + "/" + localFilename);
		if (!savefile.exists()) {
			savefile.createNewFile();
		}
		byte[] b = new byte[1024];
		FileOutputStream out = new FileOutputStream(savefile);
		
		int len = 0;
		
		while((len = in.read(b)) != -1)
		{
			out.write(b, 0, len);
			out.flush();
			System.out.println("get data");
		}
		System.out.println("end");
		
		in.close();
		out.close();
		
		
		
		//output error message
		StringBuffer result = new StringBuffer();
		String line; 
		while ((line = reader.readLine()) != null) {
			result.append(line);
		} 
		
		reader.close();
		System.out.println(result);
		
	}

}
