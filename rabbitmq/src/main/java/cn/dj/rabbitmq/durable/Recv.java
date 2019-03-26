package cn.dj.rabbitmq.durable;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

import cn.dj.rabbitmq.util.ConnectionUtils;

/**
 * @ClassName: Recv
 * @Description: 消息的持久化性：消费者-消费消息
 * @author D
 * @date 2017年3月10日 上午12:37:51
 */
public class Recv {
	
	private static final String	QUEUE_NAME		= "simple_queue";
	private static final String	EXCHANGE_NAME	= "simple_exchange";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		//声明队列-并开启队列的持久化策略
		boolean durable = true;
		channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		DefaultConsumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
				System.out.println(" [Recv_1] - " + new String(body, "utf-8"));
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		channel.basicConsume(QUEUE_NAME, false, consumer);
	}
}