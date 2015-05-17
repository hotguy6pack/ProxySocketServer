package com.owen.test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class LocalThread implements Runnable
{
	//Local server socket on localhost:9090
	private ServerSocket localSocket;
	private String localURL = "127.0.0.1";
	private int localPort = 9090;
	
	
	public void run()
	{
		Socket socket = null;
		
		try
		{
			//bind the socket to port 9090
			SocketAddress socketAddress = new InetSocketAddress(localURL, localPort);
			localSocket = new ServerSocket();
			localSocket.bind(socketAddress);
			System.out.println("Listening on: " + localURL + ":" + localPort);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (!Thread.currentThread().isInterrupted())
		{
			try
			{
				//Listen for requests
				socket = localSocket.accept();
				System.out.println("Request accepted!");
				//Start thread for redirect
				Thread redThread = new Thread(new RedirectThread(socket));
				redThread.start();
				
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
}	

