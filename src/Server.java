
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args){
        try{
        	AgendaImpl agenda=new AgendaImpl();
//        	Registry registry = LocateRegistry.getRegistry();
//        	registry.bind("RMI",agenda);
            LocateRegistry.createRegistry(8888);
            Naming.bind("rmi://localhost:8888/RMI",agenda);
            System.out.println("success bind");
        }
        catch (RemoteException e){
            e.printStackTrace();
        }
        catch (AlreadyBoundException e){
            e.printStackTrace();
        } catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
