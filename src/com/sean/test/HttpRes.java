package com.sean.test;

import java.util.Properties;

import com.sean.socket.Configuration;
import com.sean.socket.SocketConn;

public class HttpRes {
	public static void main(String[] args) {
		// 报文数据
		//String msgBody = "00000481120205LGMBKPIBU805                                                          *CLIET                    20100305182030          SUC0000                                                                      02KPIBPHDRX 0001000150KPIBP805X 000100008630858400068910258400025320100305150330ccms.805.001.01     MSGID308201003050001REFID308201003050001UY         N                                        30858400068920100305CMBWKEID00000012010-03-12T17:30:30308584000689001100000001IBPSOT00";
		String msgBody = "GrpNam=&FchNum=&DalKey=";
		String intURL = "/onlineqry/ccf";
		CifMessage(msgBody,intURL);
	}

	public static String CifMessage(String msgBody,String intURL) {
		int msgLength = msgBody.length();
		/*System.out.println("length:" + msgLength);
		System.out.println("报文长度:" + msgBody.substring(0, 8));
		System.out.println("作业头:" + msgBody.substring(8, 213));
		int inCnt = Integer.parseInt(msgBody.substring(211, 213));
		System.out.println("接口个数:" + inCnt);
		System.out.println("作业接口:" + msgBody.substring(213, 213 + 20 * inCnt));
		System.out.println("first:" + msgBody.substring(253, 339));
		System.out.println(msgBody.substring(339));
		System.out.println("作业正文:" + msgBody.substring(213 + 20 * inCnt));
		char[] chars = msgBody.toCharArray();
		System.out.println(chars);*/

		String ipString = "";
		Integer portInteger = 80;
		String msg = "";
		SocketConn instance = SocketConn.getInstance();
		Properties properites = Configuration.getProperites();
		ipString = properites.getProperty("IP.HostName").trim();
		String portRef = properites.getProperty("HOST.Port").trim();
		portInteger = Integer.parseInt(portRef);
		/*
		  msg = "POST /cifServlet HTTP/1.1\r\n" + "Host: " + ipString + ":" +
		  portInteger + "\r\n" + "Connection: close\r\n" +
		  "Content-Type: text/plain\r\n" + "Content-Length: " + msgLength +
		  "\r\n" + "\r\n" + msgBody;
		 */
//		ipString = "99.12.70.201";
//		String intURL = "/onlineqry/ccf";
		ipString = "99.13.44.33";
		msg = "POST "+intURL+" HTTP/1.1\r\n" + "Host: " + ipString + ":"
				+ portInteger + "\r\n" + "Connection: close\r\n"
				+ "Content-Type: text/plain\r\n" + "Content-Length: "
				+ msgLength + "\r\n" + "\r\n" + msgBody;
		System.out.println("ip:" + ipString + ",port:" + portInteger
				+ ",msgBody:" + msgBody);
		System.out.println("----------------------------------------");
		System.out.println("msg:\r\n" + msg);
		
		long a = System.currentTimeMillis();
		System.out.println(a);
		String respMsg = instance.sendAndRescive(ipString, portInteger, msg);
		long b = System.currentTimeMillis();
		System.out.println(b);
		System.out.println(b-a);
		System.out.println("get msg from socket:" + respMsg);

		return "";
	}
	
}
