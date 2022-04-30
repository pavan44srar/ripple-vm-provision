package com.ripple.provision.vm.service.Impl;

import com.ripple.provision.vm.exception.DuplicateResourceFoundException;
import com.ripple.provision.vm.model.Status;
import com.ripple.provision.vm.model.VirtualMachine;
import com.ripple.provision.vm.repository.VMRepository;
import com.ripple.provision.vm.service.VMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class VMServiceImpl implements VMService {
    @Autowired
    VMRepository vmRepository;

    @Override
    public Status createVM(VirtualMachine virtualMachine, String userId) throws DuplicateResourceFoundException {
        if (vmRepository.findRecordByDomainName(virtualMachine.getDomainName()).isPresent()) {
            throw new DuplicateResourceFoundException("Virtual Machine with Domain Name already exists.");
        }
        virtualMachine.setUserId(userId);
        vmRepository.save(virtualMachine);
        return new Status("Success");
    }

    @Override
    public List<VirtualMachine> getAllVirtualMachines() {
        Iterable<VirtualMachine> itrVMs = vmRepository.findAll();
        Iterator<VirtualMachine> iterator = itrVMs.iterator();
        List<VirtualMachine> virtualMachines = new ArrayList<>();
        while(iterator.hasNext()) {
            virtualMachines.add(iterator.next());
        }
        return virtualMachines;
    }
}
