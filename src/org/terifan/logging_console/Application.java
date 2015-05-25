package org.terifan.logging_console;

import javax.swing.JFrame;
import org.terifan.util.log.Log;


public class Application
{
	private LogView mLogView;
	private JFrame mFrame;


	public Application()
	{
		mLogView = new LogView(new LogModel());

		mFrame = new JFrame();
		mFrame.add(mLogView);
		mFrame.setSize(1024, 768);
		mFrame.setLocationRelativeTo(null);
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	public void start()
	{
		mFrame.setVisible(true);
	}


	public static void main(String ... args)
	{
		try
		{
			new Thread()
			{
				@Override
				public void run()
				{
					try
					{
						Application app = new Application();
						app.start();
					}
					catch (Throwable e)
					{
						e.printStackTrace(Log.out);
					}
				}
			}.start();

			Thread.sleep(1000);

			new Thread()
			{
				@Override
				public void run()
				{
					try
					{
					}
					catch (Throwable e)
					{
						e.printStackTrace(Log.out);
					}
				}
			}.start();
		}
		catch (Exception e)
		{
			e.printStackTrace(Log.out);
		}
	}
}
