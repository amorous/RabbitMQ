package cn.dj.rabbitmq.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @ClassName: ConnectionUtils
 * @Description: 获取连接的工具类
 * @author D
 * @date 2017年3月9日 下午6:27:55
 */
public class ConnectionUtils {
	
	/**
	 * @Title: getConnection
	 * @Description: 获取连接
	 */
	public static Connection getConnection() throws IOException, TimeoutException {
		//1、定义连接工厂
		ConnectionFactory connectionFactory = new ConnectionFactory();
		//2、设置服务器地址
		connectionFactory.setHost("127.0.0.1");
		//3、设置端口
		connectionFactory.setPort(5672);
		//4、设置VirtualHost、用户名及密码
		connectionFactory.setVirtualHost("/virtual_test");
		connectionFactory.setUsername("djtest");
		connectionFactory.setPassword("djtest");
		//5、获取并返回连接
		return connectionFactory.newConnection();
	}
}
