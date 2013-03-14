package com.towne.framework.core.ex;

public class SystemMobileSessionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3311079303086486950L;

    public SystemMobileSessionException(String exception) {
        if(RuntimeExceptionThreadVO.getMessage()==null) {
            RuntimeExceptionThreadVO.setMessage(exception);
        }
    }

    @Override
    public String getMessage() {
        return RuntimeExceptionThreadVO.getMessage();
    }
	
}
