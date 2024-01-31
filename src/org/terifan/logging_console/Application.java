package org.terifan.logging_console;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.swing.JFrame;


public class Application
{
	private static LogView mLogView;

	private JFrame mFrame;


	public Application()
	{
		mLogView = new LogView();

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
						e.printStackTrace(System.out);
					}
				}
			}.start();

			Thread.sleep(1000);

			new Thread()
			{
				{
					setDaemon(true);
				}
				@Override
				public void run()
				{
					try
					{
						Random rnd = new Random();
						String[] tags = {"org.terifan.efs.core.BlockDevice","org.terifan.efs.core.FileStream","org.terifan.efs.hashtable.HashTable"};
						String[] logTypes = {"DEBUG", "VERBOSE", "INFO", "WARN", "ERROR", "SEVERE"};
						int[] colors = {0xD00000,0x00D000,0x0000D0,0xD000D0,0xD0D000,0x00D0D0};
						SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss.SSS");

						for (int i = 0, depth = 0; ; i++)
						{
							String tag = tags[rnd.nextInt(tags.length)];

							mLogView.add(new LogRow(timeFormat.format(new Date()), logTypes[i%6], depth, colors[i%6], tag, RandomSentenceGenerator.next()));

							depth = Math.min(Math.max(depth + rnd.nextInt(3) - 1, 0), 5);

							Thread.sleep(i < 100 ? 0 : 100);
						}
					}
					catch (Throwable e)
					{
						e.printStackTrace(System.out);
					}
				}
			}.start();
		}
		catch (Exception e)
		{
			e.printStackTrace(System.out);
		}
	}
}
