package com.green.webstoreclient.securities;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.green.webstoreclient.handlers.OnAuthenticationFailureHandler;
import com.green.webstoreclient.handlers.OnAuthenticationSuccessHandler;


@EnableWebSecurity
@Configuration
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private OnAuthenticationFailureHandler failureHandler;
	@Autowired
	private OnAuthenticationSuccessHandler successHandler;
	
	@Autowired
	private DataSource dataSource;
		
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/assets/**");
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomerDetailsService();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepo = new JdbcTokenRepositoryImpl();
		tokenRepo.setDataSource(dataSource);
		
		return tokenRepo;
	}
	
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.rememberMe().key("uniqueAndSecret").tokenValiditySeconds(1296000);
		http.authorizeRequests()
		.antMatchers("/", "/home", "/register", "/allproducts/**","/shopping_cart","/loadproduct-bycategoryid/**","../assets/**").permitAll()
		.anyRequest().authenticated()
		.and().formLogin().loginPage("/login").permitAll()
		.usernameParameter("email")
		.passwordParameter("password")
		.loginProcessingUrl("/dologin")
		.failureHandler(failureHandler)
		.successHandler(successHandler)
		//.and().rememberMe().key("asdfghjk0987654").tokenValiditySeconds(60*60)
		//.and().rememberMe().tokenRepository(persistentTokenRepository()).tokenValiditySeconds(60*50)
//		.and().csrf().disable()
		.and().logout().permitAll();
	}
}
