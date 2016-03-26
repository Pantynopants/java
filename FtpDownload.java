
import java.util.*;
import java.io.*;
import java.nio.file.Path;

import com.enterprisedt.*;
import com.enterprisedt.net.*;
import com.enterprisedt.net.ftp.*;

import com.enterprisedt.net.ftp.FTPClient;

public class FtpDownload {
	private FTPClient ftpClient;
	
	public FtpDownload (FTPClient ftpClient){
		this.ftpClient=ftpClient;
	}
	
	public void downloadFile(String localPath, String remoteFile) throws IOException,FTPException{
		ftpClient.get(localPath,remoteFile);
	}
	
	public void downloadDir(String localPath,String remoteDir) throws IOException,FTPException{
		
		String[] subFileNames = ftpClient.dir(remoteDir,true);
		
		ftpClient.chdir(remoteDir);
		
		String localDirName = localPath + "\\" + remoteDir;
		
		File localDir = new File(localDirName);
		
		localDir.mkdir();
		
		for (int i = 0; i < subFileNames.length; i++) {
			int lastIndex = subFileNames[i].lastIndexOf(":");
			subFileNames[i] = subFileNames[i].substring(lastIndex+4);
			if (subFileNames[i] == null || ".".equals(subFileNames[i]) || "..".equals(subFileNames[i])) {
				continue;
			}
			else if(subFileNames[i].indexOf(".") == -1){
				this.downloadDir(localDirName, subFileNames[i]);
			}else{
				this.downloadFile(localDirName, subFileNames[i]);
			}
		}
		
		ftpClient.cdup();
	}
}




