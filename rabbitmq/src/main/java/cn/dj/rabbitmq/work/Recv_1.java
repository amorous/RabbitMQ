package cn.dj.rabbitmq.work;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

import cn.dj.rabbitmq.util.ConnectionUtils;

/**
 * @ClassName: Recv_1
 * @Description: Work队列模型：消费者1-消费消息-自动应答
 * @author D
 * @date 2017年3月9日 下午6:34:37
 */
public class Recv_1 {
	
	//定义队列名称
	private static final String QUEUE_NAME = "work_queue";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		//1、获取连接
		Connection connection = ConnectionUtils.getConnection();
		//2、声明信道
		Channel channel = connection.createChannel();
		//3、声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		//4、定义队列的消费者
		DefaultConsumer consumer = new DefaultConsumer(channel) {
			
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
				//6、获取消息
				System.out.println(new String(body, "utf-8"));
				try {
					//消费者1接收一条消息后休眠10毫秒
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		//5、监听队列
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
}