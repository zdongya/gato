package com.yunjing.util;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class MqttMsgSendUtil {
	private static Log log =  LogFactory.getLog("MqttMsgSendUtil");
	
	// tcp 地址     
	public static final String BROKER_URL = "tcp://115.159.44.248:61616";
	
	/**
	 * 
	 * @param topic:订阅主题
	 * @param message:消息内容
	 * @return
	 */
	public static boolean sendMessage(String topic, String message){
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
			Topic tp = new ActiveMQTopic(topic);
			
			// 创建消息发送者
			TopicPublisher publisher = session.createPublisher(tp);
			
			// 设置持久化模式
			publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			TextMessage msg = session.createTextMessage(message);
			publisher.send(msg);
			session.commit();
			return true;
		} catch (Exception e){
			log.error("推送消息失败");
			log.error(e.getMessage(), e);
			return false;
		} finally {
			// 关闭释放资源
			try {
				if (session != null){
					session.close();
				}
				if (null != connection){
					connection.close();
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	public static void main(String[] args) throws Exception {
		sendMessage("device1", "我顶你个肺");
	}

}
