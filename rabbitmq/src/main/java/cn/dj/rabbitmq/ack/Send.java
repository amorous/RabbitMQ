package cn.dj.rabbitmq.ack;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import cn.dj.rabbitmq.util.ConnectionUtils;

/**
 * @ClassName: Send
 * @Description: 消息应答：生产者发送消息
 * @author D
 * @date 2017年3月10日 上午12:15:39
 */
public class Send {
	
	private static final String EXCHANGE_NAME = "simple_exchange";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout", false);
		String message = "ack_test";
		channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("utf-8"));
		System.out.println(" [x] Sent '" + message + "'");
		channel.close();
		connection.close();
	}
}