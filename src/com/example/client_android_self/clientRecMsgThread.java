package com.example.client_android_self;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class clientRecMsgThread extends Thread {
	Socket socket =null;
	byte[] buffer = null;
	int len;
	String data = null;

	public clientRecMsgThread() {
		// TODO Auto-generated constructor stub
	}

	public clientRecMsgThread(Socket socket) {
		// super(runnable);
		// TODO Auto-generated constructor stub
		this.socket = socket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// super.run();
		// recv data from the give socket
		 try {
			InputStream in=  socket.getInputStream();
			BufferedInputStream buf = new BufferedInputStream(in); 
			
			
			while ((len =buf.read(buffer, 0,1024)) != -1)
			{
				data =  data + new String(buffer);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}

	

}
