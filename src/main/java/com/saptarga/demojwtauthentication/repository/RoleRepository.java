package com.saptarga.demojwtauthentication.repository;

import com.saptarga.demojwtauthentication.entity.Role;
import com.saptarga.demojwtauthentication.statval.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

}
