package cn.dj.rabbitmq.confirm;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import cn.dj.rabbitmq.util.ConnectionUtils;

/**
 * @ClassName: SendAsynConfirm
 * @Description: 异步Confirm模式：生产者发送消息
 * @author D
 * @date 2017年3月10日 上午12:15:39
 */
public class SendAsynConfirm {
	
	private static final String QUEUE_NAME = "confirm_queue";
	
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		//开启发送方确认模式
		channel.confirmSelect();
		
		//异步监听确认和未确认的消息
		channel.addConfirmListener(new ConfirmListener() {
			@Override
			public void handleNack(long deliveryTag, boolean multiple) throws IOException {
				System.out.println("未确认消息，标识：" + deliveryTag);
			}
			
			@Override
			public void handleAck(long deliveryTag, boolean multiple) throws IOException {
				try {
					//这里设置秒延迟，便于观察
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(String.format("已确认消息，标识：%d，多个消息：%b", deliveryTag, multiple));
			}
		});

		String message = "confirm_test";
		for (int i = 0; i < 5; i++) {
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes("utf-8"));
			System.out.println(" [x] Sent '" + message + "' - " + i);
		}
		
		System.out.println("程序执行结束");
		
		channel.close();
		connection.close();
	}
}