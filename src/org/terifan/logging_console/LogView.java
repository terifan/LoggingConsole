package org.terifan.logging_console;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;
import org.terifan.ui.Utilities;


public class LogView extends JPanel
{
	private Color mSeparatorColor = new Color(245,245,245);
	private Color mStrokeColor = new Color(128,128,128);
	private Stroke mLineStroke = new BasicStroke(1, 0, 0, 1, new float[]{1f}, 0f);

	private int mCounterWidth = 55;
	private int mTimeWidth = 90;
	private int mTypeWidth = 100;
	private int mTagWidth = 250;
	private int mIndentWidth = 50;
	private int mRowHeight = 17;
	private int mMaxLines = 100;

	private TreeMap<Integer,LogRow> mRows;
	private AtomicInteger mCounter;
	private JScrollBar mScrollBar;


	public LogView()
	{
		super(new BorderLayout());

		mCounter = new AtomicInteger();
		mRows = new TreeMap<>();

		mScrollBar = new JScrollBar(JScrollBar.VERTICAL, 0, 41, 0, Math.max(41, mRows.size()));
		mScrollBar.addAdjustmentListener(mAdjustmentListener);

		mLogPanel.setOpaque(true);
		mLogPanel.setBackground(Color.WHITE);
		mLogPanel.addComponentListener(mComponentAdapter);
		mLogPanel.addMouseWheelListener(mMouseAdapter);

		super.add(mLogPanel, BorderLayout.CENTER);
		super.add(mScrollBar, BorderLayout.EAST);
	}


	public void add(LogRow aLogRow)
	{
		if (mRows.size() > mMaxLines)
		{
			mRows.remove(mRows.firstKey());
		}

		SwingUtilities.invokeLater(()->
		{
			mRows.put(mCounter.getAndIncrement(), aLogRow);

			mScrollBar.setValue(Math.max(0, mScrollBar.getValue() - 1));
			mScrollBar.setMaximum(Math.max(mLogPanel.getHeight() / mRowHeight, mRows.size()));
			mScrollBar.invalidate();
			mLogPanel.invalidate();
			repaint();
		});
	}


	private JPanel mLogPanel = new JPanel()
	{
		@Override
		protected void paintComponent(Graphics aGraphics)
		{
			Graphics2D g = (Graphics2D)aGraphics;

			Utilities.enableTextAntialiasing(g);

			super.paintComponent(g);

			int w = getWidth();
			int h = getHeight();
			int y = 0;

			Stroke oldStroke = g.getStroke();

			int f = mRows.isEmpty() ? 0 : mRows.firstKey();

			for (int rowIndex = mScrollBar.getValue(); y < h && rowIndex < mRows.size(); rowIndex++)
			{
				y += mRowHeight;

				LogRow row = mRows.get(f + rowIndex);

				g.setColor(new Color(row.getColor()));
				g.drawString(Integer.toString(f + rowIndex), 0, y - 4);
				g.drawString(row.getTime(), mCounterWidth, y - 4);
				g.drawString(row.getLogType(), mCounterWidth + mTimeWidth, y - 4);
				g.drawString(row.getTag(), mCounterWidth + mTimeWidth + mTypeWidth, y - 4);
				g.drawString(row.getMessage(), mCounterWidth + mTimeWidth + mTypeWidth + mTagWidth + mIndentWidth * row.getDepth(), y - 4);

				g.setColor(mStrokeColor);
				g.setStroke(mLineStroke);
				for (int i = 0; i < row.getDepth(); i++)
				{
					int x = mCounterWidth + mTimeWidth + mTypeWidth + mTagWidth + mIndentWidth * i;
					g.drawLine(x, y - mRowHeight + 2, x, y - 1);
				}
				g.setStroke(oldStroke);

				g.setColor(mSeparatorColor);
				g.drawLine(0, y, w, y);
			}
		}


		@Override
		public Dimension getPreferredSize()
		{
			Dimension dim = super.getPreferredSize();
			dim.height = mRows.size() * mRowHeight;
			return dim;
		}
	};


	private transient AdjustmentListener mAdjustmentListener = aEvent ->
	{
		mLogPanel.repaint();
	};


	private ComponentAdapter mComponentAdapter = new ComponentAdapter()
	{
		@Override
		public void componentResized(ComponentEvent aEvent)
		{
			SwingUtilities.invokeLater(()->
			{
				int s = mLogPanel.getHeight() / mRowHeight;

				mScrollBar.setVisibleAmount(s);

				int v = mRows.size() - s;
				if (mScrollBar.getValue() > v)
				{
					mScrollBar.setValue(v);
				}
			});
		}
	};


	private MouseAdapter mMouseAdapter = new MouseAdapter()
	{
		@Override
		public void mouseWheelMoved(MouseWheelEvent aEvent)
		{
			mScrollBar.setValue(mScrollBar.getValue() + aEvent.getWheelRotation() * aEvent.getScrollAmount());
		}
	};
}
