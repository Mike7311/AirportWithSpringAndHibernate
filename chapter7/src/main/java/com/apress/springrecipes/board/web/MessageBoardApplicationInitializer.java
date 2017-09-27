package com.apress.springrecipes.board.web;

import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.apress.springrecipes.board.config.MessageBoardConfiguration;
import com.apress.springrecipes.board.web.config.MessageBoardSecurityConfiguration;
import com.apress.springrecipes.board.web.config.MessageBoardWebConfiguration;

@Order(1)
public class MessageBoardApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {MessageBoardConfiguration.class, MessageBoardSecurityConfiguration.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {MessageBoardWebConfiguration.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

}
