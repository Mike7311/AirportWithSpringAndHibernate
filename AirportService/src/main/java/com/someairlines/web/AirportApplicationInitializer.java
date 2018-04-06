package com.someairlines.web;

import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.someairlines.config.DataConfig;
import com.someairlines.config.RootConfig;
import com.someairlines.web.config.AirportSecurity;
import com.someairlines.web.config.AirportWebConfig;

@Order(1)
public final class AirportApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {RootConfig.class, DataConfig.class, AirportSecurity.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
    	return new Class<?>[] {AirportWebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}
