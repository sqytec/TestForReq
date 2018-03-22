package com.sean.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import org.apache.log4j.Logger;

public class SocketClientConnection {

	private static final Logger logger = Logger.getLogger(SocketClientConnection.class);

	private static String hostName;

	private static int portNumber;

	private Socket socket;

	private boolean connected;
	
	private BufferedReader reader;

	private BufferedWriter writer;
	/*private static SocketClientConnection instance = null;
	private static final SocketClientConnection instance = new SocketClientConnection(hostName,portNumber);
	*/
//	private SocketClientConnection(){}

	public SocketClientConnection(String hostName, int portNumber) {
		System.out.println("SocketClientConnection hostName:"+hostName+",port:"+portNumber);
		setHostName(hostName);
		setPortNumber(portNumber);
		connected = false;
	}
	/*private SocketClientConnection(String hostName, int portNumber) {
		System.out.println("-----------------------------------outbound initial socketClientConnection,hostName:"+hostName+",portNumber:"+portNumber);
		setHostName(hostName);
		setPortNumber(portNumber);
		connected = false;
	}*/

	public void connect() {
		try {
			System.out.println("-----------hostName:"+hostName+",--------------portNumber:"+portNumber);
			this.socket = new Socket(hostName, portNumber);
			this.socket.setSoTimeout(Constants.SOCKET_TIMEOUT);
			this.connected = true;
			this.reader = new BufferedReader(new InputStreamReader(this.socket
					.getInputStream(),Constants.TRANSFER_ENCODING));
			this.writer = new BufferedWriter(new OutputStreamWriter(this.socket
					.getOutputStream(), Constants.TRANSFER_ENCODING));
			logger.info("Connected to socket server "+hostName+":"+portNumber+", local port: "+socket.getLocalPort());
		} catch (Exception e) {
			logger.error("Error in connecting to socket server "+hostName+":"+portNumber);
			logger.error(e);
			this.connected = false;
		}
	}

	/**
	 * Re-Connect if connect failed
	 * Not use in phase 1
	 */
//	public void reconnect() {
//		disconnect();
//		connect();
//	}

	public boolean isConnected() {
		return connected;
	}

	public void disconnect() {
		try {
			if(writer!=null){
				writer.close();
			}
			if(reader!=null){
				reader.close();
			}
			if(socket!=null){
				socket.close();
			}
			this.connected = false;
		} catch (IOException e) {
			logger.error("Error in closing IOException.\n\t" + e.toString());
			logger.error(e);
		}catch (Exception e1) {
			logger.error("Error in closing socket connection.\n\t" + e1.toString());
			logger.error(e1);			
		}
	}

	/**
	 * Send message to socket server
	 * @param message
	 * @return if success return true; else false
	 */
	public boolean sendMessage(String message) {
		boolean successflag = false;
		try {
			writer.write(message);
			writer.newLine();
			writer.flush();
			logger.info("Message sent to socket server: " + message);
			successflag = true;
			// Wait 0.3s for this flush
			Thread.sleep(Constants.RECONNECT_INTERVAL);
		} catch (UnsupportedEncodingException e3) {
			logger.error("Error in encoding stream to "
					+ Constants.TRANSFER_ENCODING
					+ "for sending to socket server!\n\t" + e3.toString());
			logger.error(e3);
		} catch (IOException e4) {
			logger.error("Error in sending the message to socket server!\n\t"
					+ e4.toString());
			logger.error(e4);
		} catch (Exception e5) {
			logger.error(e5.toString());
			logger.error(e5);
		} 
		return successflag;
			
	}
	


	public Message getResponseMessage() {
		Message respMessage = null;
		String exceptionMsg = null;
		try {
			String response = "";
			String tempStr = "";
			//*********************
			char[] bytes = new char[1024];
			int read = 0;
			while((read=reader.read(bytes, 0, 1024))!=-1){
			    tempStr=new String(bytes,0,read);
			    response += tempStr;
			    tempStr = null;
			}
			logger.info("Message got from socket server: " + response);
			if(response==null || "".equals(response.trim())){
				exceptionMsg = "Error in getting message from socket server! Server return null value!";
				respMessage = createMessage(Constants.RETURN_FAIL,exceptionMsg);
			}else{
				respMessage = createMessage(Constants.RETURN_SUCCESS,response);
			}
		} catch (UnsupportedEncodingException e3) {
			exceptionMsg = "Error in encoding stream to "
					+ Constants.TRANSFER_ENCODING
					+ " received from socket server!" + e3.toString();
			respMessage = createMessage(Constants.RETURN_FAIL,exceptionMsg);
			logger.error(exceptionMsg );
			logger.error(e3);
		} catch (IOException e4) {
			exceptionMsg ="Error in reading stream from socket server!"+ e4.toString();
			respMessage = createMessage(Constants.RETURN_FAIL,exceptionMsg);
			logger.error(exceptionMsg);
			logger.error(e4);
		} catch (NullPointerException e5) {
			exceptionMsg ="Error, socket is null! " + e5.toString();
			respMessage = createMessage(Constants.RETURN_FAIL,exceptionMsg);
			logger.error(exceptionMsg);
			logger.error(e5);
		} catch (Exception e6) {
			exceptionMsg ="Unknow Exception! " + e6.toString();
			respMessage = createMessage(Constants.RETURN_FAIL,exceptionMsg);
			logger.error(exceptionMsg);
			logger.error(e6);
		}
		return respMessage;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}
	
	
//	public Message getMessage() {
//	InputStream inputStream = null;
//	try {
//		String response = "";
//		String tempStr = "";
//		//*********************
//		byte[] bytes=new byte[1024];
//		inputStream = socket.getInputStream();
//		int read = 0;
//		while((read=inputStream.read(bytes))!=-1){
//		    tempStr=new String(bytes,0,read);
//		    System.out.println("tempStr="+tempStr);
//		    response += tempStr;
//		    tempStr = null;
//		}
//		logger.info("Message got from socket server: " + response);
//		if(response==null){
//			logger.error("Error in getting message from socket server! Server return null value!\n\t");
//			return null;
//		}
//		Message msg = new Message(Constants.RETURN_SUCCESS,response);
//		msg.setReturnCode(Constants.RETURN_SUCCESS);
//		msg.setReturnMsg(response);
//		return msg;
//		
//	} catch (UnsupportedEncodingException e3) {
//		logger.error("Error in encoding stream to "
//				+ Constants.TRANSFER_ENCODING
//				+ " received from socket server!\n\t" + e3.toString());
//		logger.debug(e3);
//		String exceptionMsg = getExceptionMsg();
//		Message msg = new Message(Constants.RETURN_FAIL,exceptionMsg);
//		msg.setReturnCode(Constants.RETURN_FAIL);
//		msg.setReturnMsg(exceptionMsg);
//		return msg;
//	} catch (IOException e4) {
//		logger.error("Error in reading stream from socket server!\n\t"
//				+ e4.toString());
//		logger.debug(e4);
//		/**
//		 * 
//		 * if read from socket server IOException, try to reconnect and
//		 * return the reconnect information to host
//		 */
//		logger.error("Get message from socket server error, reconnecting");
//		String exceptionMsg = getExceptionMsg();
//		Message msg = new Message(Constants.RETURN_FAIL,exceptionMsg);
//		msg.setReturnCode(Constants.RETURN_FAIL);
//		msg.setReturnMsg(exceptionMsg);
//		return msg;
//	} catch (NullPointerException e5) {
//		logger.error("Error, socket is null!\n\t" + e5.toString());
//		logger.debug(e5);
//		String exceptionMsg = getExceptionMsg();
//		Message msg = new Message(Constants.RETURN_FAIL,exceptionMsg);
//		msg.setReturnCode(Constants.RETURN_FAIL);
//		msg.setReturnMsg(exceptionMsg);
//		return msg;
//	} catch (Exception e6) {
//		logger.error(e6.toString());
//		logger.debug(e6);
//		String exceptionMsg = getExceptionMsg();
//		Message msg = new Message(Constants.RETURN_FAIL,exceptionMsg);
//		msg.setReturnCode(Constants.RETURN_FAIL);
//		msg.setReturnMsg(exceptionMsg);
//		return msg;
//	}finally {
//		//关闭资源
//		try {
//			if(inputStream!=null){
//				inputStream.close();
//			}
////			reader.close();
//			writer.close();
//			disconnect();
//		} catch (IOException e) {
//			logger.error("Error in closing IOException.\n\t" + e.toString());
//			logger.debug(e);
//		}catch (Exception e1) {
//			logger.error("Error in closing socket connection.\n\t" + e1.toString());
//			logger.debug(e1);
//		}
//	}
//}
	
	/**
	 * New message object
	 * @param returnCode
	 * @param returnMsg
	 * @return
	 */
	private Message createMessage(int returnCode,String returnMsg){
		Message message = new Message();
		message.setReturnCode(returnCode);
		message.setReturnMsg(returnMsg);
		return message;
		
	}
	
	/*public static SocketClientConnection getInstance(String ip, int port){
		System.out.println("----------------------------get outbound getInstance!");
		if (instance==null){
			System.out.println("instance is not exists!");
			instance =new SocketClientConnection(ip,port); 
		}
		return instance;
	}*/
	
}
