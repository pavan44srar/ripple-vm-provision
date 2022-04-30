package com.ripple.provision.vm.repository;

import com.ripple.provision.vm.model.VirtualMachine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VMRepository extends CrudRepository<VirtualMachine,Integer> {

    Iterable<VirtualMachine> findAll();

    @Query(value = "select * from vm_details where vm_details.domain_name=:domain_name", nativeQuery=true)
    Optional<VirtualMachine> findRecordByDomainName(@Param("domain_name") String domain_name);
}
