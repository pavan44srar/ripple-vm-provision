package com.ripple.provision.vm.repository;

import java.util.List;
import java.util.Optional;

import com.ripple.provision.vm.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
  Optional<User> findByUserId(String userId);
}