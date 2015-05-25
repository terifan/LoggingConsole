package org.terifan.apps.logging_console;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.terifan.util.log.Log;


public class GraphView
{
	private JFrame mFrame;
	private GraphPanel [] mPanels;


	public GraphView(int aMeasures)
	{
		this(new String[aMeasures]);
	}


	public GraphView(String ... aMeasures)
	{
		mPanels = new GraphPanel[aMeasures.length];
		for (int i = 0; i < aMeasures.length; i++)
		{
			mPanels[i] = new GraphPanel();
		}

		mFrame = new JFrame();
		mFrame.setLayout(new GridLayout(aMeasures.length,1));

		for (int i = 0; i < aMeasures.length; i++)
		{
			JPanel panel = new JPanel(new BorderLayout());
			JLabel label = new JLabel(aMeasures[i]==null?"Measure "+(1+i):aMeasures[i]);
			panel.setBackground(Color.BLACK);
			panel.setOpaque(true);
			label.setForeground(Color.GREEN);
			panel.add(label, BorderLayout.NORTH);
			panel.add(mPanels[i], BorderLayout.CENTER);

			mFrame.add(panel);

			if (i == 0)
			{
				mPanels[i].setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
			}
			else if (i == aMeasures.length-1)
			{
				mPanels[i].setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY));
			}
			else
			{
				mPanels[i].setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.GRAY));
			}
		}

		mFrame.setSize(1024, 150 * aMeasures.length);
		mFrame.setLocationRelativeTo(null);
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mFrame.setVisible(true);
	}


	public synchronized void addValue(int aMeasure, long aValue)
	{
		mPanels[aMeasure].mValues[mPanels[aMeasure].mCount % GraphPanel.CAPACITY] = aValue;
		mPanels[aMeasure].mCount++;

		mPanels[aMeasure].repaint();
	}


	public static void main(String ... args)
	{
		try
		{
			GraphView w = new GraphView(5);

			for (int i = 0; i < 100000; i++)
			{
				w.addValue(0, (int)(100*Math.sin(i/150.0)));
				w.addValue(1, (int)(100*Math.sin(i/200.0)));
				w.addValue(2, (int)(100*Math.sin(i/100.0)));
				w.addValue(3, (int)(100*Math.sin(i/50.0)));
				w.addValue(4, (int)(100*Math.random()*Math.sin(i/200.0)));

				Thread.sleep(10);
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace(Log.out);
		}
	}
}
