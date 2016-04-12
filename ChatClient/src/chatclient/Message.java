package chatclient;

import java.util.*;


public class Message {

	private String from = null;
	private String to = null;
	private String type = null;
	private String content = null;
	private String time = null;
	
	public Message() {
	}
	
	public void setTo(String name) {
		to = name;
	}
	public String getTo() {
		return to;
		
	}
	
	
	public void setFrom(String string) {
		from = string;
	}
	public String getFrom() {
		return from;
	}


	public void setContent(String userstr) {
		content = userstr;
	}
	public String getContent() {
		return content;
	}
	
	public void setMessageType(String string) {
		type = string;
	}
	public String getMessageType() {
		return type;
	}
	
	public void setSendTime(Date date) {
		time = date.toString();
	}
	public String getSendTime() {
		return time;
		
	}

	
	

	

}
