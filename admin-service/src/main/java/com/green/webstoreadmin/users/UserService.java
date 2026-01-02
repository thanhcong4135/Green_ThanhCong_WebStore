package com.green.webstoreadmin.users;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.green.webstoremodels.dto.UserData;
import com.green.webstoremodels.dto.UserDto;

@Service
public class UserService {
	
	private final RestTemplate restTemplate = new RestTemplate();

	@Value("${auth.service.url:http://localhost:8090/api/users}")
	private String authServiceUrl;
	
	public List<UserData> getAllUsers(){
		UserData[] users = restTemplate.getForObject(authServiceUrl, UserData[].class);
		return users != null ? Arrays.asList(users) : List.of();
	}
	
	public UserDto getUserByUsername(String username) {
		String url = UriComponentsBuilder.fromHttpUrl(authServiceUrl).pathSegment("by-username", username).toUriString();
		return restTemplate.getForObject(url, UserDto.class);
	}
	
	public void deleteUserById(int id) {
		String url = UriComponentsBuilder.fromHttpUrl(authServiceUrl).pathSegment(String.valueOf(id)).toUriString();
		restTemplate.delete(url);
	}
	
	public UserDto saveUser(UserDto user) {
		if (user.getId() == null) {
			return restTemplate.postForObject(authServiceUrl, user, UserDto.class);
		}
		String url = UriComponentsBuilder.fromHttpUrl(authServiceUrl).pathSegment(String.valueOf(user.getId())).toUriString();
		restTemplate.put(url, user);
		return user;
	}
	
}
