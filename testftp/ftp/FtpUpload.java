package testftp.ftp;
import java.util.*;
import java.io.*;
import java.nio.file.Path;

import com.enterprisedt.*;
import com.enterprisedt.net.*;
import com.enterprisedt.net.ftp.*;

public class FtpUpload {
	private FTPClient ftpClient;
	
	public FtpUpload (FTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}
	
	public void uploadFile(String name) throws IOException,FTPException{
		String fileName = name.substring(name.lastIndexOf("\\")+1);
		
		ftpClient.put(name, fileName);
	}
	
	public void uploadDir(String dir) throws IOException,FTPException{
		String dirName = dir.substring(dir.lastIndexOf("\\")+1);
		
		ftpMkDir(dirName);
		
		String currentDir = ftpClient.pwd();
		
		ftpClient.chdir(dirName);
		
		String[] subFileNames = this.getDirList(dir);
		
		for (int i = 0; i < subFileNames.length; i++) {
			String tmpPath = dir + "\\" + subFileNames[i];
			File tmpFile = new File(tmpPath);
			
			if(tmpFile.isFile()){
				this.uploadFile(tmpPath);
			}else{
				this.uploadDir(tmpPath);
			}
		}
		ftpClient.chdir(currentDir);
	}

	private synchronized void ftpMkDir(String dirName) throws IOException,FTPException{
		// TODO Auto-generated method stub
		ftpClient.mkdir(dirName);
	}

	public String[] getDirList(String path) {
		// TODO Auto-generated method stub
		File file = new File(path);
		String[] subFileName = file.list();
		
		return subFileName;
	}
	
	
}
