import java.io.*;
import java.util.*;
import java.net.*;


public class HTTPserver extends Thread{
	
	private String root = "G:/ftp";
	
	private PrintStream writer = null;
	private BufferedReader reader = null;
	private FileInputStream in = null;
	private OutputStream os = null;
	private InputStream is = null;
	
	private Socket s = null;
	
	public HTTPserver() {
	}
	
	public HTTPserver(Socket client) {
		
		s = client;
		
	}
	public void returnResponse() throws IOException {
		
		try {
			
		is = this.s.getInputStream();
		os = this.s.getOutputStream();
			
		reader = new BufferedReader(new InputStreamReader(is));
		writer = new PrintStream(os); 
		
		String firstLineRequest;
		firstLineRequest = reader.readLine();
		
		String uri = firstLineRequest.split(" ")[1];
		System.out.println(uri);
		if (!uri.startsWith("/")) {
			uri += "/" + uri;
		}
		File file = new File(root, uri);
		
		if (file.exists()) {
			
			
			writer.println("HTTP/1.1 200 OK");
			
			if (uri.endsWith(".html")) {
				writer.println("Content-Type:text/html");
			} else if(uri.endsWith(".jpg") || uri.endsWith(".jpeg")){
				writer.println("Content-Type:image/jpeg");
			}else{
				writer.println("Content-Type:application/octet-stream");
			}
			
			in = new FileInputStream(root + uri);
			
			
			FileInputStream fis = new FileInputStream(file);
			
			writer.println("Content-Length:" + fis.available());
			writer.println();    //according to http protocol, a blank line need to split the head and content
			writer.flush();
			
			//send data
			byte[] b = new byte[1024];
			int len = 0;
			len = fis.read(b , 0 , b.length);
			while(len != -1)
			{
				os.write(b , 0 , len);
				len = fis.read(b, 0, b.length);
			}
			os.flush();
			fis.close();
		}
		else{      
		
			writer.println("HTTP/1.1 404 Not Found");
			writer.println("Content-Type:text/plain");
			writer.println("Content-Length:14");
			writer.println();
			writer.println("访问内容不存在");
			writer.flush();
		}
		is.close();
		os.close();
		reader.close();
		writer.close();
		s.close();
		} catch (SocketException e) {
			System.out.println("reset connection");
		}
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
