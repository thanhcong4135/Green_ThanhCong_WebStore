package com.green.webstoreadmin.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.green.webstoreadmin.handler.OnAuthenticationFailureHandler;
import com.green.webstoreadmin.handler.OnAuthenticationSuccessHandler;
import com.green.webstoreadmin.helper.PasswordManager;
import com.green.webstoreadmin.users.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter{
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		String password = PasswordManager.getBCrypPassword("12345678");
//		
//		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//		.withUser("thanhcong4135@gmail.com").password(password).roles("ADMIN");
//		
//	}
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private OnAuthenticationSuccessHandler successHandler;
	
	@Autowired
	private OnAuthenticationFailureHandler failureHandler;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService	userDetailsService() {
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setPasswordEncoder(passwordEncoder());
		authProvider.setUserDetailsService(userDetailsService());
		
		return authProvider;
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepo = new JdbcTokenRepositoryImpl();
		tokenRepo.setDataSource(dataSource);
		
		return tokenRepo;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());	
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.rememberMe().key("thanhcong").tokenValiditySeconds(1296000);
		http.authorizeRequests()
		.antMatchers("/assets/**","/css/**", "/js/**").permitAll()
		.antMatchers("/users", "/roles").hasAnyAuthority("ADMIN")
		.anyRequest().authenticated()
		.and().formLogin().loginPage("/login").permitAll()
		.usernameParameter("username")
		.passwordParameter("password")
		.loginProcessingUrl("/dologin")
		.successHandler(successHandler)
		.failureHandler(failureHandler)
		.and().logout().permitAll();
	}
}
