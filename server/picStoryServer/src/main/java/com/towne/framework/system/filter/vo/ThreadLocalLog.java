package com.towne.framework.system.filter.vo;


public class ThreadLocalLog {
    private static final ThreadLocal<MobileLoggerVO> logs = new ThreadLocal<MobileLoggerVO>();

    public static void setSystemLoggerVO(MobileLoggerVO LoggerVO) {
        logs.set(LoggerVO);
    }

    public static MobileLoggerVO getSystemLoggerVO() {
    	MobileLoggerVO vo = logs.get();
        if(vo==null) {
            vo = new MobileLoggerVO();
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
