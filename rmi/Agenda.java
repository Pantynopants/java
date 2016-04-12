
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Agenda extends Remote {
	
    public abstract boolean Register(String username, String password)throws RemoteException;
    
    public abstract boolean Login(String username, String password)throws RemoteException;
    
    public abstract boolean AddAgenda(String username, String password,String otherusername,int start,int end,String title, int meetingID) throws RemoteException;
    
    public abstract String GetAgenda(String otherusername, int start, int end)throws RemoteException;
    
    public abstract boolean DeleteAgenda(String username, String password, int meetingID) throws RemoteException;
    
    public abstract boolean ClearAll(String username, String password) throws RemoteException;
}
