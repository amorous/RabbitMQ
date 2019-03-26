package cn.dj.rabbitmq.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import cn.dj.rabbitmq.util.ConnectionUtils;

/**
 * @ClassName: Send
 * @Description: 简单队列模型：生产者发送消息
 * @author D
 * @date 2017年3月9日 下午5:52:03
 */
public class Send {
	
	//定义队列名称
	private static final String QUEUE_NAME = "simple_queue";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		//1、获取连接
		Connection connection = ConnectionUtils.getConnection();
		//2、声明信道
		Channel channel = connection.createChannel();
		//3、声明(创建)队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		//4、定义消息内容
		String message = "Hello World!";
		//5、发布消息
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
		System.out.println(" [x] Sent'" + message + "'");
		//6、关闭通道
		channel.close();
		//7、关闭连接
		connection.close();
	}
}