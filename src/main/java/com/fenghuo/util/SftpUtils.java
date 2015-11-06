package com.fenghuo.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 *  通过FPT复制web服务器文件至文件服务器
 */
public class SftpUtils {
	private static final Logger log = LoggerFactory.getLogger(SftpUtils.class);
	// linux上传地址
	private static String pathLinux = PropertiesUtils.getProp("path.linux");
	//图片服务器信息
	private static String[] host;
	private static String[] port;
	private static String[] username;
	private static String[] password;
	
	private static Session session = null;
	private static Channel channel = null;

	static{
		//初始化图片服务器信息
		host = PropertiesUtils.getProp("ftp.host").split(" ");
		username = PropertiesUtils.getProp("ftp.username").split(" ");
		password = PropertiesUtils.getProp("ftp.password").split(" ");
		port = PropertiesUtils.getProp("ftp.port").split(" ");
	}

	private static ChannelSftp getChannel(String username, String password, String host, int port) throws JSchException {
		JSch jsch = new JSch();
		session = jsch.getSession(username, host, port);
		session.setPassword(password);
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.setTimeout(60000);
		session.connect();

		channel = session.openChannel("sftp");
		channel.connect();
		return (ChannelSftp) channel;
	}

	private static void closeChannel() {
		if (channel != null) {
			channel.disconnect();
		}
		if (session != null) {
			session.disconnect();
		}
	}

	private static void mkdirs(ChannelSftp chSftp, String path) throws SftpException {
		String[] paths = path.split("/");
		path = "";
		for (int i = 0; i < paths.length; i++) {
			path += paths[i] + "/";
			try {
				chSftp.ls(path);
			} catch (SftpException e) {
				log.debug("create dir : " + path);
				chSftp.mkdir(path);
			}
		}
	}

	/**
	 * 把文件上传到ftp服务器上
	 * 
	 * @param src
	 *            文件的完整路径
	 *            (如/home/.../fileName.mp4)
	 * @return boolean
	 */
	public static boolean uploadFile(String src) {
		ChannelSftp chSftp = null;
		String path = null;

		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("win") >= 0) {
			path = src.substring(2, src.lastIndexOf("/"));
		} else if(os.contains("mac")){
		  	System.out.println("======" + src);
			path = src.substring(0, src.lastIndexOf("/"));
		}else {
			path = src.substring(0, src.lastIndexOf("/"));
		}
		
		// 循环读取主机信息进行操作
		for (int i = 0; i < host.length; i++) {
			try {
				chSftp = getChannel(username[i], password[i], host[i], Integer.parseInt(port[i]));
				mkdirs(chSftp, path);
				chSftp.put(src, path, ChannelSftp.OVERWRITE);
				if (i == (host.length - 1)) {
					return true;
				}
			} catch (SftpException e) {
				e.printStackTrace();
			} catch (JSchException e) {
				e.printStackTrace();
			} finally {
				chSftp.quit();
				closeChannel();
			}
		}
		return false;
	}

	/**
	 * 重命名ftp服务器上的文件
	 * 
	 * @param src
	 *            文件的完整路径
	 *            (如/home/.../temp_fileName.mp4)
	 * @param dest
	 *            文件的完整路径
	 *            (如/home/Java/.../fileName.mp4)
	 * @return boolean
	 */
	public static boolean renameFile(String src, String dest) {
		ChannelSftp chSftp = null;

		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("win") >= 0) {
			src = src.substring(2);
			dest = dest.substring(2);
		}
		
		// 循环读取主机信息进行操作
		for (int i = 0; i < host.length; i++) {
			try {
				chSftp = getChannel(username[i], password[i], host[i], Integer.parseInt(port[i]));
				chSftp.rename(src, dest);
				if (i == (host.length - 1)) {
					return true;
				}
			} catch (SftpException e) {
				e.printStackTrace();
			} catch (JSchException e) {
				e.printStackTrace();
			} finally {
				chSftp.quit();
				closeChannel();
			}
		}
		return false;
	}
	
	/**
	 * 文件服务器文件：删除
	 *
	 * @param filePath
	 *            文件相对路径
	 *           （数据库url,如/picture/.../*.suffix）
	 * @return Boolean
	 */
	public static boolean removeFile(String filePath) {
		filePath = pathLinux + filePath;	//将系统路径构建到文件路径
		ChannelSftp chSftp = null;
		
		// 循环读取主机信息进行操作
		for (int i = 0; i < host.length; i++) {
			try {
				chSftp = getChannel(username[i], password[i], host[i], Integer.parseInt(port[i]));
				chSftp.rm(filePath);
				if (i == (host.length - 1)) {
					return true;
				}
			} catch (SftpException e) {
				e.printStackTrace();
			} catch (JSchException e) {
				e.printStackTrace();
			} finally {
				chSftp.quit();
				closeChannel();
			}
		}
		return false;
	}
	
	/**
	 * 文件服务器文件：下载
	 *
	 * @param filePath
	 *            文件完整（绝对）路径
	 * @param webFilePath
	 *            Web服务器临时存放路径
	 * @return InputStream
	 */
	public static boolean downloadFile(String filePath, String webFilePath) {
		ChannelSftp chSftp = null;
	 
		// 循环读取主机信息进行操作
		for (int i = 0; i < host.length; i++) {
			try {
				chSftp = getChannel(username[i], password[i], host[i], Integer.parseInt(port[i]));
				File file = new File(webFilePath);
				chSftp.get(filePath, new FileOutputStream(file));
				if(file.exists()){
					return true;
				}
			} catch (SftpException e) {
				e.printStackTrace();
			} catch (JSchException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				chSftp.quit();
				closeChannel();
			}
		}
		return false;
	}
	
	/**
	 * 上传文件: 通过输入流上传至FTP
	 *
	 * @param inputStream
	 *            文件输入流
	 * @param filepath
	 *            文件服务器存放地址(绝对路径)
	 * @return Boolean
	 */
	public static boolean uploadFileForInputStream(InputStream inputStream, String filename) {
       boolean result = false;
       ChannelSftp chSftp = null;
		// 循环读取主机信息进行操作
		for (int i = 0; i < host.length; i++) {
			try {
				chSftp = getChannel(username[i], password[i], host[i], Integer.parseInt(port[i]));
				mkdirs(chSftp, filename.substring(0, filename.lastIndexOf("/")));
				chSftp.put(inputStream, filename);
				if (i == (host.length - 1)) {
					result = true;
				}
			} catch (SftpException e) {
				e.printStackTrace();
			} catch (JSchException e) {
				e.printStackTrace();
			} finally {
				chSftp.quit();
				closeChannel();
			}
		}
        return result;
	}
}