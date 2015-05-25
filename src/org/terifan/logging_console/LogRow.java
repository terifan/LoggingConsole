package org.terifan.logging_console;

import java.util.Date;
import org.terifan.util.bundle.Bundle;


public class LogRow
{
	private Date mTime;
	private int mDepth;
	private String mTag;
	private String mMessage;
	private LogType mLogType;
	private Bundle mExtra;


	public LogRow(Date aTime, LogType aLogType, int aDepth, String aTag, String aMessage, Bundle aExtra)
	{
		mTime = aTime;
		mLogType = aLogType;
		mDepth = aDepth;
		mTag = aTag;
		mMessage = aMessage;
		mExtra = aExtra;
	}


	public LogType getLogType()
	{
		return mLogType;
	}


	public int getDepth()
	{
		return mDepth;
	}


	public Date getTime()
	{
		return mTime;
	}


	public String getTag()
	{
		return mTag;
	}


	public String getMessage()
	{
		return mMessage;
	}


	public Bundle getExtra()
	{
		return mExtra;
	}
}
