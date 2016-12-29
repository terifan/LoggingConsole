package org.terifan.logging_console;


public class LogRow
{
	private String mTime;
	private int mDepth;
	private int mColor;
	private String mTag;
	private String mMessage;
	private String mLogType;


	public LogRow(String aTime, String aLogType, int aDepth, int aColor, String aTag, String aMessage)
	{
		mTime = aTime;
		mLogType = aLogType;
		mDepth = aDepth;
		mColor = aColor;
		mTag = aTag;
		mMessage = aMessage;
	}


	public String getLogType()
	{
		return mLogType;
	}


	public int getDepth()
	{
		return mDepth;
	}


	public int getColor()
	{
		return mColor;
	}


	public String getTime()
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
}
