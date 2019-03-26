package cn.dj.rabbitmq.ps;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import cn.dj.rabbitmq.util.ConnectionUtils;

/**
 * @ClassName: Send
 * @Description: Publish/Subscribe-发布订阅模型-生产者发送消息
 * @author D
 * @date 2017年3月10日 上午12:15:39
 */
public class Send {
	
	//定义交换机名称
	private static final String EXCHANGE_NAME = "fanout_exchange";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		//1、获取连接
		Connection connection = ConnectionUtils.getConnection();
		//2、声明信道
		Channel channel = connection.createChannel();
		//3、声明交换机
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		//4、定义消息内容
		String message = "注册成功";
		//5、发布消息
		channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("utf-8"));
		System.out.println(" [x] Sent '" + message + "'");
		//6、关闭信道
		channel.close();
		//7、关闭连接
		connection.close();
	}
}