package com.face.callout.repository;

import com.face.callout.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {

	Role findByRole(String role);
}