
import java.util.ArrayList;
import java.util.List;
	
public class FriendStatus {


	MainFrame mainFrame;
		
		public final ArrayList<Userinfo> friendList = new ArrayList<Userinfo>();
		
		public FriendStatus() {
			addUser();
		}
		
		public FriendStatus(MainFrame mainFrame) {
			this.mainFrame = mainFrame;
			addUser();
		}

		public ArrayList<Userinfo> getFriendList() {
			return this.friendList;
		}
		
		public boolean addUser(){
			
//			for (int i = 0; i < mainFrame.comboBox.getItemCount(); i++) {
//				Userinfo temp = new Userinfo(mainFrame.comboBox.getItemAt(i), "");
//				friendList.add(temp);
//			}
			
			Userinfo tom = new Userinfo("tom", "tom");
			friendList.add(tom);
			
			Userinfo lucy = new Userinfo("lucy", "lucy");
			friendList.add(lucy);
			
			Userinfo jack = new Userinfo("jack", "jack");
			friendList.add(jack);
			
			Userinfo allUser = new Userinfo("allUser", "");
			friendList.add(allUser);
			
			if (friendList.size() == 4) {
				return true;
			} else {
				return false;
			}
			
			
		}
	
	
		public void printFriendList() {
			
			if (friendList.size() != 4) {
				addUser();
			}
			
				for (int i = 0; i < friendList.size(); i++) {
					System.out.println(friendList.get(i).getName());
				}
			}
		
	

}
