import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;

public class Client {
    public static void main(String[] args) {
        String realUser = "";
        String realPass = "";
        try {
        	
        	//register as "RMI"
            Agenda agenda = (Agenda) Naming.lookup("rmi://localhost:8888/RMI");
            Scanner input = new Scanner(System.in);
            boolean flag = true;
            String command = "";
            while (flag) {
                command = input.nextLine();
                System.out.println(command);
                
                
                    if(command.startsWith("register")){
                    	
//                    	register [username] [password]

                    	  String[] array = command.split(" ");
                    	  String username = array[1];
                    	  String password = array[2];
                    	  
                        if (agenda.Register(username, password)) {
                            System.out.println("success");
                        } else {
                            System.out.println("fail:user name already exist");
                        }
                        continue;
                    }
                    if(command.startsWith("login")){
                    	String[] array = command.split(" ");
                  	    String usernamelog = array[1];
                  	    String passwordlog = array[2];
                        if (agenda.Login(usernamelog, passwordlog)) {
                            System.out.println("success");
                            realUser = usernamelog;
                            realPass = passwordlog;
                        } else {
                            System.out.println("fail");
                        }
                        continue;
                    }
                    if(command.startsWith("add")){
                    	//add [username] [password] [otherusername] [start] [end] [title]
                    	String[] array = command.split(" ");
                  	    realUser = array[1];
                  	    realPass = array[2];
                  	    String otheruser = array[3];
                  	    int starthour = Integer.parseInt(array[4]);
                  	    int endhour = Integer.parseInt(array[5]);
                  	    String title = array[6];
                  	  
                        
                        if (agenda.AddAgenda(realUser, realPass, otheruser, starthour, endhour, title, (int)(Math.random()*100))) {
                            System.out.println("success");
                            String result = agenda.GetAgenda(realUser, starthour, endhour);
                            System.out.println(result);
                        } else {
                            System.out.println("fail");
                        }
                        continue;
                    }
                    if(command.startsWith("query")){
                    	
//                    	query [username] [password] [start] [end]
                    	String[] array = command.split(" ");
                  	    realUser = array[1];
                  	    realPass = array[2];
                  	    int starthourq = Integer.parseInt(array[3]);
                  	    int endhourq = Integer.parseInt(array[4]);
                  	    
                        
                        String result = agenda.GetAgenda(realUser, starthourq, endhourq);
                        System.out.println(result);
                    }
                    if(command.startsWith("clear")){
                    	
//                    	clear [username] [password]
                    	String[] array = command.split(" ");
                  	    realUser = array[1];
                  	    realPass = array[2];
                    			
                        if (agenda.ClearAll(realUser, realPass)) {
                            System.out.println("success");
                        } else {
                            System.out.println("fail");
                        }
                        continue;
                    }
                    if(command.startsWith("delete")){
                    	
//                    	delete [username] [password] [(int)meetingID]
                    	String[] array = command.split(" ");
                  	    realUser = array[1];
                  	    realPass = array[2];
                  	    int idInt = Integer.parseInt(array[3]);
                  	    
                        if(agenda.DeleteAgenda(realUser,realPass,idInt)){
                            System.out.println("success");
                        }
                        else{
                            System.out.println("fail");
                        }
                        continue;
                    }
                    if(command.startsWith("register")){
                    	flag = false;
                        continue;
                    }
                   
                
            }
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
