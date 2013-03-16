package com.towne.framework.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class StringUtil {
    
    public static boolean isNotEmpty(String args) {
        if(args!=null) {
            if(!args.trim().equals("")) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isNotEmpty(Object args) {
        if(args!=null) {
            if(!args.toString().equals("null")) {
                return true;
            }
        }
        return false;
    }

    public static String clearWidth(String input)
	{
		if(input == null)
			return "";
		String ret = "";
		int start =0;
		int pos = 0;
		while(pos >=0)
		{
			pos = input.indexOf(" width",start);
			if(pos <0){
				ret = ret+input.substring(start);
				break;
			}
			ret = ret+input.substring(start,pos );
			int p0 = pos + 6;
			pos = input.indexOf("=",p0);
			if(pos <0){
				ret = ret+input.substring(p0);
				break;
			}
			String s = input.substring(p0,pos);
			if(s == null )
				continue;
			if(s.trim().length() != 0)
			{
				ret = ret+input.substring(start,pos);
				start = pos;
				continue;
			}
			
			int p1 = pos+1;
			pos = input.indexOf("\"",p1);
			if(pos <0)
			{
				ret = ret+input.substring(start);
				break;
			}
			
			s = input.substring(p1,pos);
			if(s == null )
				continue;
			if(s.trim().length() != 0){
				ret = ret+input.substring(start,pos);
				start = pos;
				continue;
			}
			p1 = pos+1;
			pos = input.indexOf("\"",p1);
			if(pos <0)
			{
				ret = ret+input.substring(start);
				break;
			}
			
			s = input.substring(p1,pos);
			if(s == null )
				continue;
			
			start = pos +1;
		}
		return ret;
	}
    
    public static String notNull(String str) {
    	if (str == null) {
    		return "";
    	} else {
    		return str;
    	}
    }

	/**
	 * 根据提供的字符个数截取字符串，如果字符串字符数小于截取的个数则返回全部，如果大于截取个数则超过部分由more代替
	 * @author zhangwei5 2012-04-12 09:36
	 * @param str 截取的字符串
	 * @param toCount 截取的字符个数
	 * @param more 超过部分的替代字符串
	 * @return 截取后的字符串，如果字符串字符数小于截取的个数则返回全部，如果大于截取个数则超过部分由more代替
	 */
	 public static String substring(String str, int toCount,String more)
	    {
	      int reInt = 0;
	      String reStr = "";
	      if (str == null)
	        return "";
	      char[] tempChar = str.toCharArray();
	      int length =  tempChar.length;
	      for (int kk = 0; (kk < length && toCount > reInt); kk++) {
	        @SuppressWarnings("static-access")
			String s1 = str.valueOf(tempChar[kk]);
	        byte[] b;
			try {
				b = s1.getBytes("GBK");
				reInt += b.length;
				reStr += tempChar[kk];
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	      }
	      if (toCount == reInt || (toCount == reInt - 1)&&more!=null)
	        reStr += more;
	      return reStr;
	    }    
    
	
	/**
	 * 数据压缩
	 * @param data
	 * @return
	 */
	public  static byte[] dataCompress(byte[] data) {
		GZIPOutputStream gos;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			gos = new GZIPOutputStream(baos);
			
			byte[] buf = new byte[1024];
			int num;
			while ((num = bais.read(buf)) != -1) {
				gos.write(buf, 0, num);
			}
			gos.finish();
			gos.flush();
			gos.close();
			byte[] output = baos.toByteArray();  
			baos.close();
			return output;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 数据解压缩
	 * @param data
	 * @return
	 */
	public static byte[] dataDecompress(byte[] a){
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(a);
			GZIPInputStream gis = new GZIPInputStream(bais);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int num;
			while((num=gis.read(buf))!=-1){
				baos.write(buf, 0, num);
			}
			gis.close();
			byte[] ret = baos.toByteArray();
			baos.close();
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 数据解压缩
	 * @param in
	 * @param length
	 * @return
	 */
	public static byte[] dataDecompress(InputStream in,int length){
		if(length>0){
			try {
				GZIPInputStream gis = new GZIPInputStream(in);
				byte[] buf = new byte[length];
				gis.read(buf);
				gis.close();
				return buf;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return  null;
	}    
}
