package com.green.webstoreadmin.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.green.webstoreadmin.handler.OnAuthenticationFailureHandler;
import com.green.webstoreadmin.handler.OnAuthenticationSuccessHandler;
import com.green.webstoreadmin.users.RemoteUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigure {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private OnAuthenticationSuccessHandler successHandler;
	
	@Autowired
	private OnAuthenticationFailureHandler failureHandler;

	@Autowired
	private RemoteUserDetailsService remoteUserDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService	userDetailsService() {
		return remoteUserDetailsService;
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setPasswordEncoder(passwordEncoder());
		authProvider.setUserDetailsService(remoteUserDetailsService);
		
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepo = new JdbcTokenRepositoryImpl();
		tokenRepo.setDataSource(dataSource);
		
		return tokenRepo;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authenticationProvider(authenticationProvider());

		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/assets/**", "/css/**", "/js/**").permitAll()
				.requestMatchers("/users", "/roles").hasAuthority("ADMIN")
				.anyRequest().authenticated())
			.formLogin(form -> form
				.loginPage("/login").permitAll()
				.usernameParameter("username")
				.passwordParameter("password")
				.loginProcessingUrl("/dologin")
				.successHandler(successHandler)
				.failureHandler(failureHandler))
			.logout(logout -> logout.permitAll())
			.rememberMe(remember -> remember
				.key("thanhcong")
				.tokenValiditySeconds(1296000)
				.userDetailsService(userDetailsService())
				.tokenRepository(persistentTokenRepository()));
		
		return http.build();
	}
}
