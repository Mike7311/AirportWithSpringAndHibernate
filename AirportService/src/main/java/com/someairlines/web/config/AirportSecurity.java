package com.someairlines.web.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class AirportSecurity extends WebSecurityConfigurerAdapter{

	@Autowired
    private DataSource dataSource;
	
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
    	auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(new Md5PasswordEncoder())
    	.authoritiesByUsernameQuery("select users.username, authorities.authority from users,authorities where users.username=? AND users.id=authorities.user_id");
    }
    
    @Bean @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
