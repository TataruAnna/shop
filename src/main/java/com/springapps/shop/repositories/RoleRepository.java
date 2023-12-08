package com.springapps.shop.repositories;

import com.springapps.shop.entities.Role;
import com.springapps.shop.entities.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleType(RoleType roleType);
}
