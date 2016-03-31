

import java.util.*;
import java.io.*;
import com.enterprisedt.*;
import com.enterprisedt.net.*;
import com.enterprisedt.net.ftp.*;
public class FtpFactory {
	
	public static final String CST_FTP_HOST="127.0.0.1";
	
	public static final String CST_FTP_USER="admin";
	
	public static final String CST_FTP_PASSWORD="admin";
	
	private FTPClient ftpClient;
	
	private String ftpHost;
	
	private String ftpUser;
	
	private String ftpPassword;
	
	public FtpFactory () {
		this(CST_FTP_HOST,CST_FTP_USER,CST_FTP_PASSWORD);
		
	}
	
	public FtpFactory(String ftpHost, String ftpUser, String ftpPassword) {
		this.ftpHost = ftpHost;
		this.ftpUser = ftpUser;
		this.ftpPassword = ftpPassword;
		
	}
	
	public FTPClient getLoginedFtpClient() throws IOException,FTPException{
		if(this.ftpClient == null){
			init();
		}
		if(this.ftpClient.connected() == false){
			this.ftpClient.setRemoteHost(this.ftpHost);
			this.ftpClient.connect();
			this.ftpClient.login(this.ftpUser, this.ftpPassword);
			
		}
		return ftpClient;
	}
	
	public static void closeFtpClient(FTPClient ftpClient) throws IOException,FTPException{
		if(ftpClient != null || ftpClient.connected()){
			ftpClient.quit();
			ftpClient = null;
		}
	}
	
	private void init() throws IOException,FTPException{
		if(this.ftpClient == null){
			this.ftpClient = new FTPClient();
			this.ftpClient.setConnectMode(FTPConnectMode.PASV);
			
		}
	}
	
	public FTPClient getFtpClient() {
		return this.ftpClient;
	}
	
	public void setFtpClient(FTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}
	
	public String getFtpHost() {
		return this.ftpHost;
	}
	
	public void setFtpHost(String ftpHost) {
		this.ftpHost = ftpHost;
	}
	
	public String getFtpUser() {
		return this.ftpUser;
	}
	
	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}
	
	public String getFtpPassord() {
		return this.ftpPassword;
	}
	
	public void setFtpPassord(String ftpPassord) {
		this.ftpPassword = ftpPassord;
	}
}
