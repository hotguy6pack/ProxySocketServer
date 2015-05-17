package com.owen.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ForwardThread extends Thread
{
	private static final int READ_BUFFER_SIZE =12000;
	
	private InputStream mInputStream = null;
	private OutputStream mOutputStream = null;
	private String action;
	
	public ForwardThread(InputStream aInputStream, OutputStream aOutputStream, String action)
	{
		this.mInputStream = aInputStream;
		this.mOutputStream = aOutputStream;
		this.action = action;
	}


	public void run()
	{
		byte[] buffer = new byte[READ_BUFFER_SIZE];
		
		try
		{
			while (true)
			{
				//forward bytes from mInputeStream to mOutputStream
				int bytesRead = mInputStream.read(buffer);
				System.out.println(action + ": " + bytesRead + " bytes");
				if (bytesRead == -1)
				{
					break;
				}
				mOutputStream.write(buffer, 0, bytesRead);
			}
		
	}catch (IOException e)
		{
		
		}
		connectionBroken();
	}
	
	//close the streams when the transfer is done.
	public void connectionBroken()
	{
		try
		{
			mInputStream.close();
			mOutputStream.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
