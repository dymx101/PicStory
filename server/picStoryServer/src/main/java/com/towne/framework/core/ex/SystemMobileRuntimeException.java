package com.towne.framework.core.ex;

public class SystemMobileRuntimeException extends RuntimeException {
    /**
     * <pre>
     * 
     * </pre>
     */
    private static final long serialVersionUID = 8136266661435849877L;

    public SystemMobileRuntimeException(String exception) {
        if(RuntimeExceptionThread.getMessage()==null) {
            RuntimeExceptionThread.setMessage(exception);
        }
    }

    @Override
    public String getMessage() {
        return RuntimeExceptionThread.getMessage();
    }

}
