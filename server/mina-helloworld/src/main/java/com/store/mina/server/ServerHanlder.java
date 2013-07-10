package com.store.mina.server;

import org.apache.mina.core.service.IoHandlerAdapter;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.store.mina.object.UserInfo;

public class ServerHanlder extends IoHandlerAdapter {

    private static ServerHanlder samplMinaServerHandler = null;  
    
    public static ServerHanlder getInstances() {  
        if (null == samplMinaServerHandler) {  
            samplMinaServerHandler = new ServerHanlder();  
        }  
        return samplMinaServerHandler;  
    }  
  
    private ServerHanlder() {  
  
    } 
    
	private int count = 0;

	// 当一个新客户端连接后触发此方法.
	public void sessionCreated(IoSession session) {
		System.out.println("created session");
	}

	// 当一个客端端连结进入时 @Override
	public void sessionOpened(IoSession session) throws Exception {
		count++;
		System.out.println("no. " + count + " address： : "
				+ session.getRemoteAddress());
	}

	// 当客户端发送的消息到达时:
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
        if (message instanceof UserInfo) {  
            UserInfo text = (UserInfo) message;  
            System.out.println("服务器接收到从客户端的姓名："+text.getName());  
            System.out.println("服务器接收到从客户端的QQ："+text.getQQNum());  
        }   
	}

	// 当信息已经传送给客户端后触发此方法.
	@Override
	public void messageSent(IoSession session, Object message) {
		System.out.println("message had been sent to client");

	}

	// 当一个客户端关闭时
	@Override
	public void sessionClosed(IoSession session) {
		System.out.println("one Clinet Disconnect !");
	}

	// 当连接空闲时触发此方法.
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) {
		System.out.println("IDLE " + session.getIdleCount(status));
	}

	// 当接口中其他方法抛出异常未被捕获时触发此方法
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		System.out.println("exception error");
	}

}
