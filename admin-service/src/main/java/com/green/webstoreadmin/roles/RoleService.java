package com.green.webstoreadmin.roles;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.green.webstoremodels.dto.RoleDto;

@Service
public class RoleService {
	
	private final RestTemplate restTemplate = new RestTemplate();

	@Value("${auth.service.roles-url:http://localhost:8090/api/roles}")
	private String authRoleUrl;
	
	public List<RoleDto> getAllRoles(){
		RoleDto[] roles = restTemplate.getForObject(authRoleUrl, RoleDto[].class);
		return roles != null ? Arrays.asList(roles) : List.of();
	}
	
	public void deleteRoleById(Integer id) {
		String url = UriComponentsBuilder.fromHttpUrl(authRoleUrl).pathSegment(String.valueOf(id)).toUriString();
		restTemplate.delete(url);
	}
	
	public RoleDto getRoleByName(String name) {
		String url = UriComponentsBuilder.fromHttpUrl(authRoleUrl).pathSegment("by-name", name).toUriString();
		return restTemplate.getForObject(url, RoleDto.class);
	}
	
	public RoleDto getRoleById(Integer id) {
		String url = UriComponentsBuilder.fromHttpUrl(authRoleUrl).pathSegment(String.valueOf(id)).toUriString();
		return restTemplate.getForObject(url, RoleDto.class);
	}
	
	public void save(RoleDto role) {
		if (role.getId() == null) {
			restTemplate.postForObject(authRoleUrl, role, RoleDto.class);
		} else {
			String url = UriComponentsBuilder.fromHttpUrl(authRoleUrl).pathSegment(String.valueOf(role.getId())).toUriString();
			restTemplate.put(url, role);
		}
	}
	
	public Boolean checkRoleNameExist(String name, Integer id) {
		RoleDto role = getRoleByName(name);
		return role != null && !role.getId().equals(id);
	}
	
	public Boolean checkNewRoleNameExist(String name) {
		return getRoleByName(name) != null;
	}

}
