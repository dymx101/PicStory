package com.towne.hessian;


import com.caucho.hessian.server.HessianServlet;

public class BasicAPIImpl extends HessianServlet implements BasicAPI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2882289787227102745L;

	public String sayHello() {
		return "Hello";
	}

}
