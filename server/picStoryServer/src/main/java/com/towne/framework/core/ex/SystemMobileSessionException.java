package com.towne.framework.core.ex;

public class SystemMobileSessionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3311079303086486950L;

    public SystemMobileSessionException(String exception) {
        if(RuntimeExceptionThreadVo.getMessage()==null) {
            RuntimeExceptionThreadVo.setMessage(exception);
        }
    }

    @Override
    public String getMessage() {
        return RuntimeExceptionThreadVo.getMessage();
    }
	
}
