package com.skilldistillery.paseo.services;

import java.util.List;

import com.skilldistillery.paseo.entities.Message;

public interface MessageService {
	
	List<Message> findAllMessagesBySender(int senderId);
	List<Message> findAllMessagesReceived(int recieverId);
	public Message create(Message message, int senderId);
	public Message delete(Message message, int userId);
	

}
