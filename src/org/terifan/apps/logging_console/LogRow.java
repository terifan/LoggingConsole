package org.terifan.apps.logging_console;


public class LogRow
{
	private long mTime;
	private int mDepth;
	private String mTag;
	private String mMessage;
	private int mColor;
	private int mType;


	public LogRow(long aTime, int aType, int aColor, int aDepth, String aTag, String aMessage)
	{
		mTime = aTime;
		mDepth = aDepth;
		mTag = aTag;
		mMessage = aMessage;
		mColor = aColor;
		mType = aType;
	}


	public int getType()
	{
		return mType;
	}


	public int getColor()
	{
		return mColor;
	}


	public int getDepth()
	{
		return mDepth;
	}


	public long getTime()
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
