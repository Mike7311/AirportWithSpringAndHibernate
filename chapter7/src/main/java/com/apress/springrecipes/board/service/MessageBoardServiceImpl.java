package com.apress.springrecipes.board.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.annotation.Secured;

import com.apress.springrecipes.board.domain.Message;

public class MessageBoardServiceImpl implements MessageBoardService {

	private Map<Long, Message> messages = new LinkedHashMap<>();
	
	@Secured({"ROLE_USER", "ROLE_GUEST"})
	@Override
	public List<Message> listMessages() {
		return new ArrayList<Message>(messages.values());
	}

	@Secured("ROLE_USER")
	@Override
	public void postMessage(Message message) {
		message.setId(System.currentTimeMillis());
		messages.put(message.getId(), message);
	}

	@Secured("ROLE_ADMIN")
	@Override
	public void deleteMessage(Message message) {
		messages.remove(message.getId());
	}

	@Secured({"ROLE_USER", "ROLE_GUEST"})
	@Override
	public Message findMessageById(Long messageId) {
		return messages.get(messageId);
	}

}
