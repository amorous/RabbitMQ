package cn.dj.rabbitmq.confirm;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import cn.dj.rabbitmq.util.ConnectionUtils;

/**
 * @ClassName: SendBatchConfirm
 * @Description: 批量Confirm模式：生产者发送消息
 * @author D
 * @date 2017年3月10日 上午12:15:39
 */
public class SendBatchConfirm {
	
	
	private static final String QUEUE_NAME = "confirm_queue";
	
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		String message = "confirm_test";
		
		//开启发送方确认模式
		channel.confirmSelect();
		
		for (int i = 0; i < 10; i++) {
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes("utf-8"));
			System.out.println(" [x] Sent '" + message + "'");
		}
		
		//直到所有信息都发布，只要有一个未确认就会IOException
		channel.waitForConfirmsOrDie();
		System.out.println("消息发送成功");
		channel.close();
		connection.close();
	}
}