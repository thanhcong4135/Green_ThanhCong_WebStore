package com.green.webstoreadmin.roles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.green.webstoremodels.dto.RoleDto;

@RestController
@RequestMapping("/api/admin/roles")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping
	public List<RoleDto> listRoles() {
		return roleService.getAllRoles();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RoleDto> getRole(@PathVariable(name = "id") Integer id) {
		RoleDto role = roleService.getRoleById(id);
		return role != null ? ResponseEntity.ok(role) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<RoleDto> createRole(@ModelAttribute("role") RoleDto role) {
		roleService.save(role);
		return ResponseEntity.ok(role);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<RoleDto> updateRole(@PathVariable(name = "id") Integer id, @ModelAttribute("role") RoleDto role) {
		role.setId(id);
		roleService.save(role);
		return ResponseEntity.ok(role);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRole(@PathVariable(name = "id") Integer id) {
		roleService.deleteRoleById(id);
		return ResponseEntity.noContent().build();
	}
	
}
