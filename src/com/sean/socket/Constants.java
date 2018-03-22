package com.sean.socket;

public class Constants {


	// buffer size for receiving socket message
	
	public final static int RETURN_SUCCESS = 1;//成功
	
	public final static int RETURN_FAIL = -1;//失败
	
	public final static int BUFFER_SIZE = 1024;

	public final static int CLIENT_HOUSEKEEP_INTERVAL = 1 * 1000;

	public final static int SERVER_HOUSEKEEP_INTERVAL = 1 * 1000;

	public final static int RECONNECT_INTERVAL = 3 * 1000;

	// define timeout of socket operation in millisecond
	public static final int SOCKET_TIMEOUT = 1000 * 30;

	// socket transfer encoding type
	public static final String TRANSFER_ENCODING = "UTF-8";
	public static final String DEFAULT_ENCODING = "utf-8";
	public static final String SPLITFLAG = "\\|#\\*\\|";
	public static final String LINESEPARATOR = System.getProperty("line.separator");
	public static final String ALGORITHM = "AES";
	public static final String ALGORITHMMODE = "AES/ECB/PKCS5Padding";
//	public static final String BREAKLINE = "\n\r";
}
