package com.ripple.provision.vm.controller;

import com.ripple.provision.vm.model.*;
import com.ripple.provision.vm.service.AuthService;
import com.ripple.provision.vm.service.VMService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(value = "test")
@WebMvcTest(VMController.class)
@ContextConfiguration(classes = VMController.class)
public class VMControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        VMService vmService;

        @Before
        public void setup() throws Exception {
            MockitoAnnotations.initMocks(this);
            Status status = new Status("Success");
            when(vmService.createVM(any(VirtualMachine.class),any(String.class))).thenReturn(status);
        }

        @Test
        public void return_status_ok_when_virtual_machine_is_created() throws Exception {

            this.mockMvc.perform(post("/api/vm/new").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                            .content("{\"vmName\": \"Test\",\"domainName\": \"abc.com\",\"os\" : \"ubuntu\",\"ramSize\": 16,\"hardDiskSize\": 512,\"noOfCPUCores\": 8,\"userId\" : \"pavan44srar@gmail.com\"}"))
                    .andExpect(status().isOk());

        }

        @Test
        public void return_status_ok_for_all_vm_retrieval() throws Exception {
            User user = new User();
            AuthService authService = Mockito.mock(AuthService.class);
            when(authService.registerUser(user)).thenReturn(new Status("Success"));
            this.mockMvc.perform(get("/api/vm/all"))
                    .andExpect(status().isOk());
        }

        @Test
        public void throw_error_when_register_user_body_is_not_acceptable() throws Exception {

            this.mockMvc.perform(post("/api/vm/new").accept(MediaType.APPLICATION_ATOM_XML).contentType(MediaType.APPLICATION_ATOM_XML)
                            .content("{\"vmName\": \"Test\",\"domainName\": \"abc.com\",\"os\" : \"ubuntu\",\"ramSize\": 16,\"hardDiskSize\": 512,\"noOfCPUCores\": 8,\"userId\" : \"pavan44srar@gmail.com\"}"))
                    .andExpect(status().isUnsupportedMediaType());

        }

        @Test
        public void throw_url_not_found_exception() throws Exception {
            User user = new User();
            AuthService authService = Mockito.mock(AuthService.class);
            when(authService.registerUser(user)).thenReturn(new Status("Success"));
            this.mockMvc.perform(post("/api/vm/ne").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                            .content("{\"vmName\": \"Test\",\"domainName\": \"abc.com\",\"os\" : \"ubuntu\",\"ramSize\": 16,\"hardDiskSize\": 512,\"noOfCPUCores\": 8,\"userId\" : \"pavan44srar@gmail.com\"}"))
                    .andExpect(status().isNotFound());
        }



    }


