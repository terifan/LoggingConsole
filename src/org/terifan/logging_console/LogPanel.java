package org.terifan.logging_console;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.text.SimpleDateFormat;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import org.terifan.ui.Utilities;


public class LogPanel extends JPanel
{
	private Color mSeparatorColor = new Color(245,245,245);
	private Color mStrokeColor = new Color(128,128,128);
	private SimpleDateFormat mTimeFormat = new SimpleDateFormat("HH:mm:ss.SSS");
	private Stroke mLineStroke = new BasicStroke(1, 0, 0, 1, new float[]{1f}, 0f);

	private LogView mLogView;
	private JScrollBar mScrollBar;
	private Color[] mTextColors;

	private int mCounterWidth = 55;
	private int mTimeWidth = 90;
	private int mTypeWidth = 100;
	private int mTagWidth = 250;
	private int mIndentWidth = 50;
	private int mRowHeight = 17;


	public LogPanel(LogView aLogView, JScrollBar aScrollBar)
	{
		mScrollBar = aScrollBar;
		mLogView = aLogView;

		mTextColors = new Color[]
		{
			new Color(200,0,0),
			new Color(200,0,0),
			new Color(200,200,0),
			new Color(0,0,0),
			new Color(0,200,0),
			new Color(200,0,200)
		};

		super.setBackground(Color.WHITE);
		super.setOpaque(true);
	}


	@Override
	protected void paintComponent(Graphics aGraphics)
	{
		Graphics2D g = (Graphics2D)aGraphics;

		Utilities.enableTextAntialiasing(g);

		super.paintComponent(g);

		int w = getWidth();
		int h = getHeight();
		int y = 0;
		LogModel model = mLogView.getModel();

		Stroke oldStroke = g.getStroke();

		for (int rowIndex = mScrollBar.getValue(); y < h && rowIndex < model.size(); rowIndex++)
		{
			y += mRowHeight;

			LogRow row = model.get(rowIndex);

			g.setColor(mTextColors[row.getLogType().ordinal()]);

			g.drawString(Integer.toString(rowIndex), 0, y - 4);
			g.drawString(mTimeFormat.format(row.getTime()), mCounterWidth, y - 4);
			g.drawString(row.getLogType().name(), mCounterWidth + mTimeWidth, y - 4);
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
		dim.height = mLogView.getModel().size() * mRowHeight;
		return dim;
	}
}