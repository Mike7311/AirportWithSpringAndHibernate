package com.someairlines.web;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@Order(2)
public final class AirportSecurityInitializer extends AbstractSecurityWebApplicationInitializer {
}
