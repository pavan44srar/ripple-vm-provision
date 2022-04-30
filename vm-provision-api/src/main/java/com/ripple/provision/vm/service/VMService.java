package com.ripple.provision.vm.service;

import com.ripple.provision.vm.exception.DuplicateResourceFoundException;
import com.ripple.provision.vm.model.Status;
import com.ripple.provision.vm.model.VirtualMachine;

import java.util.List;

public interface VMService {
    public Status createVM(VirtualMachine vmProvision, String userId) throws DuplicateResourceFoundException;

    public List<VirtualMachine> getAllVirtualMachines();
}
