package com.csv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.csv.services.AccountService;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration implements WebMvcConfigurer{
	
	@Autowired
	AccountService accountService;
	
	
	protected void configure(HttpSecurity httpSecurity) throws Exception{

		httpSecurity.cors().and().csrf().disable();
		
		httpSecurity.authorizeRequests()
		.antMatchers("/superadmin**").access("hasHole('ROLE_SUPER_ADMIN')")
		.antMatchers("/admin**").access("hasHole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')")
		.antMatchers("/employee**").access("hasHole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
		.and()
		.formLogin().loginPage("/dashboard/login")
		.loginProcessingUrl("/dashboard/process-login")
		.defaultSuccessUrl("/dashboard/welcome")
		.failureUrl("/dashboard/login?error")
		.usernameParameter("username").passwordParameter("password")
		.and()
		.logout().logoutUrl("/dashboard/logout")
		.logoutSuccessUrl("/dashboard/login?logout")
		.and()
		.exceptionHandling().accessDeniedPage("/dashboard/accessDenied");
		
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception{
		builder.userDetailsService(accountService);
	}
	
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
}
