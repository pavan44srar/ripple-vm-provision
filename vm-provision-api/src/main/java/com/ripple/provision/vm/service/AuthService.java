package com.ripple.provision.vm.service;

import com.ripple.provision.vm.model.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthService {

    public AuthenticationResponse authenticateUser(AuthenticationRequest authenticationRequest) throws Exception;
    public UserInfo loadUserByUsername(String userName) throws UsernameNotFoundException;

    public Status registerUser(User user) throws Exception;
}
