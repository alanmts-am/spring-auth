package br.com.alan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.alan.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRespository extends JpaRepository<Role, String> {
    Optional<Role> findByRole(String role);

    @Query(value = "SELECT r.* FROM tb_user_roles ur JOIN tb_role r ON ur.roles_role = r.role WHERE ur.user_id = :id", nativeQuery = true)
    public List<Role> findByUserRoles(@Param("id") Long id);
}
