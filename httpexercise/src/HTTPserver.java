import java.io.*;
import java.util.*;
import java.net.*;


public class HTTPserver extends Thread{
	private String root = "C:/Users/Administrator/Desktop/web";
	
	private PrintStream writer = null;
	private BufferedReader reader = null;
	private FileInputStream in = null;
	private DataOutputStream os = null;
	private DataInputStream is = null;
	
	private Socket s = null;
	
	public HTTPserver() {
	}
	
	public HTTPserver(Socket client) {
		
		s = client;
		
	}
	public void returnResponse() throws IOException {
		
		
		reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
		
		
		String firstLineRequest;
		firstLineRequest = reader.readLine();
		
		String uri = firstLineRequest.split(" ")[1];
		System.out.println(uri);
		
		File file = new File(root, uri);
		
		if (file.exists()) {
			
			writer = new PrintStream(s.getOutputStream());
			writer.println("HTTP/1.1 200 OK");
			
			if (uri.endsWith(".html")) {
				writer.println("Content-Type:text/html");
			} else if(uri.endsWith(".jpg") || uri.endsWith(".jpeg")){
				writer.println("Content-Type:image/jpeg");
			}else{
				writer.println("Content-Type:application/octet-stream");
			}
			
			in = new FileInputStream(root + uri);
			
			//返回内容字节数
			writer.println("Content-Length:" + in.available());
			//根据HTTP协议 ，空行将结束头信息
			writer.println();
			
			writer.flush();
			
			byte[] b = new byte[1024];
			int len = 0;
			len = in.read(b);
			
			os = new DataOutputStream(s.getOutputStream());
			while (-1 != len) {
				os.write(b, 0, len);
				len = in.read();
			}
			os.flush();
			
		} else {
			writer.println("HTTP/1.1 404 Not Found");
			writer.println("Content-Type:text/html");
			writer.println("Content-Length:7");
			writer.println();
			//发送响应体
			writer.println("访问内容不存在");
			writer.flush();
		}
		
		reader.close();
		return;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			returnResponse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IOE");
			e.printStackTrace();
		}
		
	}
}
