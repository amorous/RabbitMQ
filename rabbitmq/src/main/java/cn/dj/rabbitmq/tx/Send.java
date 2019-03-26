package cn.dj.rabbitmq.tx;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import cn.dj.rabbitmq.util.ConnectionUtils;

/**
 * @ClassName: Send
 * @Description: 事务机制：生产者发送消息
 * @author D
 * @date 2017年3月10日 上午12:15:39
 */
public class Send {
	
	private static final String QUEUE_NAME = "transaction_queue";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		String message = "transaction_test";
		
		try {
			//1、声明事务
			channel.txSelect();
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes("utf-8"));
			System.out.println(" [x] Sent '" + message + "'");
			//除以0，模拟异常，使用rabbitmq默认交换机
			int i = 1/0;
			//2、提交事务
			channel.txCommit();
		} catch (Exception e) {
			//3、回滚事务
			channel.txRollback();
			System.out.println("回滚事务：" + e.getMessage());
		} finally {
			channel.close();
			connection.close();
		}
	}
}