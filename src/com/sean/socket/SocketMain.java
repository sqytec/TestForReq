package com.sean.socket;

import java.util.Properties;

public class SocketMain {
	public static void main(String[] args) {
		String msgBody = "";
		String ipString = "";
		Integer portInteger = 80;
		SocketConn instance = SocketConn.getInstance();
		Properties properites = Configuration.getProperites();
		ipString = properites.getProperty("IP.HostName").trim();
		String portRef = properites.getProperty("HOST.Port").trim();
		portInteger = Integer.parseInt(portRef);
		System.out.println("ip:"+ipString+",port:"+portInteger+",msgBody:"+msgBody);
		String respMsg = instance.sendAndRescive(ipString,portInteger,msgBody);
		System.out.println("get msg from socket:"+respMsg);
	}
}
