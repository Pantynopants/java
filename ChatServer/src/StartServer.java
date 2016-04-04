import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class StartServer {

	public static void main(String[] args) {
		
		ServerSocket ss = null;
		
		try {
			ss = new ServerSocket(4001);
			
			while (true) {
				
				Socket s = ss.accept();
				UserThread user = new UserThread(s);
				user.start();
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
