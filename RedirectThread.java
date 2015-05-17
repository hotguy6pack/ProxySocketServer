package com.owen.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class RedirectThread implements Runnable 
{
	//Address of remote server that is being redirected to
	private Socket localSocket;
	public String skuraURL = "10.0.1.3";
	int skuraPort = 5451;
	
	public RedirectThread(Socket socket)
	{
		this.localSocket = socket;
	}

	@Override
	public void run()
	{
		try
		{
			//Create socket for remote server
			Socket skuraSocket = new Socket(skuraURL, skuraPort);
			
			//Input & Output from android device
			InputStream cIn = localSocket.getInputStream();
			OutputStream cOt = localSocket.getOutputStream();
			
			//Input & Output from remote server
			InputStream sIn = skuraSocket.getInputStream();
			OutputStream sOt = skuraSocket.getOutputStream();
			
			//Forward input from android to output of remote server and vise-versa
			ForwardThread forward = new ForwardThread(cIn, sOt, "Forwarding");
			ForwardThread retrieve = new ForwardThread(sIn, cOt, "Retrieving");
			
			System.out.println("Redirecting to: " + skuraURL + ":" + skuraPort);
			
			forward.start();
			retrieve.start();
		}
		catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
