package com.ripple.provision.vm.controller;

import com.ripple.provision.vm.exception.DuplicateResourceFoundException;
import com.ripple.provision.vm.model.*;
import com.ripple.provision.vm.service.VMService;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/vm")
public class VMController {

    @Autowired
    VMService vmService;

    @PostMapping("/new")
    public Status createVirtualMachine(@Valid @RequestBody VirtualMachine virtualMachine, @RequestHeader("userId") String userId) throws DuplicateResourceFoundException {
        return vmService.createVM(virtualMachine, userId);
    }

    @GetMapping("/all")
    public List<VirtualMachine> getALLVirtualMachines() throws Exception {
        return vmService.getAllVirtualMachines();
    }
}


