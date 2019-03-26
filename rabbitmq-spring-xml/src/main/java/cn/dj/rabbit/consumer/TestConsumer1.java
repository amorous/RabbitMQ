package cn.dj.rabbit.consumer;

/**
 * @ClassName: TestConsumer1
 * @Description: 消费者-定义method方式
 * @author D
 * @date 2017年3月10日 下午2:46:06
 */
public class TestConsumer1 {
	
	//监听方法
	public void listen(String msg) {
		System.out.println("收到消息===>" + msg);
	}
}
