package com.towne.framework.core.ex;

public class SystemMobileRuntimeException extends RuntimeException {
    /**
     * <pre>
     * 
     * </pre>
     */
    private static final long serialVersionUID = 8136266661435849877L;

    public SystemMobileRuntimeException(String exception) {
        if(RuntimeExceptionThreadVO.getMessage()==null) {
            RuntimeExceptionThreadVO.setMessage(exception);
        }
    }

    @Override
    public String getMessage() {
        return RuntimeExceptionThreadVO.getMessage();
    }

}
