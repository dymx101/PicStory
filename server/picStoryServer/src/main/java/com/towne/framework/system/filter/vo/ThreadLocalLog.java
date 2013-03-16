package com.towne.framework.system.filter.vo;

public class ThreadLocalLog {
	private static final ThreadLocal<MobileLoggerVo> logs = new ThreadLocal<MobileLoggerVo>();

	public static void setSystemLoggerVO(MobileLoggerVo LoggerVO) {
		logs.set(LoggerVO);
	}

	public static MobileLoggerVo getSystemLoggerVO() {
		MobileLoggerVo vo = logs.get();
		if (vo == null) {
			vo = new MobileLoggerVo();
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
