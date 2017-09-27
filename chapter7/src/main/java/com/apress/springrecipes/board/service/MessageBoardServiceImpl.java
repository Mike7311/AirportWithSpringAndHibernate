package com.apress.springrecipes.board.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.apress.springrecipes.board.domain.Message;

public class MessageBoardServiceImpl implements MessageBoardService {

	private Map<Long, Message> messages = new LinkedHashMap<>();
	
	@Override
	public List<Message> listMessages() {
		return new ArrayList<Message>(messages.values());
	}

	@Override
	public void postMessage(Message message) {
		message.setId(System.currentTimeMillis());
		messages.put(message.getId(), message);
	}

	@Override
	public void deleteMessage(Message message) {
		messages.remove(message.getId());
	}

	@Override
	public Message findMessageById(Long messageId) {
		return messages.get(messageId);
	}

}
