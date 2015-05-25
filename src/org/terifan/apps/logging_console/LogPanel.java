package org.terifan.apps.logging_console;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Random;
import java.util.TreeMap;
import javax.swing.JPanel;
import javax.swing.JScrollBar;


public class LogPanel extends JPanel
{
	private TreeMap<Integer,LogRow> mRows;
	private JScrollBar mScrollBar;
	private Color[][] mColors;


	public LogPanel(JScrollBar aScrollBar)
	{
		mRows = new TreeMap<>();
		mScrollBar = aScrollBar;

		Random rnd = new Random();
		String[] tags = {"Block device","File stream","Hash table"};
		ArrayDeque<Integer> stack = new ArrayDeque<>();

		int a = 250;
		int b = 245;
		mColors = new Color[][]
		{
			{new Color(a,255,a),new Color(b,255,b)},
			{new Color(255,a,a),new Color(255,b,b)},
			{new Color(a,a,255),new Color(b,b,255)}
		};

		for (int i = 0, depth = 0; i < 1000; i++)
		{
			int n = rnd.nextInt(3)-1;
			if (n > 0 && stack.size()<8 || stack.isEmpty())
			{
				int tagId = rnd.nextInt(tags.length);
				String tag = tags[tagId];
				stack.add(tagId);
				mRows.put(i, new LogRow(System.currentTimeMillis(), 0, tagId, depth, tag, RandomSentenceGenerator.next()));
				depth++;
			}
			else if (n < 0)
			{
				int tagId = stack.removeLast();
				mRows.put(i, new LogRow(System.currentTimeMillis(), 1, tagId, depth, "-----------------------"+tags[tagId], ""));
				depth--;
			}
			else
			{
				mRows.put(i, new LogRow(System.currentTimeMillis(), 2, stack.peekLast(), depth, "", RandomSentenceGenerator.next()));
			}
		}
	}


	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Font bold = g.getFont().deriveFont(Font.BOLD);
		Font plain = g.getFont();

		int y = 15;
		SimpleDateFormat df = new SimpleDateFormat("hh:MM:ss.SSS");

		for (int rowIndex = mScrollBar.getValue(); rowIndex < 1000; rowIndex++)
		{
			if (y > getHeight())
			{
				break;
			}

			LogRow row = mRows.get(rowIndex);

			if (row.getType() == 0)
			{
				g.setColor(mColors[row.getColor()][1]);
			}
			else
			{
				g.setColor(mColors[row.getColor()][0]);
			}
			g.fillRect(0, y-11, getWidth(), 14);
			g.setColor(Color.BLACK);

			g.drawString("" + rowIndex, 0, y);
			g.drawString(df.format(new Date(row.getTime())), 25, y);

			if (row.getType() == 2)
			{
				g.drawString(row.getMessage(), 125 + 50 * row.getDepth(), y);
			}
			else
			{
				g.drawString(row.getTag(), 125 + 50 * row.getDepth(), y);
				g.setFont(plain);
				g.drawString(row.getMessage(), 80+125 + 50 * row.getDepth(), y);
			}

			for (int i = 0; i < row.getDepth()-1; i++)
			{
				g.drawString(" |", 125 + 50 * i, y);
			}

			if (row.getDepth() > 0)
			{
				if (row.getType() == 1)
				{
					g.drawString(" \\-----------", 125 + 50 * (row.getDepth()-1), y);
				}
				else
				{
					g.drawString(" +----------", 125 + 50 * (row.getDepth()-1), y);
				}
			}

			y += 15;
		}
	}


	@Override
	public Dimension getPreferredSize()
	{
		return super.getPreferredSize();
	}
}
