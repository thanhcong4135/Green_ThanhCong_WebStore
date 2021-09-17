package com.green.webstoreadmin.roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.green.webstoremodels.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	@Query("SELECT r FROM Role r WHERE r.name = :name")
	public Role getRoleByName(@Param("name") String name);

}
