package com.fenghuo.util;

import org.springframework.amqp.core.AmqpTemplate;

import com.fenghuo.model.Msg;
public class MsgPush {

    private static AmqpTemplate amqpTemplate;
    private static AmqpTemplate amqpTemplate_balance;
    
	public static void setAmqpTemplate(AmqpTemplate amqpTemplate) {
		MsgPush.amqpTemplate = amqpTemplate;
	}
	
	public static void setAmqpTemplate_balance(AmqpTemplate amqpTemplate_balance) {
		MsgPush.amqpTemplate_balance = amqpTemplate_balance;
	}



	/**
	 * 用户下单后将配送零食消息推送给配送人员
	 * @param staff 接送消息的配送人员登录账号
	 * @param 订单自增编号
	 * @param 订单生成编号
	 * @param 配送地址
	 */
	public void sendMsg(String staff,long orderId,String address){
		Msg.sendMsg.Builder builder = Msg.sendMsg.newBuilder();
		builder.setStaffTel(staff);
		builder.setOrderId(orderId);
		builder.setAddress(address);
		amqpTemplate.convertAndSend("send.one", builder.build().toByteArray());
	}
	
	/**
	 * 订单结算推送
	 * @param staff 接送消息的配送人员登录账号
	 * @param 订单自增编号
	 * @param 配送地址
	 */
	public void balanceMsg(String staff,long oldOrderId,long newOrderId,String address){
		Msg.balanceMsg.Builder builder = Msg.balanceMsg.newBuilder();
		builder.setStaffTel(staff);
		builder.setOldOrderId(oldOrderId);
		builder.setNewOrderId(newOrderId);
		builder.setAddress(address);
		amqpTemplate_balance.convertAndSend("balance.one", builder.build().toByteArray());
	}
	
}
