package br.com.alan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alan.model.Role;

public interface RoleRespository extends JpaRepository<Role, Long> {
}
