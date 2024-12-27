package br.com.filla.api.domain.authorization.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Role findByName(String name);

  boolean existsByName(String name);

}
