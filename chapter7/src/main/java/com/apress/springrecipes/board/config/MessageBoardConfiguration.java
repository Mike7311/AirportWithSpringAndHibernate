package com.apress.springrecipes.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.apress.springrecipes.board.service.MessageBoardService;
import com.apress.springrecipes.board.service.MessageBoardServiceImpl;

@Configuration
public class MessageBoardConfiguration {

	@Bean
	public MessageBoardService messageBoardService() {
		return new MessageBoardServiceImpl();
	}
}
