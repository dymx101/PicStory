package com.towne.framework.system.filter.vo;

public class ThreadLocalLog {
	private static final ThreadLocal<MobileLogger> logs = new ThreadLocal<MobileLogger>();

	public static void setSystemLoggerVO(MobileLogger LoggerVO) {
		logs.set(LoggerVO);
	}

	public static MobileLogger getSystemLoggerVO() {
		MobileLogger vo = logs.get();
		if (vo == null) {
			vo = new MobileLogger();
			logs.set(vo);
			return vo;
		} else {
			return vo;
		}

	}

	public static void clearSystemLoggerVO() {
		logs.remove();
	}

}
