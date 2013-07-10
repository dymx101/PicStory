package com.store.mina.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaServer {

	/**
	 * @param args
	 */    
	public static void main(String[] args) {
		// 创建一个非阻塞的server端Socket ，用NIO
		SocketAcceptor acceptor = new NioSocketAcceptor();

		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		// 设定这个过滤器将以对象为单位读取数据
		ProtocolCodecFilter filter = new ProtocolCodecFilter(
				new ObjectSerializationCodecFactory());

//		chain.addLast("logger", new LoggingFilter());
//		chain.addLast("codec", new ProtocolCodecFilter(
//				new TextLineCodecFactory(Charset.forName("UTF-8"))));
		chain.addLast("threadPool",
				new ExecutorFilter(Executors.newCachedThreadPool()));
		chain.addLast("objectFilter", filter);

		// 设定服务器消息处理器
		acceptor.setHandler(ServerHanlder.getInstances());
		// 服务器绑定的端口
		int bindPort = 12211;
		// 绑定端口，启动服务器
		try {
			acceptor.bind(new InetSocketAddress(bindPort));
		} catch (IOException e) {
			System.out.println("Mina Server start for error!" + bindPort);
			e.printStackTrace();
		}
		System.out.println("Mina Server run done! on port:" + bindPort);
	}
}
