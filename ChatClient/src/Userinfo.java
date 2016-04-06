
public class Userinfo {

	private String username = null;
	private String password = null;
	
	public Userinfo() {
	}
	
	public Userinfo(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getName() {
		return username;
	}
	
	public String getPass() {
		return password;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
