package com.green.webstoreclient.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;

import com.green.webstoreclient.handlers.OnAuthenticationFailureHandler;
import com.green.webstoreclient.handlers.OnAuthenticationSuccessHandler;


@EnableWebSecurity
@Configuration
public class WebSecurityConfigure {
	
	@Autowired
	private OnAuthenticationFailureHandler failureHandler;
	@Autowired
	private OnAuthenticationSuccessHandler successHandler;
		
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
		return new InMemoryTokenRepositoryImpl();
	}
	
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authenticationProvider(authenticationProvider());
		
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/", "/home", "/register", "/allproducts/**", "/shopping_cart", "/loadproduct-bycategoryid/**", "/assets/**").permitAll()
				.anyRequest().authenticated())
			.formLogin(form -> form
				.loginPage("/login").permitAll()
				.usernameParameter("email")
				.passwordParameter("password")
				.loginProcessingUrl("/dologin")
				.failureHandler(failureHandler)
				.successHandler(successHandler))
			.rememberMe(remember -> remember
				.key("uniqueAndSecret")
				.tokenValiditySeconds(1296000)
				.userDetailsService(userDetailsService())
				.tokenRepository(persistentTokenRepository()))
			.logout(logout -> logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.permitAll())
			.csrf(AbstractHttpConfigurer::disable);
		
		return http.build();
	}
}
