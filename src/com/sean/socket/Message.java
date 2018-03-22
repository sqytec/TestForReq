package com.sean.socket;

public class Message {
	
	int returnCode;

	String returnMsg;
	
	public Message() {
	}

	public Message(int returnCode,String returnMsg) {
		this.returnCode = returnCode;
		this.returnMsg = returnMsg;
	}


	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	
}
