package com.towne.framework.core.utils;

public class VersionUtil {
	public static int compare(String versionSrc,String versionDest)
	{
		String [] src = versionSrc.split("\\.");
		String [] dest = versionDest.split("\\.");
		for(int i=0;i<src.length;i++)
		{
			if(i == dest.length)
				return -1;
			try{
				int s = Integer.parseInt(src[i]);
				int d = Integer.parseInt(dest[i]);
				if(d > s)
					return 1;
				if(d < s)
					return -1;
			}catch(Exception e)
			{
				return 0;
			}
		}
		if(src.length == dest.length)
			return 0;
		return 1;
	}
}
