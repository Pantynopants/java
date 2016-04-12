import java.util.ArrayList;
import java.util.List;


public class MessageDAO {

	public static final ArrayList<Message> messageList = new ArrayList<Message>();
	
	//for receiver, such as server and another person
	public static List<Message> getMyMessage(String me) {
		if (messageList == null) {
			return null;
		}
		ArrayList<Message> message2meList = new ArrayList<Message>();
	
			//System.out.println(me + "'s message");
			for(Message temp :messageList){
	            if(temp.getTo().equals(me)){
	            	System.out.println(temp.getFrom());
	            	message2meList.add(temp);
	            }
	        }
			

		
		return message2meList;
	}

	public synchronized static boolean add(Message message) {

		messageList.add(message);
		return true;
	}
	
	public synchronized static boolean removeMyMessage(ArrayList<Message> myMessage) {
		
		if (messageList == null || myMessage == null) {
			return false;
		}
		for (int i = 0; i < myMessage.size(); i++) {
			for (int j = 0; j < messageList.size(); j++) {
				
				//get messages from other to me
				if (messageList.get(j) != null && myMessage.get(i) != null && messageList.get(j).getSendTime().equals(myMessage.get(i).getSendTime())) {
					messageList.remove(j);
				}
			}
		}

		return false;
		
	}
	
	public static boolean isEmpty() {
	
		//System.out.println(messageList.isEmpty());
		return messageList.isEmpty();
	}

	public static int getNumberOfMsg() {
		
		
		return messageList.size();
	}
	
}
