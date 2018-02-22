package com.someairlines.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@EnableWebSecurity
public class TestSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/flights*").hasAnyRole("ADMIN, USER")
                .antMatchers("/crews*").hasRole("USER")
//                .antMatchers("/messageDelete*").hasRole("ADMIN")
            .and()
                .formLogin()
                    .loginPage("/login.jsp")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .loginProcessingUrl("/j_spring_security_check")
                    .defaultSuccessUrl("/flights")
                    .failureUrl("/login.jsp?error=true")
            .and()
                .logout()
                    .logoutSuccessUrl("/")
            .and()
                .rememberMe()
            .and()
            	.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN")
    	.and().withUser("user1").password("user1").roles("USER");
    }
    
    @Bean @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
