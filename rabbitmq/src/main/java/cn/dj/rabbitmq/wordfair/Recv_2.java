package cn.dj.rabbitmq.wordfair;

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
 * @Description: Work队列模型：消费者2-消费消息
 * @author D
 * @date 2017年3月9日 下午6:46:14
 */
public class Recv_2 {
	
	//定义队列名称
	private static final String QUEUE_NAME = "work_queue_fair";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		//1、获取连接
		Connection connection = ConnectionUtils.getConnection();
		//2、声明信道
		Channel channel = connection.createChannel();
		//3、声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		//4、保证一次只处理一个
		int prefetchCount = 1;
		channel.basicQos(prefetchCount);
		//5、定义队列的消费者
		DefaultConsumer consumer = new DefaultConsumer(channel) {
			
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
				//7、获取消息
				System.out.println(new String(body, "utf-8"));
				//消费者2接收一条消息后休眠1000毫秒
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					//手动应答
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};
		//6、监听队列-关闭自动应答
		boolean autoAck = false;
		channel.basicConsume(QUEUE_NAME, autoAck, consumer);
	}
}