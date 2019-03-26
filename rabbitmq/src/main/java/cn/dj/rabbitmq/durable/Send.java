package cn.dj.rabbitmq.durable;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import cn.dj.rabbitmq.util.ConnectionUtils;

/**
 * @ClassName: Send
 * @Description: 消息的持久化性：生产者发送消息
 * @author D
 * @date 2017年3月10日 上午12:15:39
 */
public class Send {
	
	//定义交换机名称
	private static final String EXCHANGE_NAME = "simple_exchange";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		//声明交换机-并开启交换机的持久化策略
		boolean durable = true;
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout", durable);
		String message = "durable_test";
		
		//传送模式-标记持久化
		int deliveryMode = 2;
		AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder().deliveryMode(deliveryMode).build();
		channel.basicPublish(EXCHANGE_NAME, "", basicProperties, message.getBytes("utf-8"));
		System.out.println(" [x] Sent '" + message + "'");
		channel.close();
		connection.close();
	}
}