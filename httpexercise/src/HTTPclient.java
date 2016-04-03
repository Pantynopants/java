import java.io.*;
import java.util.*;
import java.net.*;

public class HTTPclient {

	private static int SERVER_PORT = 8888;
	private static String SERVER_IP = "127.0.0.1";
	private static String savelocation = "C:/Users/Administrator/Desktop/web/save";
	private static String filename = "";
	
	public static void main(String[] args) throws IOException {

		Socket s = new Socket(SERVER_IP, SERVER_PORT);
		PrintStream writer = new PrintStream(s.getOutputStream());
		
		filename = "111.jpg";
		writer.println("GET /" + filename + " HTTP/1.1");
		writer.println("Host:localhost");
		writer.println("connection:keep-alive");
		writer.println();
		writer.flush();
		
		InputStream in = s.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String firstLineOfResponse = reader.readLine();//HTTP/1.1 200 OK
		String secondLineOfResponse = reader.readLine();//Content-Type:text/html
		String threeLineOfResponse = reader.readLine();//Content-Length:
		String fourLineOfResponse = reader.readLine();//blank line
		//读取响应数据，保存文件
		//success
		byte[] b = new byte[1024];
		OutputStream out = new FileOutputStream(savelocation + "/" + filename);
		int len = in.read(b);
		
		while(len!=-1)
		{
			out.write(b, 0, len);
			len = in.read(b);
		}
		
		in.close();
		out.close();
		//响应失败（状态码 响应失败（状态码 响应失败（状态码 响应失败（状态码 404 ） - 将响应信息打印在控制台上 将响应信息打印在控制台上 将响应信息打印在控制台上 将响应信息打印在控制台上 将响应信息打印在控制台上 将响应信息打印在控制台上 将响应信息打印在控制台上
		//output error message
		StringBuffer result = new StringBuffer();
		String line; while ((line = reader.readLine()) != null) {
			result.append(line);
		} 
		
		reader.close();
		System.out.println(result);
		
	}

}
