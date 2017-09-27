package com.apress.springrecipes.board.web;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@Order(2)
public class MessageBoardSecurityInitializer extends AbstractSecurityWebApplicationInitializer{}