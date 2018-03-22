package com.sean.test;

import java.net.Socket;

public class Test {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("99.1.2.73", 80);
//			Socket socket = new Socket("99.12.70.201", 80);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
