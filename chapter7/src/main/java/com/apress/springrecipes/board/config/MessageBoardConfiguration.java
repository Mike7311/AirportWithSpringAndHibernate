package com.apress.springrecipes.board.config;

import javax.sql.DataSource;

import org.apache.derby.jdbc.ClientDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import com.apress.springrecipes.board.service.MessageBoardService;
import com.apress.springrecipes.board.service.MessageBoardServiceImpl;

/**
 * Created by marten on 06-06-14.
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class MessageBoardConfiguration {

    @Bean
    public MessageBoardService messageBoardService() {
        return new MessageBoardServiceImpl();
    }
    
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(ClientDriver.class.getName());
        dataSource.setUrl("jdbc:derby://localhost:1527/board;create=true");
        dataSource.setUsername("app");
        dataSource.setPassword("app");
        return dataSource;
    }
}
