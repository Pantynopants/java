import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class StartServer {

	public static ServerSocket fileSocket = null;
	public static ServerSocket ss = null;	
	
	public static void main(String[] args) {
		
		try {
			ss = new ServerSocket(4001);
			fileSocket = new ServerSocket(4002);
			new UserinfoDAO();
			while (true) {
						
				Socket s = ss.accept();
				//can send and login
				UserThread user = new UserThread(s);
				user.start();
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
