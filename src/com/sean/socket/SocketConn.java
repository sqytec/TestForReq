package com.sean.socket;

import org.apache.log4j.Logger;

public class SocketConn {
	private static final Logger logger = Logger.getLogger(SocketConn.class);
	private static SocketConn my_instance = new SocketConn();
	SocketClientConnection connection = null;
	Message respMsg = null;
	String msg = "";
	
	private SocketConn(){
		
	}

	public String sendAndRescive(String ip,Integer port,String msg) {
		System.out.println("hostName:"+ip+",port:"+port+"\r\n,msg:"+msg);
		try {
			socketConnect(ip,port);
			/*if (connection.isConnected()) {
				logger.info("socket is connected!");
				if (connection.sendMessage(msg)){
					logger.info("send message to socket!");
					respMsg = connection.getResponseMessage();
					logger.info("get message from socket:"+respMsg);
					if (!"".equals(respMsg)){
						logger.info(respMsg.getReturnMsg());
						return respMsg.getReturnMsg();
					}
					else{
						logger.info("empty content from socket!");
						return "empty content from socket!";
					}
				}
			}
			else{
				logger.info("connect is error!");
			}*/
		} catch (Exception e) {
			logger.info("error for connect socket!");
		}
		return "return msg";
	}
	
	private void socketConnect(String ip,Integer port) {
		// TODO Auto-generated method stub
		SocketClientConnection connection = new SocketClientConnection(ip, port);
		connection.connect();
	}
	
	public static SocketConn getInstance(){
		return my_instance;
	}
}
