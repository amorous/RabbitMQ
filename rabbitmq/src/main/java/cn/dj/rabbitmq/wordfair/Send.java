package cn.dj.rabbitmq.wordfair;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import cn.dj.rabbitmq.util.ConnectionUtils;

/**
 * @ClassName: Send
 * @Description: Work队列模型：生产者发送消息
 * @author D
 * @date 2017年3月9日 下午6:23:59
 */
public class Send {
	
	//定义队列名称
	private static final String QUEUE_NAME = "work_queue_fair";
	
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		//1、获取连接
		Connection connection = ConnectionUtils.getConnection();
		//2、声明信道
		Channel channel = connection.createChannel();
		//3、声明(创建)队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		//4、限制每个消费者同时最多能处理1条消息
		int prefetchCount = 1;
		channel.basicQos(prefetchCount);
		
		//5、定义消息内容(发布多条消息)
		for (int i = 0; i < 10; i++) {
			String message = "I am work_queue " + i;
			//6、发布消息
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes("utf-8"));
			System.out.println(" [x] Sent '" + message + "'");
			//模拟发送消息延时，便于演示多个消费者竞争接受消息
			Thread.sleep(i * 10);
		}
		//7、关闭信道
		channel.close();
		//8、关闭连接
		connection.close();
	}
}