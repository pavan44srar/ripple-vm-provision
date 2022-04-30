package com.ripple.provision.vm.controller;

import com.ripple.provision.vm.Provision;
import com.ripple.provision.vm.exception.ControllerExceptionHandler;
import com.ripple.provision.vm.filter.JwtRequestFilter;
import com.ripple.provision.vm.model.AuthenticationRequest;
import com.ripple.provision.vm.model.AuthenticationResponse;
import com.ripple.provision.vm.model.Status;
import com.ripple.provision.vm.model.User;
import com.ripple.provision.vm.service.AuthService;
import com.ripple.provision.vm.service.Impl.AuthServiceImpl;
import com.ripple.provision.vm.util.JwtUtil;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(value = "test")
@WebMvcTest(AuthController.class)
@ContextConfiguration(classes = AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AuthService authService;

      @Before
      public void setup() throws Exception {
          MockitoAnnotations.initMocks(this);
          AuthenticationResponse authenticationResponse = new AuthenticationResponse("testJWT");
          when(authService.authenticateUser(any(AuthenticationRequest.class))).thenReturn(authenticationResponse);
      }

    @Test
    public void authenticateUser() throws Exception {

        this.mockMvc.perform(post("/api/auth/sign-in").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"pavan44srar@gmail.com\",\"password\": \"password\"}"))
                .andExpect(status().isOk());

    }

    @Test
    public void registerUser() throws Exception {
        User user = new User();
        AuthService authService = Mockito.mock(AuthService.class);
        when(authService.registerUser(user)).thenReturn(new Status("Success"));
        this.mockMvc.perform(post("/api/auth/sign-up").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\" : \"pavan44srar@gmail.com\",\"password\" : \"password\",\"email\" : \"pavan44srar@gmail.com\",\"name\" : \"Pavan Mohan\",\"phoneNumber\": \"+911234567890\",\"country\": \"Wakhanda\",\"designation\": \"Manager\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void throw_error_when_media_type_is_not_json() throws Exception {

        this.mockMvc.perform(post("/api/auth/sign-in").accept(MediaType.APPLICATION_ATOM_XML).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"pavan44srar@gmail.com\",\"password\": \"password\"}"))
                .andExpect(status().isNotAcceptable());

    }

    @Test
    public void throw_error_when_register_user_body_is_not_acceptable() throws Exception {
        User user = new User();
        AuthService authService = Mockito.mock(AuthService.class);
        when(authService.registerUser(user)).thenReturn(new Status("Success"));
        this.mockMvc.perform(post("/api/auth/sign-up").accept(MediaType.APPLICATION_ATOM_XML).contentType(MediaType.APPLICATION_ATOM_XML)
                        .content("{\"userId\" : \"pavan44srar@gmail.com\",\"password\" : \"password\",\"email\" : \"pavan44srar@gmail.com\",\"name\" : \"Pavan Mohan\",\"phoneNumber\": \"+911234567890\",\"country\": \"Wakhanda\",\"designation\": \"Manager\"}"))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void throw_url_not_found_exception() throws Exception {
        User user = new User();
        AuthService authService = Mockito.mock(AuthService.class);
        when(authService.registerUser(user)).thenReturn(new Status("Success"));
        this.mockMvc.perform(post("/api/auth/sig-up").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\" : \"pavan44srar@gmail.com\",\"password\" : \"password\",\"email\" : \"pavan44srar@gmail.com\",\"name\" : \"Pavan Mohan\",\"phoneNumber\": \"+911234567890\",\"country\": \"Wakhanda\",\"designation\": \"Manager\"}"))
                .andExpect(status().isNotFound());
    }



}
