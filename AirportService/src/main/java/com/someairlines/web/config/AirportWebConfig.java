package com.someairlines.web.config;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.someairlines.config.RootConfig;

@Configuration
@ComponentScan("com.someairlines.web")
@EnableWebMvc
@EnableWebSecurity
public class AirportWebConfig extends WebMvcConfigurerAdapter {
	
//	@Bean
//    public ViewResolver viewResolver() {
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setPrefix("/WEB-INF/view/");
//        viewResolver.setSuffix(".jsp");
//        return viewResolver;
//    }
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private RootConfig rootConfig;
	@Bean
	@Description("Thymeleaf View Resolver")
	public ThymeleafViewResolver viewResolver() {
	    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
	    viewResolver.setTemplateEngine(templateEngine());
	    viewResolver.setOrder(1);
	    return viewResolver;
	}
	
	@Bean
	@Description("Thymeleaf Template Resolver")
	public ServletContextTemplateResolver templateResolver() {
	    ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
	    templateResolver.setPrefix("/WEB-INF/views/");
	    templateResolver.setSuffix(".html");
	    templateResolver.setTemplateMode("HTML5");
	    return templateResolver;
	}
	 
	@Bean
	@Description("Thymeleaf Template Engine")
	public SpringTemplateEngine templateEngine() {
	    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	    templateEngine.setTemplateResolver(templateResolver());
	    templateEngine.setTemplateEngineMessageSource(rootConfig.messageSource());
	    return templateEngine;
	}
	
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/login.jsp");
    }
	
	@Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }
 
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
