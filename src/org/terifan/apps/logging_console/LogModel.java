package org.terifan.apps.logging_console;

import java.util.Random;
import java.util.TreeMap;


public class LogModel
{
	private TreeMap<Integer,LogRow> mRows;

	public LogModel()
	{
		mRows = new TreeMap<>();

		Random rnd = new Random();
		String[] tags = {"org.terifan.efs.core.BlockDevice","org.terifan.efs.core.FileStream","org.terifan.efs.hashtable.HashTable"};

		for (int i = 0, depth = 0; i < 1000000; i++)
		{
			int tagId = rnd.nextInt(tags.length);
			mRows.put(i, new LogRow(System.currentTimeMillis(), tagId, 0, depth, tags[tagId], RandomSentenceGenerator.next()));
			depth = Math.min(Math.max(depth + rnd.nextInt(3) - 1, 0), 5);
		}
	}


	public LogRow get(int aIndex)
	{
		return mRows.get(aIndex);
	}


	public int size()
	{
		return mRows.size();
	}
}
