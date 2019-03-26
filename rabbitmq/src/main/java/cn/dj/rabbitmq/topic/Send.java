package cn.dj.rabbitmq.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import cn.dj.rabbitmq.util.ConnectionUtils;

/**
 * @ClassName: Send
 * @Description: Topics-主题模式：生产者发送消息
 * @author D
 * @date 2017年3月10日 上午12:15:39
 */
public class Send {
	
	//定义交换机名称
	private static final String EXCHANGE_NAME = "topic_exchange";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		//1、获取连接
		Connection connection = ConnectionUtils.getConnection();
		//2、声明信道
		Channel channel = connection.createChannel();
		//3、声明交换机
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		//4、定义消息内容
		String messageByAdd = "新增用户";
		//5、发布消息
		String routingKeyByAdd = "user.add";
		channel.basicPublish(EXCHANGE_NAME, routingKeyByAdd, null, messageByAdd.getBytes("utf-8"));
		System.out.println(" [x] Sent '" + messageByAdd + "'");
		
		//4、定义消息内容
		String messageByDel = "删除用户";
		//5、发布消息
		String routingKeyByDel = "user.delete";
		channel.basicPublish(EXCHANGE_NAME, routingKeyByDel, null, messageByDel.getBytes("utf-8"));
		System.out.println(" [x] Sent '" + messageByDel + "'");
		//6、关闭信道
		channel.close();
		//7、关闭连接
		connection.close();
	}
}