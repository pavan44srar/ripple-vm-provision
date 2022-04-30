package com.ripple.provision.vm.service;

import com.ripple.provision.vm.controller.VMController;
import com.ripple.provision.vm.exception.DuplicateResourceFoundException;
import com.ripple.provision.vm.model.Status;
import com.ripple.provision.vm.model.VirtualMachine;
import com.ripple.provision.vm.repository.VMRepository;
import com.ripple.provision.vm.service.Impl.VMServiceImpl;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(value = "test")
@ContextConfiguration(classes = VMServiceImpl.class)
public class VMServiceTest {

    @Autowired
    VMServiceImpl vmService;

    @MockBean
    VMRepository vmRepository;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void return_success_when_vm_is_created() throws Exception {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        VirtualMachine virtualMachine = new VirtualMachine(1, "test", "abc.com", "test", 12, 13, 1, "test", timestamp, timestamp);
        when(vmRepository.findRecordByDomainName(virtualMachine.getDomainName())).thenReturn(null);
        when(vmRepository.save(any(VirtualMachine.class))).thenReturn(new VirtualMachine());
        Status status = vmService.createVM(new VirtualMachine());
        assertEquals(status.getResponse(), "Success");

    }

    @Test
    public void return_duplicate_dn_when_vm_already_exists() throws Exception {
        Optional<VirtualMachine> vm = Optional.of(new VirtualMachine());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        VirtualMachine virtualMachine = new VirtualMachine(1, "test", "abc.com", "test", 12, 13, 1, "test", timestamp, timestamp);
        when(vmRepository.findRecordByDomainName(virtualMachine.getDomainName())).thenReturn(vm);
        assertThrows(DuplicateResourceFoundException.class, () -> {
            vmService.createVM(virtualMachine);
        });

    }

    @Test
    public void return_virtual_machines_when_vm_exists() throws Exception {
        List<VirtualMachine> vm = new ArrayList<>();
        vm.add(new VirtualMachine());
        vm.add(new VirtualMachine());
        Iterable<VirtualMachine> itrVMs = vm;
        when(vmRepository.findAll()).thenReturn(itrVMs);
        List<VirtualMachine> lst = vmService.getAllVirtualMachines();
        assertEquals(lst.size(), 2);

    }

    @Test
    public void return_empty_list_when_no_vm() throws Exception {
        List<VirtualMachine> vm = new ArrayList<>();
        Iterable<VirtualMachine> itrVMs = vm;
        when(vmRepository.findAll()).thenReturn(itrVMs);
        List<VirtualMachine> lst = vmService.getAllVirtualMachines();
        assertEquals(lst.size(), 0);


    }
}
