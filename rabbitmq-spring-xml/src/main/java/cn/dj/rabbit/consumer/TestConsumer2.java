package cn.dj.rabbit.consumer;

import java.io.UnsupportedEncodingException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @ClassName: TestConsumer2
 * @Description: 消费者-实现接口的方式
 * @author D
 * @date 2017年3月10日 下午2:46:06
 */
public class TestConsumer2 implements MessageListener {
	
	@Override
	public void onMessage(Message msg) {
		try {
			System.out.println("收到消息===>" + new String(msg.getBody(), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
