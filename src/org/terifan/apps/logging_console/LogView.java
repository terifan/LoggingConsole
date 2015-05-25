package org.terifan.apps.logging_console;

import java.awt.BorderLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;


public class LogView extends JPanel
{
	public LogView()
	{
		super(new BorderLayout());

		JScrollBar sb = new JScrollBar(JScrollBar.VERTICAL, 0, 48, 0, 1000);

		final LogPanel lp = new LogPanel(sb);

		sb.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e)
			{
				lp.repaint();
			}
		});

		super.add(lp, BorderLayout.CENTER);
		super.add(sb, BorderLayout.EAST);
	}
}
