package com.example.client_android_self;



import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;



import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	
	EditText msgtext;
	Button send;
	Button connect;
	TextView msgtxttotal;
	String msgStr;
	Socket socket = null;
	SocketAddress remoteAdr ;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		msgtext = (EditText) findViewById(R.id.msgtext);
	    send = (Button) findViewById(R.id.send);
	    connect = (Button) findViewById(R.id.connect);
	    msgtxttotal = (TextView) findViewById(R.id.msgtxttotal);
	    
	    connect.setOnClickListener(new OnClickListener() {
	    	 
            @Override
            public void onClick(View v) {
            	try {
					socket = new Socket();
					socket.connect(new InetSocketAddress("192.168.0.105", 30000), 5000);
			     	
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
       
            	
            new clientRecMsgThread().start();
            	
            }
        });
	    
	    send.setOnClickListener(new OnClickListener() {
	    	 
            @Override
            public void onClick(View v) {
            	msgStr = msgtext.getText().toString();
            	msgtxttotal.append("client:"+msgStr+"\n");
                //启动线程 向服务器发送和接收信息
              /*  new MyThread(geted1).start();*/
            	//send the msgstr to server by socket that already connnected ,connected after click the  connect buttton
            	OutputStream ou;
				try {
					ou = socket.getOutputStream();
					ou.write(msgStr.getBytes());
	            	ou.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            
            }
        });
	}
	
	public class clientRecMsgThread extends Thread {
	
		byte[] buffer = null;
		int len;
		String data = null;

		public clientRecMsgThread() {
			// TODO Auto-generated constructor stub
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
				msgtxttotal.append("server:"+data+"\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}

		

	}
}
