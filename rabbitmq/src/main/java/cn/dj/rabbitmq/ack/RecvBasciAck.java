package cn.dj.rabbitmq.ack;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

import cn.dj.rabbitmq.util.ConnectionUtils;

/**
 * @ClassName: RecvAutoAck
 * @Description: 消息应答-手动应答：消费者-消费消息
 * @author D
 * @date 2017年3月10日 上午12:37:51
 */
public class RecvBasciAck {
	
	private static final String	QUEUE_NAME		= "simple_queue";
	private static final String	EXCHANGE_NAME	= "simple_exchange";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		DefaultConsumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
				System.out.println(" [Recv_1] - " + new String(body, "utf-8"));
				//手动应答
				//deliveryTag：该消息的index
				//multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息
				channel.basicAck(envelope.getDeliveryTag(), false);
				//channel.basicNack(envelope.getDeliveryTag(), false, true);
				//channel.basicRecover(true);
				//channel.basicReject(envelope.getDeliveryTag(), true);
			}
		};
		//关闭自动应答机制
		boolean autoAck = false;
		channel.basicConsume(QUEUE_NAME, autoAck, consumer);
	}
}