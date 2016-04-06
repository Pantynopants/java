import java.util.ArrayList;
import java.util.List;


public class UserinfoDAO {

	
	public static final ArrayList<Userinfo> userList = new ArrayList<Userinfo>();
	
	public UserinfoDAO() {
		addUser();
	}
	
	public static boolean addUser(){
		Userinfo tom = new Userinfo("tom", "tom");
		userList.add(tom);
		
		Userinfo lucy = new Userinfo("lucy", "lucy");
		userList.add(lucy);
		
		Userinfo jack = new Userinfo("jack", "jack");
		userList.add(jack);
		
		Userinfo allUser = new Userinfo("allUser", "");
		userList.add(allUser);
		
		if (userList.size() == 4) {
			return true;
		} else {
			return false;
		}
		
		
	}
	
	public static boolean validateUser(String name, String pass) {
		if (userList.size() == 4) {
			for (int i = 0; i < userList.size(); i++) {
				if (userList.get(i).getName().equals(name) && userList.get(i).getPass().equals(pass)) {
					return true;
				}
			}
		} else {
			addUser();
			for (int i = 0; i < userList.size(); i++) {
				if (userList.get(i).getName().equals(name) && userList.get(i).getPass().equals(pass)) {
					return true;
				}
			}
		}
		
		return false;
	}

	public static void getUserList() {
		
		if (userList.size() != 3) {
			addUser();
		}
		
			for (int i = 0; i < userList.size(); i++) {
				System.out.println(userList.get(i).getName());
			}
		}
	
}
