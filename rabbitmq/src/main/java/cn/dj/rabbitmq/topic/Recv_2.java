package cn.dj.rabbitmq.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

import cn.dj.rabbitmq.util.ConnectionUtils;

/**
 * @ClassName: Recv_2
 * @Description: Topics-主题模式：消费者2-消费消息
 * @author D
 * @date 2017年3月10日 上午12:37:51
 */
public class Recv_2 {
	
	//定义队列名称
	private static final String	QUEUE_NAME		= "topic_queue_2";
	//定义交换机名称
	private static final String	EXCHANGE_NAME	= "topic_exchange";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		//1、获取连接
		Connection connection = ConnectionUtils.getConnection();
		//2、声明信道
		Channel channel = connection.createChannel();
		//3、声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		//4、将队列绑定到交换机
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "user.#");
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		//5、定义队列的消费者
		DefaultConsumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
				//6、获取消息
				System.out.println(" [Recv_1] - " + new String(body, "utf-8"));
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		//7、监听队列-关闭自动确认
		channel.basicConsume(QUEUE_NAME, false, consumer);
	}
}