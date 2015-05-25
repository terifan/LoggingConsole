package org.terifan.logging_console;

import java.awt.BorderLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JPanel;
import javax.swing.JScrollBar;


public class LogView extends JPanel
{
	private LogModel mModel;
	private LogPanel mLogPanel;


	public LogView(LogModel aModel)
	{
		super(new BorderLayout());

		mModel = aModel;

		JScrollBar sb = new JScrollBar(JScrollBar.VERTICAL, 0, 41, 0, mModel.size());

		mLogPanel = new LogPanel(this, sb);

		sb.addAdjustmentListener(mAdjustmentListener);

		super.add(mLogPanel, BorderLayout.CENTER);
		super.add(sb, BorderLayout.EAST);
	}


	public LogModel getModel()
	{
		return mModel;
	}


	private transient AdjustmentListener mAdjustmentListener = (e) ->
	{
		mLogPanel.repaint();
	};
}
