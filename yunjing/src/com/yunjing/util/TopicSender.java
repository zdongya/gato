package com.yunjing.util;

import javax.jms.DeliveryMode;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

public class TopicSender {
	
	public static final int SEND_NUM = 5;
	
	// tcp 地址     
	public static final String BROKER_URL = "tcp://139.196.175.166:61616";
	
	public static final String DESTINATION = "device1";
	
	public static void sendMessage(TopicSession session, TopicPublisher publisher) throws Exception{
			MapMessage map = session.createMapMessage();
			map.setString("message", "abcccc");
			publisher.send(map);
		
	}
	
	public static void run() throws Exception {
		TopicConnection connection = null;
		TopicSession session = null;
		try{
			// 创建链接工厂
			TopicConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, BROKER_URL);
			// 通过工厂创建一个连接
			connection = factory.createTopicConnection();
			// 启动连接
			connection.start();
			// 创建一个session会话
			session = connection.createTopicSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			// 创建一个消息队列
			
			Topic topic = new ActiveMQTopic(DESTINATION);
			
			// 创建消息发送者
			TopicPublisher publisher = session.createPublisher(topic);
			// 设置持久化模式
			publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			sendMessage(session, publisher);
			session.commit();
		} catch (Exception e){
			throw e;
		} finally {
			// 关闭释放资源
			if (session != null){
				session.close();
			}
			if (null != connection){
				connection.close();
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		TopicSender.run();
	}
}
