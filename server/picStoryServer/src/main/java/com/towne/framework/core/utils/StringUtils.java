package com.towne.framework.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public abstract class StringUtils {
	public static int length(String string, String charset) {
		if (string == null)
			return 0;
		try {
			return string.getBytes(charset).length;
		} catch (UnsupportedEncodingException ex) {
		}
		return string.length();
	}

	public static String left1(String string, String charset, int len) {
		if (string == null)
			return null;
		if (charset == null) {
			return string.substring(0, len);
		}
		if (length(string, charset) < len) {
			return string;
		}
		StringBuffer sb = new StringBuffer();
		String str1 = string.substring(0, len / 2);
		sb.append(str1);
		int rl = length(str1, charset);
		int index = str1.length();
		while (rl < len) {
			char c = string.charAt(index++);
			rl += length(String.valueOf(c), charset);
			if (rl > len)
				break;
			sb.append(c);
		}
		return sb.toString();
	}

	public static String left2(String string, String charset, int len) {
		if (string == null)
			return null;
		if (charset == null)
			return string.substring(0, len);
		byte[] bytes;
		try {
			bytes = string.getBytes(charset);
		} catch (UnsupportedEncodingException ex) {
			System.out.println(ex.getMessage());
			return string.substring(0, len);
		}

		int i = len - 1;
		for (; i >= 0; --i) {
			if (bytes[i] > 0)
				break;
		}
		if ((len - i) % 2 == 1) {
			return new String(bytes, 0, len);
		}
		return new String(bytes, 0, len - 1);
	}

	public static int countUnquoted(String string, char character) {
		if ('\'' == character) {
			throw new IllegalArgumentException(
					"Unquoted count of quotes is invalid");
		}

		if (string == null) {
			return 0;
		}

		int count = 0;
		int stringLength = string.length();
		boolean inQuote = false;
		for (int indx = 0; indx < stringLength; ++indx) {
			char c = string.charAt(indx);
			if (inQuote) {
				if ('\'' == c)
					inQuote = false;
			} else if ('\'' == c)
				inQuote = true;
			else if (c == character) {
				++count;
			}
		}
		return count;
	}

	public static String qualifier(String qualifiedName) {
		int loc = qualifiedName.lastIndexOf(".");
		return ((loc < 0) ? "" : qualifiedName.substring(0, loc));
	}

	public static String unqualify(String qualifiedName) {
		return qualifiedName.substring(qualifiedName.lastIndexOf(".") + 1);
	}

	public static String join(String seperator, String[] strings) {
		int length = strings.length;
		if (length == 0)
			return "";
		if (length == 1)
			return strings[0];
		StringBuffer buf = new StringBuffer(length * strings[0].length())
				.append(strings[0]);

		for (int i = 1; i < length; ++i) {
			buf.append(seperator).append(strings[i]);
		}
		return buf.toString();
	}

	public static String fillToString(String fillString, String separator,
			int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; ++i) {
			if (i > 0)
				sb.append(separator);
			sb.append(fillString);
		}
		return sb.toString();
	}

	public static String encodeHex(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < bytes.length; ++j) {
			int b = bytes[j] & 0xFF;
			if (b < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(b));
		}
		return sb.toString();
	}

	public static byte[] decodeHex(String hex) {
		if (hex.length() % 2 != 0) {
			throw new IllegalArgumentException();
		}
		char[] arr = hex.toCharArray();
		byte[] b = new byte[hex.length() / 2];
		int i = 0;
		int j = 0;
		for (int l = hex.length(); i < l; ++j) {
			String swap = "" + arr[(i++)] + arr[i];
			int byteint = Integer.parseInt(swap, 16) & 0xFF;
			b[j] = new Integer(byteint).byteValue();

			++i;
		}

		return b;
	}

	public static String checkCritiria(String wokselect1, String example1,
			String wokoper1, String wokselect2, String example2,
			String wokoper2, String wokselect3, String example3) {
		StringBuffer strbuff = new StringBuffer();
		if (org.apache.commons.lang.StringUtils.isNotBlank(example1)) {
			strbuff.append(wokselect1);
			strbuff.append("=(");
			strbuff.append(example1);
			strbuff.append(")");
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(example2)) {
			String temp = strbuff.toString();
			if (org.apache.commons.lang.StringUtils.isNotBlank(temp)) {
				strbuff.append(" " + wokoper1 + " ");
				strbuff.append(wokselect2);
				strbuff.append("=(");
				strbuff.append(example2);
				strbuff.append(")");
			} else {
				strbuff.append(wokselect2);
				strbuff.append("=(");
				strbuff.append(example2);
				strbuff.append(")");
			}
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(example3)) {
			String temp1 = strbuff.toString();
			if (org.apache.commons.lang.StringUtils.isNotBlank(temp1)) {
				strbuff.append(" " + wokoper2 + " ");
				strbuff.append(wokselect3);
				strbuff.append("=(");
				strbuff.append(example3);
				strbuff.append(")");
			} else {
				strbuff.append(wokselect3);
				strbuff.append("=(");
				strbuff.append(example3);
				strbuff.append(")");
			}
		}
		return strbuff.toString();
	}

	public static boolean isNotEmpty(String args) {
		if (args != null) {
			if (!args.trim().equals("")) {
				return true;
			}
		}
		return false;
	}

	public static boolean isNotEmpty(Object args) {
		if (args != null) {
			if (!args.toString().equals("null")) {
				return true;
			}
		}
		return false;
	}

	public static String clearWidth(String input) {
		if (input == null)
			return "";
		String ret = "";
		int start = 0;
		int pos = 0;
		while (pos >= 0) {
			pos = input.indexOf(" width", start);
			if (pos < 0) {
				ret = ret + input.substring(start);
				break;
			}
			ret = ret + input.substring(start, pos);
			int p0 = pos + 6;
			pos = input.indexOf("=", p0);
			if (pos < 0) {
				ret = ret + input.substring(p0);
				break;
			}
			String s = input.substring(p0, pos);
			if (s == null)
				continue;
			if (s.trim().length() != 0) {
				ret = ret + input.substring(start, pos);
				start = pos;
				continue;
			}

			int p1 = pos + 1;
			pos = input.indexOf("\"", p1);
			if (pos < 0) {
				ret = ret + input.substring(start);
				break;
			}

			s = input.substring(p1, pos);
			if (s == null)
				continue;
			if (s.trim().length() != 0) {
				ret = ret + input.substring(start, pos);
				start = pos;
				continue;
			}
			p1 = pos + 1;
			pos = input.indexOf("\"", p1);
			if (pos < 0) {
				ret = ret + input.substring(start);
				break;
			}

			s = input.substring(p1, pos);
			if (s == null)
				continue;

			start = pos + 1;
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
	 * 
	 * @author zhangwei5 2012-04-12 09:36
	 * @param str
	 *            截取的字符串
	 * @param toCount
	 *            截取的字符个数
	 * @param more
	 *            超过部分的替代字符串
	 * @return 截取后的字符串，如果字符串字符数小于截取的个数则返回全部，如果大于截取个数则超过部分由more代替
	 */
	public static String substring(String str, int toCount, String more) {
		int reInt = 0;
		String reStr = "";
		if (str == null)
			return "";
		char[] tempChar = str.toCharArray();
		int length = tempChar.length;
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
		if (toCount == reInt || (toCount == reInt - 1) && more != null)
			reStr += more;
		return reStr;
	}

	/**
	 * 数据压缩
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] dataCompress(byte[] data) {
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
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] dataDecompress(byte[] a) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(a);
			GZIPInputStream gis = new GZIPInputStream(bais);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int num;
			while ((num = gis.read(buf)) != -1) {
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
	 * 
	 * @param in
	 * @param length
	 * @return
	 */
	public static byte[] dataDecompress(InputStream in, int length) {
		if (length > 0) {
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
		return null;
	}
}