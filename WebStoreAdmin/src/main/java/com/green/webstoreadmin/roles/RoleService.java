package com.green.webstoreadmin.roles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.webstoremodels.entities.Role;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	public List<Role> getAllRoles(){
		return roleRepository.findAll();
	}
	
	public void deleteRoleById(Integer id) {
		roleRepository.deleteById(id);
	}
	
	public Role getRoleByName(String name) {
		return roleRepository.getRoleByName(name);
	}
	
	public Role getRoleById(Integer id) {
		return roleRepository.getById(id);
	}
	
	public void save(Role role) {
		roleRepository.save(role);
	}
	
	public Boolean checkRoleNameExist(String name, Integer id) {
		Role role = getRoleByName(name);
		
		if(role != null && role.getId() != id) {
			return true;
		}
		return false;
	}
	
	public Boolean checkNewRoleNameExist(String name) {
		Role role = getRoleByName(name);
		
		if(role != null) {
			return true;
		}
		return false;
	}

}
