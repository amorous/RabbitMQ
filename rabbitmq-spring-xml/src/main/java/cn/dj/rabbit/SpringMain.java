package cn.dj.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName: SpringMain
 * @Description: Spring-RabbitMQ集成测试类
 * @author D
 * @date 2017年3月10日 下午3:05:35
 */
public class SpringMain {
	
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws InterruptedException {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-rabbitmq.xml");
		RabbitTemplate template = context.getBean(RabbitTemplate.class);
		template.convertAndSend("hello spring-rabbit");
		context.destroy();
	}
}
