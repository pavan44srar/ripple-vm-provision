package com.ripple.provision.vm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import com.ripple.provision.vm.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
