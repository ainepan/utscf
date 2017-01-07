package com.ut.comm.tool;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.ut.comm.tool.string.StringUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;

public class FtpUtil {
	public static final String ANONYMOUS_LOGIN = "anonymous";
	private FTPClient ftp;
	private boolean is_connected;

	int defaultTimeout = 30 * 1000;
	int connectTimeout = 30 * 1000;
	int dataTimeout = 30 * 1000;
	String charset = "GBK";
	private String backupPath;

	public FtpUtil() {
		ftp = new FTPClient();
		is_connected = false;
	}

	public FtpUtil(int defaultTimeoutSecond, int connectTimeoutSecond, int dataTimeoutSecond) {
		ftp = new FTPClient();
		is_connected = false;
		defaultTimeout = defaultTimeoutSecond * 1000;
		connectTimeout = connectTimeoutSecond * 1000;
		dataTimeout = dataTimeoutSecond * 1000;
	}

	public void connect(String host, int port, String user, String password, boolean isTextMode, String charset)
			throws IOException {
		try {
			ftp.setDefaultTimeout(defaultTimeout);
			ftp.setConnectTimeout(connectTimeout);
			ftp.setDataTimeout(dataTimeout);
			ftp.connect(host, port);
			if (charset != null && charset.length() > 0) {
				this.charset = charset;
			}
		} catch (UnknownHostException ex) {
			throw new IOException("Can't find FTP server '" + host + "'");
		}
		int reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			disconnect();
			throw new IOException("Can't connect to server '" + host + "'");
		}
		if (user == "") {
			user = ANONYMOUS_LOGIN;
		}

		if (!ftp.login(user, password)) {
			is_connected = false;
			disconnect();
			throw new IOException("Can't login to server '" + host + "'");
		} else {
			is_connected = true;
		}
		if (isTextMode) {
			ftp.setFileType(FTP.ASCII_FILE_TYPE);
		} else {
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
		}
		ftp.setControlEncoding(this.charset);
		ftp.setBufferSize(1024);
	}

	public void upload(String remotePath, String fileName, String localPath,String localBackup) throws Exception {
		File localFile = new File(localPath + File.separator + fileName);
		if (!localFile.exists()) {
			throw new IOException("Can't upload '" + localFile.getAbsolutePath() + "'. This file doesn't exist.");
		}

		if (localFile.isDirectory()) {
			String[] fileList = localFile.list();
			for (String string : fileList) {
				remotePath = remotePath + "\\" + localFile.getName();
				localPath = localPath + "\\" + localFile.getName();
				localBackup = localBackup + "\\" + localFile.getName();
				upload(remotePath, string, localPath,localBackup);
			}
			return;
		}
		
		boolean succcess = false;
		
		InputStream in = null;
		try {
			String remotePath1 = remotePath.contains("\\\\")?remotePath.replaceAll("\\\\", File.separator):remotePath;
			ftp.enterLocalPassiveMode();
//			makeDirectory(remotePath1);
			remotePath1 = new String(remotePath.getBytes(charset), "iso-8859-1");
			// ftp.changeWorkingDirectory(File.separator);
			ftp.changeWorkingDirectory(File.separator + remotePath1);
			in = new BufferedInputStream(new FileInputStream(localFile));
			String fileName1 = new String(fileName.getBytes(charset), "iso-8859-1");
			if (!ftp.storeFile(fileName1, in)) {
				throw new IOException("Can't upload file '" + fileName
						+ "' to FTP server. Check FTP permissions and path.");
			}else{
				if(StringUtil.isNotEmpty(localBackup)){
					
				} 
				succcess = true;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException ex) {
			}
		}
		
		if(succcess){
			if(StringUtil.isTrimNotEmpty(localBackup)){
				FileUtil.upload(localFile.getName(), localBackup, localFile);
			}
			if(!localFile.delete()){
				System.out.println("delete file failed...");
			}
		}
	}

	public void download(String remotePath, String fileName, String localPath,String validate) throws Exception {
		try {
			File file = new File(localPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			File localFile = new File(localPath + File.separator + fileName);
			String remotePath1 = remotePath.replaceAll("\\\\", File.separator);
			remotePath1 = new String(remotePath.getBytes(charset), "iso-8859-1");
			String fileName1 = new String(fileName.getBytes(charset), "iso-8859-1");

			ftp.changeWorkingDirectory(File.separator + remotePath1);
			ftp.enterLocalPassiveMode();

			FTPFile[] fileInfoArray = ftp.listFiles();
			if (fileInfoArray == null || fileInfoArray.length == 0) {
				throw new FileNotFoundException("File " + fileName + " was not found on FTP server.");
			}

			for (FTPFile ftpFile : fileInfoArray) {
				if (ftpFile.isDirectory()) {
					remotePath1 = remotePath1 + File.separator + ftpFile.getName();
					localPath = localPath + File.separator + ftpFile.getName();
					ftp.changeWorkingDirectory(File.separator);
					String[] remoteNames = getFileNames(remotePath1);
					ftp.changeWorkingDirectory(File.separator);
					for (String name : remoteNames) {
						download(remotePath1.substring(1), name, localPath);
					}

				} else if (ftpFile.isFile()) {
					OutputStream out = null;
					try {
						FTPFile fileInfo = fileInfoArray[0];
						long size = fileInfo.getSize();
						if (size > Integer.MAX_VALUE) {
							throw new IOException("File " + fileName + " is too large.");
						}

						out = new FileOutputStream(localFile);
						if (!ftp.retrieveFile(fileName1, out)) {
							throw new IOException("Error loading file " + fileName
									+ " from FTP server. Check FTP permissions and path.");
						}
						out.flush();
					} catch (Exception e) {
						throw e;
					} finally {
						if (out != null) {
							try {
								out.close();
							} catch (IOException ex) {
							}
						}
					}

				} else {

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
		}
	}
	
	public void download(String remotePath, String fileName, String localPath) throws IOException {
		try {
			File file = new File(localPath);
			if (file.exists()) {
				file.mkdirs();
			}
			File localFile = new File(localPath + File.separator + fileName);
			String remotePath1 = remotePath.replaceAll("\\\\", File.separator);
			remotePath1 = new String(remotePath.getBytes(charset), "iso-8859-1");
			String fileName1 = new String(fileName.getBytes(charset), "iso-8859-1");

			ftp.changeWorkingDirectory(File.separator + remotePath1);
			ftp.enterLocalPassiveMode();

			FTPFile[] fileInfoArray = ftp.listFiles(fileName1);
			if (fileInfoArray == null || fileInfoArray.length == 0) {
				throw new FileNotFoundException("File " + fileName + " was not found on FTP server.");
			}

			FTPFile fileInfo = fileInfoArray[0];
			long size = fileInfo.getSize();
			if (size > Integer.MAX_VALUE) {
				throw new IOException("File " + fileName + " is too large.");
			}
			OutputStream out = null;
			try{
				out = new FileOutputStream(localFile);
				if (!ftp.retrieveFile(fileName1, out)) {
					throw new IOException("Error loading file " + fileName
							+ " from FTP server. Check FTP permissions and path.");
				}
				out.flush();
			}catch(IOException e){
				throw e;
			}finally{
				if (out != null) {
					try {
						out.close();
					} catch (IOException ex) {
					}
				}
			}
			
		} finally {
			
		}
	}

	public void remove(String remoteFile) throws IOException {
		remoteFile = remoteFile.replaceAll("\\\\", File.separator);
		String remoteFile1 = new String(remoteFile.getBytes(charset), "iso-8859-1");
		if (!ftp.deleteFile(remoteFile1)) {
			throw new IOException("Can't remove file '" + remoteFile + "' from FTP server.");
		}
	}

	public void disconnect() throws IOException {

		if (ftp.isConnected()) {
			try {
				ftp.logout();
				ftp.disconnect();
				is_connected = false;
			} catch (IOException ex) {
			}
		}
	}

	public boolean isConnected() {
		return is_connected;
	}

	public void makeDirectory(String directoryname) throws Exception {
		directoryname = directoryname.replaceAll("\\\\", File.separator);
		String[] list = directoryname.split(File.separator);
		String parentPath = "";
		for (String string : list) {
			string = new String(string.getBytes(charset), "iso-8859-1");
			ftp.changeWorkingDirectory(parentPath);
			ftp.makeDirectory(string);
			parentPath += File.separator + string;
		}
		ftp.changeWorkingDirectory(File.separator);
	}

	public String[] getFileNames(String path) throws Exception {
		OutputStream out = null;
		String[] fileNameList = null;
		try {
			String remotePath1 = path.replaceAll("\\\\", File.separator);
			remotePath1 = new String(path.getBytes(charset), "iso-8859-1");

			ftp.changeWorkingDirectory(remotePath1);
			fileNameList = ftp.listNames();
		} catch (Exception ex) {
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException ex) {
				}
			}
		}
		return fileNameList;
	}

	public static void main(String[] args) throws Exception {
		FtpUtil ftpUtil = new FtpUtil();
//		ftpUtil.connect("22.196.66.8", 21, "ftpuser", "abc@123", false, "");
//		ftpUtil.upload("UpLoadSuppliers", "供应商信息初始化.xls", "D:\\logs\\20151224");
//		ftpUtil.download("UpLoadSuppliers", "供应商信息初始化.xls", "D:\\logs\\20151224");
//		ftpUtil.disconnect();
		File f = new File("D:\\temp\\local\\cust\\pamt");
		ftpUtil.deleteFile(f);
	}
	
	public void deleteFile(File f){
		if(f.delete()){
			System.out.println("success"+f.getAbsolutePath());
		}else{
			File[] list = f.listFiles();
			for (File string : list) {
//				File sfile = new File(string);
				deleteFile(string);
			}
			f.delete();
		}
	}
	
	public void retrieve(String remote, String local, String validate,boolean deleteOnSuccess) throws Exception {
		try {
			String serverpath = remote;

			String localpath = local;

			serverpath = gbkToIso8859(serverpath);

//			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);

			downLoadFile(serverpath, localpath, validate,deleteOnSuccess);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String gbkToIso8859(String serverpath) throws Exception {
		String string = new String(serverpath.getBytes(charset), "iso-8859-1");
		return string;
	}

	private void downLoadFile(String remote, String local, String validate, boolean deleteOnSuccess) {
		try {
			// 转到指定下载目录
			ftp.changeWorkingDirectory(remote);

			FTPFile[] files = ftp.listFiles();

			for (FTPFile file : files) {
				if (file.isDirectory()) {
					ftp.changeWorkingDirectory(File.separator);
					downLoadFile(remote  + File.separator+ file.getName()+ File.separator, local + File.separator + file.getName()+ File.separator, validate,deleteOnSuccess);
				} else {
					ftp.changeWorkingDirectory(File.separator+remote);
					local = gbkToIso8859(local);
					
					File localFile = new File(local);

					if (!localFile.exists()) {
						localFile.mkdirs();
					}

					if (StringUtil.isTrimEmpty(validate)||validate.indexOf(getSuffix(file.getName()))>-1) {
						OutputStream out = null;
						String tempName =  file.getName()+"_tmp";
						String localTmpFileName = local + File.separator + tempName;
						File localTmpFile = new File(localTmpFileName);
						try {
							//先重命名，然后下载，防止大文件重复下载
							if(ftp.rename(file.getName(),tempName)){
//								out = new FileOutputStream(localFile);
								out = new FileOutputStream(localTmpFile);
								if (!ftp.retrieveFile(tempName, out)) {
									throw new IOException("Error loading file " + file.getName()
											+ " from FTP server. Check FTP permissions and path.");
								}else{
									if(deleteOnSuccess){
										if(!ftp.deleteFile(file.getName())){
											System.out.println("delete failed...");
											String bkName =  file.getName()+"_bk";
											if(!ftp.rename(tempName,bkName)){
												System.out.println("remote rename failed...");
											}else{
												System.out.println("remote rename success...");
											}
										}
									}else{
										String bkName =  file.getName()+"_bk";
										if(!ftp.rename(tempName,bkName)){
											System.out.println("remote rename failed...");
										}else{
											System.out.println("remote rename success...");
										}
									}
								}
								out.flush();
							}
							
						}catch(IOException e){
							throw e;
						}finally{
							if (out != null) {
								try {
									out.close();
								} catch (IOException ex) {
								}
							}
						}
						//更新本地文件名
						localFile = new File(local + File.separator + gbkToIso8859(file.getName()));
						if(localTmpFile.renameTo(localFile)){
							System.out.println("local rename success...");
						}else{
							System.out.println("local rename failed...");
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getSuffix(String fileName) throws Exception {
		if(StringUtil.isTrimEmpty(fileName)||fileName.indexOf(".")<0){
			return "";
		}
		
		return fileName.substring(fileName.indexOf(".")+1);
	}

	public String getBackupPath() {
		return backupPath;
	}

	public void setBackupPath(String backupPath) {
		this.backupPath = backupPath;
	}
}
