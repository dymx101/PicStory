package com.towne.hessian;



import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.server.HessianServlet;
import com.towne.data.mongodb.examples.hello.HelloMongo;

public class BasicAPIImpl extends HessianServlet implements BasicAPI {

	/**
	 * 
	 */
	@Autowired
	HelloMongo helloMongo;
	
	private static final long serialVersionUID = -2882289787227102745L;

	public String sayHello() {
		
		return helloMongo.run();
	}

}
