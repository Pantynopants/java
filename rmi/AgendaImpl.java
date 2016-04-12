
import javax.jws.soap.SOAPBinding;
import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

public class AgendaImpl extends UnicastRemoteObject implements Agenda {

	private static String UserSave_PATH = "G:\\ftp\\rmi\\user.txt";
	private static String AgendaSave_PATH = "G:\\ftp\\rmi\\agenda.txt";
	
	//must throws RemoteException here!!!
    public AgendaImpl() throws RemoteException {

    }

    @Override
    public boolean Register(String username, String password) throws RemoteException {
        try {
        	//write agenda information in a file
        	
            FileWriter file = new FileWriter(new File(UserSave_PATH),true);
            BufferedWriter writer = new BufferedWriter(file);
            
            writer.append(username + " " + password + "\r\n");
            writer.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean Login(String username, String password) throws RemoteException {
        try {
            FileReader file = new FileReader(new File(UserSave_PATH));
            BufferedReader reader = new BufferedReader(file);
            String content = reader.readLine();
            while (content != null) {
                String[] result = content.split(" ");
                if (username.equals(result[0])) {
                    if (password .equals(result[1])) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    content=reader.readLine();
                    continue;
                }
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean AddAgenda(String username, String password, String otherusername, int start, int end, String title, int meetingID) throws RemoteException {
        try {
        	
            FileWriter file = new FileWriter(new File(AgendaSave_PATH),true);
            BufferedWriter writer = new BufferedWriter(file);
            
            FileReader file1 = new FileReader(new File(AgendaSave_PATH));
            BufferedReader reader = new BufferedReader(file1);
            
            String content = reader.readLine();
            
            while (content != null) {
                String[] array = content.split(" ");
                
                //array[2] is otheruser, array[0] is currentuser
                System.out.println(array[5] + "\t"+ title);
                if ( array[5].equals( title) && array[0].equals( username)) {
                	System.out.println("already have a meeting in the same time");
                	writer.close();
                	reader.close();
                	return false;
                }
                content = reader.readLine();
            }
            
            writer.append(username + " " + password + " " + otherusername + " " + Integer.toString(start) + " " + Integer.toString(end) + " " + title + " " + meetingID + "\r\n");
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String GetAgenda(String otherusername, int start, int end) throws RemoteException {
        try {
        	
            FileReader file = new FileReader(new File(AgendaSave_PATH));
            BufferedReader reader = new BufferedReader(file);
            
            List<AgendaInfo> result = new LinkedList<AgendaInfo>();
            String content = reader.readLine();
            String finalre="";
            while (content != null) {
                String[] array = content.split(" ");
                
                //array[2] is otheruser, array[0] is currentuser
                if ( (array[2].equals( otherusername) || array[0].equals( otherusername)) && Integer.parseInt(array[3]) == start && Integer.parseInt(array[4]) == end) {
                    AgendaInfo temp = new AgendaInfo(array[0], array[1], array[2], Integer.parseInt(array[3]), Integer.parseInt(array[4]), array[5], Integer.parseInt(array[6]));
                    result.add(temp);
                    System.out.println(temp.toString());
                    finalre += temp.toString() + "\n";
                }
                content = reader.readLine();
            }

            return finalre;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean DeleteAgenda(String username, String password, int meetingID) throws RemoteException {
        try {
        	
            FileReader file = new FileReader(new File(AgendaSave_PATH));
            BufferedReader reader = new BufferedReader(file);
            List<AgendaInfo> result = new LinkedList<AgendaInfo>();
            String content = reader.readLine();
            //get the special information
            while (content != null) {
                String[] array = content.split(" ");
                if (Integer.parseInt(array[6]) == meetingID) {
                    content=reader.readLine();
                    continue;
                }
                AgendaInfo temp = new AgendaInfo(array[0], array[1], array[2], Integer.parseInt(array[3]), Integer.parseInt(array[4]), array[5], Integer.parseInt(array[6]));
                result.add(temp);
                content = reader.readLine();
            }
            try {
            	//rewrite the data in agenda.txt
                FileWriter wrifile = new FileWriter(new File(AgendaSave_PATH));
                BufferedWriter writer = new BufferedWriter(wrifile);
                for (AgendaInfo info : result) {
                    wrifile.write(info.getUsername() + " " + info.getPassword() + " " + info.getOtherusername() + " " + info.getStart() + " " + info.getEnd() + " " + info.getTitle() + " " + info.getMeetingID() + "\r\n");
                }
                writer.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean ClearAll(String username, String password) {
        try {
            FileReader file = new FileReader(new File(AgendaSave_PATH));
            BufferedReader reader = new BufferedReader(file);
            List<AgendaInfo> result = new LinkedList<AgendaInfo>();
            
            String content = reader.readLine();
            while (content != null) {
                String[] array = content.split(" ");
                if (array[0].equals(username)) {
                    content=reader.readLine();
                    continue;
                }
                AgendaInfo temp = new AgendaInfo(array[0], array[1], array[2], Integer.parseInt(array[3]), Integer.parseInt(array[4]), array[5], Integer.parseInt(array[6]));
                result.add(temp);
                content = reader.readLine();
            }
            try {
                FileWriter wrifile = new FileWriter(new File(AgendaSave_PATH));
                BufferedWriter writer = new BufferedWriter(wrifile);
                for (AgendaInfo info : result) {
                    wrifile.write(info.getUsername() + " " + info.getPassword() + " " + info.getOtherusername() + " " + info.getStart() + " " + info.getEnd() + " " + info.getTitle() + " " + info.getMeetingID() + "\r\n");
                }
                writer.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
