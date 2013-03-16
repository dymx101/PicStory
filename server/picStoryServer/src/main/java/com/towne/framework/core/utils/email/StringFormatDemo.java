package com.towne.framework.core.utils.email;

/**
 * 讲解StringFormat用法
 * @Title: StringFormatDemo.java
 * @Package com.thomsonreuters.mra.utils.email
 * @Description: 讲解StringFormat用法
 * @author javaeye 
 * @date 2010-12-9 下午04:26:43
 * @version MRA V1.0
 */
import java.math.BigDecimal;

/**
 * 1.%[argument_index$][flags][width][.precision]conversion
 * 
 * String.format("%1$s",1234,5678); 指向第一个参数转换为字符串
 * String.format("%1$s%2$s",1234,5678);将两个参数格式化为字符串,并连接在一起
 * String.format("%s",1234,5678); 指向第一个参数转换为字符串
 * String.format("%s%06d",1234,5678); 将第一个格式化为“1234” 第二个格式化005678 w
 */
public class StringFormatDemo {

	/**
	 * 处理浮点型数据 应用范围float、Float、double、Double 和 BigDecimal
	 * %[argument_index$][flags][width][.precision]conversion
	 * %[index$][标识][最小宽度][保留精度]转换方式 标识： '-' 在最小宽度内左对齐，不可以与“用0填充”同时使用 '+'
	 * 结果总是包括一个符号 ' ' 正值前加空格，负值前加负号 '0' 结果将用零来填充 ',' 每3位数字之间用“，”分隔（只适用于fgG的转换）
	 * '(' 若参数是负数，则结果中不添加负号而是用圆括号把数字括起来（只适用于eEfgG的转换） 最小宽度: 最终该整数转化的字符串最少包含多少位数字
	 * 保留精度：保留小数位后面个数 转换方式： 'e', 'E' -- 结果被格式化为用计算机科学记数法表示的十进制数 'f' --
	 * 结果被格式化为十进制普通表示方式 'g', 'G' -- 根据具体情况，自动选择用普通表示方式还是科学计数法方式 'a', 'A' --
	 * 结果被格式化为带有效位数和指数的十六进制浮点数
	 * 
	 */
	public static void formatFloat() {
		System.out.println(String.format("%1$e", 1234567890.123456789));// 转换为科学记数法表示
		System.out.println(String.format("%1$020e", 1234567890.123456789));// 转换为科学记数法表示,长度为20,用0填充
		System.out.println(String.format("%1$g", 12345.123456789));// 根据结果制动识别使用转换器e或f
		System.out.println(String.format("%1$a", 12345.123456789));// 转换为16进制的浮点数
		System.out.println(String.format("%1$,f", 1234567890.123456789));// 转换结果保留默认小数位，3位数字用,隔开，转换为十进制表示
		System.out.println(String.format("%1$,f", 1234567890.123456789));// 转换结果保留默认小数位，3位数字用,隔开
		System.out.println(String.format("%1$.10f", 1234567890.123456789));// 转换结果是保留10位精度.转换成十进制表示方式
		System.out.println(String.format("%1$,.100f", new BigDecimal(
				"12345678909.1234567890123456789")));// 转换结果是保留100位精度,没有精度丢失,整数位3个就用,隔开
		System.out.println(String.format("%1$,.5f", 1234567890.123456789));// 转换结果保留5位小数，3位数字用,隔开
	}

	/**
	 * 处理整数型数据 应用范围 byte、Byte、short、Short、int、Integer、long、Long 和 BigInteger
	 * 
	 * %[argument_index$][flags][width]conversion %[index$][标识][最小宽度]转换方式 标识：
	 * '-' 在最小宽度内左对齐，不可以与“用0填充”同时使用 '#'
	 * 只适用于8进制和16进制，8进制时在结果前面增加一个0，16进制时在结果前面增加0x '+'
	 * 结果总是包括一个符号（一般情况下只适用于10进制，若对象为BigInteger才可以用于8进制和16进制） ' '
	 * 正值前加空格，负值前加负号（一般情况下只适用于10进制，若对象为BigInteger才可以用于8进制和16进制） '0' 结果将用零来填充 ','
	 * 只适用于10进制，每3位数字之间用“,”分隔 '(' 若参数是负数，则结果中不添加负号而是用圆括号把数字括起来（同‘+’具有同样的限制）
	 * 最小宽度: 最终该整数转化的字符串最少包含多少位数字 转换方式：d-十进制 o-八进制 x或X-十六进制
	 */
	public static void formatNumber() {
		System.out.println(String.format("%1$d", -31)); // 格式化成数值表示使用十进制，结果"-31"
		System.out.println(String.format("%1$o", -31)); // 格式化成数值表示使用八进制，结果"37777777741"
		System.out.println(String.format("%1$19d", -31));// 格式化成数值表示使用十进制，总长度显示19位结果"                -31"
		System.out.println(String.format("%1$-19d", -31));// 格式化成数值表示使用十进制，总长度显示19位,左靠齐结果"-31                "
		System.out.println(String.format("%1$09d", -31));// 格式化成数值表示,使用十进制，结果"-00000031"
		System.out.println(String.format("%1$,9d", -3123));// 每3位数字用,隔开,总长度9位，结果"   -3,123"
		System.out.println(String.format("%1$,09d", -3123));// 每3位数字用,隔开,用0填充总长度9位，结果"-0003,123"
		System.out.println(String.format("%1$(9d", -3123));// 每3位数字用,用0填充总长度9位，结果"     (3123)"
		System.out.println(String.format("%1$ 9d", -31));
	}

	/**
	 * 处理字符型数据 对字符进行格式化是非常简单的，c C表示字符，标识中'-'表示左对齐，其他就没什么了
	 */
	public static void formatChar() {
		System.out.println(String.format("%1$c", 97));// 转换为字符
		System.out.println(String.format("%1$10c", '鄒'));// 转换为字符,十位
		System.out.println(String.format("%1$-10c", '鸿'));// 转换为字符，十位，靠左
	}

	/**
	 * 格式化百分比.%特殊字符。转义格式为 %%而不是\
	 */
	public static void formatBaiFenBi() {
		System.out.println(String.format("%1$f%%", 12.123456));
		System.out.println(String.format("%1$.4f%%", 12.123456));// 留取4位小数,4舍5入
		BigDecimal a = new BigDecimal("12.12"), b = new BigDecimal("13.13");
		BigDecimal c = a.divide(b, 28, BigDecimal.ROUND_HALF_UP);// 保留28位小数
		System.out.println(c + "");
		System.out.println(String.format("%1$.28f", c));// 格式为保留28位小数
	}

	/**
	 * 获取独立平台行分隔符
	 */
	public static void getSeparator() {
		System.out.println(String.format("%n"));
		System.out.println(System.getProperty("line.separator"));
	}

	/**
	 * 格式化日期 (可用范围long,Long,Calendar,java.util.Date) %[index$][标识][最小宽度]转换方式 标识：
	 * 日期和时间转换字符的前缀 t或者T 转换方式： 格式化日期转换字符 'B' 特定于语言环境的月份全称，例如 "January" 和
	 * "February"。 'b' 特定于语言环境的月份简称，例如 "Jan" 和 "Feb"。 'h' 与 'b' 相同。 'A'
	 * 特定于语言环境的星期几全称，例如 "Sunday" 和 "Monday" 'a' 特定于语言环境的星期几简称，例如 "Sun" 和 "Mon"
	 * 'C' 除以 100 的四位数表示的年份，被格式化为必要时带前导零的两位数，即 00 - 99 'Y'
	 * 年份，被格式化为必要时带前导零的四位数（至少），例如，0092 等于格里高利历的 92 CE。 'y'
	 * 年份的最后两位数，被格式化为必要时带前导零的两位数，即 00 - 99。 'j'
	 * 一年中的天数，被格式化为必要时带前导零的三位数，例如，对于格里高利历是 001 - 366。 'm' 月份，被格式化为必要时带前导零的两位数，即
	 * 01 - 13。 'd' 一个月中的天数，被格式化为必要时带前导零两位数，即 01 - 31 'e' 一个月中的天数，被格式化为两位数，即 1 -
	 * 31。
	 * 
	 * 格式化时间字符 'H' 24 小时制的小时，被格式化为必要时带前导零的两位数，即 00 - 23。 'I' 12
	 * 小时制的小时，被格式化为必要时带前导零的两位数，即 01 - 12。 'k' 24 小时制的小时，即 0 - 23。 'l' 12
	 * 小时制的小时，即 1 - 12。 'M' 小时中的分钟，被格式化为必要时带前导零的两位数，即 00 - 59。 'S'
	 * 分钟中的秒，被格式化为必要时带前导零的两位数，即 00 - 60 （"60" 是支持闰秒所需的一个特殊值）。 'L'
	 * 秒中的毫秒，被格式化为必要时带前导零的三位数，即 000 - 999。 'N' 秒中的毫微秒，被格式化为必要时带前导零的九位数，即
	 * 000000000 - 999999999。 'p' 特定于语言环境的 上午或下午 标记以小写形式表示，例如 "am" 或 "pm"。使用转换前缀
	 * 'T' 可以强行将此输出转换为大写形式。 'z' 相对于 GMT 的 RFC 822 格式的数字时区偏移量，例如 -0800。 'Z'
	 * 表示时区缩写形式的字符串。Formatter 的语言环境将取代参数的语言环境（如果有）。 's' 自协调世界时 (UTC) 1970 年 1 月
	 * 1 日 00:00:00 至现在所经过的秒数，即 Long.MIN_VALUE/1000 与 Long.MAX_VALUE/1000 之间的差值。
	 * 'Q' 自协调世界时 (UTC) 1970 年 1 月 1 日 00:00:00 至现在所经过的毫秒数，即 Long.MIN_VALUE 与
	 * Long.MAX_VALUE 之间的差值 格式化时间组合字符 'R' 24 小时制的时间，被格式化为 "%tH:%tM" 'T' 24
	 * 小时制的时间，被格式化为 "%tH:%tM:%tS"。 'r' 12 小时制的时间，被格式化为 "%tI:%tM:%tS %Tp"。上午或下午标记
	 * ('%Tp') 的位置可能与语言环境有关。 'D' 日期，被格式化为 "%tm/%td/%ty"。 'F' ISO 8601
	 * 格式的完整日期，被格式化为 "%tY-%tm-%td"。 'c' 日期和时间，被格式化为 "%ta %tb %td %tT %tZ %tY"，例如
	 * "Sun Jul 20 16:17:00 EDT 1969"。
	 * 
	 */
	public static void formatDate() {
		long c = System.currentTimeMillis();
		System.out.println(String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS",
				c));
		System.out.println(String.format(
				"%1$ty-%1$tm-%1$td %1$tI:%1$tM:%1$tS %1$tp %1$tb %1$ta", c));
		System.out.println(String.format("%1$tF %1$tT", 1244943575031l));
	}

	/**
	 * 规转换可应用于任何参数类型 'b或B' '\u0062' 将生成 "true" 或 "false"， 如果参数为 null，则结果为
	 * "false"。如果参数是一个 boolean 值或 Boolean，那么结果是由 String.valueOf() 返回的字符串。否则结果为
	 * "true"。 'h或H' '\u0068' 生成一个表示对象的哈希码值的字符串。 如果参数 arg 为 null，则结果为
	 * "null"。否则，结果为调用 Integer.toHexString(arg.hashCode()) 得到的结果。 's或S' '\u0073'
	 * 生成一个字符串。 如果参数为 null，则结果为 "null"。如果参数实现了 Formattable，则调用其 formatTo
	 * 方法。否则，结果为调用参数的 toString() 方法得到的结果。
	 * 
	 * 
	 */
	public static void formatAny() {
		System.out
				.println(String.format("%b %b %b %b", null, "", "true", true));
		String pattern = "%1$s 在 %4$tF %4$tT 说了 \"%1$s 爱 %2$s %3$d 年\"";
		System.out.println(String.format(pattern, "mingming", "shuilian",
				10000, System.currentTimeMillis()));
	}

	public static void main(String[] args) {
		formatAny();
	}
}
