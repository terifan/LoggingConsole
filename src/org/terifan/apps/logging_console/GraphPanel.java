package org.terifan.apps.logging_console;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;


class GraphPanel extends JPanel
{
	final static int CAPACITY = 1000;

	private final static Color mBackground = Color.BLACK;
	private final static Color mGridColor = new Color(0,80,0);
	private final static Color mMeasureColor = new Color(0,160,0);
	private final static Color mFillColor = new Color(0,255,0,32);

	long [] mValues;
	int mCount;


	public GraphPanel()
	{
		mValues = new long[CAPACITY];
	}


	@Override
	protected void paintComponent(Graphics g)
	{
		g.setColor(mBackground);
		g.fillRect(0, 0, getWidth(), getHeight());

		int prev = 0;
		int gf = 6;
		int dx = 2;
		int gd = dx * gf;
		int w = getWidth();
		int h = (getHeight()/gd)*gd;
		int ys = getHeight()-h-1;

		g.setColor(mGridColor);

		for (int y = 0; y <= h; y += gd)
		{
			g.drawLine(0, ys+y, w, ys+y);
		}
		for (int x = w + dx - dx * (mCount % gf); x > 0; x -= gd)
		{
			g.drawLine(x, ys, x, ys+h);
		}

		long max = Long.MIN_VALUE;
		long min = 0; //Long.MAX_VALUE;
		for (int i = mCount, j = 0, n = w/dx; --i >= 0 && j < n; j++)
		{
			long v = mValues[i % CAPACITY];
			max = Math.max(max, v);
			min = Math.min(min, v);
		}

		long range = max - min;

		if (range == 0)
		{
			range = 1;
		}

		g.setColor(mMeasureColor);
		g.drawString(""+max, 5, 15);
		g.drawString(""+min, 5, h-5);

		int [] xp = new int[w/dx + 100];
		int [] yp = new int[w/dx + 100];
		int n = 0;

		for (int i = mCount, j = 0, x = w + dx; x > 0 && --i >= 0 && j < CAPACITY; j++, x -= dx)
		{
			int v = (int)((mValues[i % CAPACITY] - min) * h / range);

			if (j > 0)
			{
				g.drawLine(x, ys+h-prev, x-dx, ys+h-v);

				xp[n] = x-dx;
				yp[n] = ys+h-v;
				n++;
			}

			prev = v;
		}

		if (n > 1)
		{
			xp[n] = xp[n - 1];
			yp[n] = ys+h;
			n++;

			xp[n] = w;
			yp[n] = ys+h;
			n++;

			g.setColor(mFillColor);
			g.fillPolygon(xp, yp, n);
		}
	}
}