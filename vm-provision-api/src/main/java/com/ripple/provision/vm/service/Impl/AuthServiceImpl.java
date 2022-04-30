package com.ripple.provision.vm.service.Impl;

import com.ripple.provision.vm.model.*;
import com.ripple.provision.vm.repository.RoleRepository;
import com.ripple.provision.vm.repository.UserRepository;
import com.ripple.provision.vm.util.JwtUtil;
import com.ripple.provision.vm.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public AuthenticationResponse authenticateUser(AuthenticationRequest authenticationRequest) throws Exception{
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(), authenticationRequest.getPassword()));
            } catch (BadCredentialsException e) {
                throw new AuthenticationException("Incorrect username or password");
            } catch (Exception e) {
                throw new Exception("No Role assigned, reach out to admin", e);
            }
            final UserInfo userInfo = loadUserByUsername(authenticationRequest.getUsername());
            final String jwt = jwtTokenUtil.generateToken(userInfo);
            return new AuthenticationResponse(jwt);
    }

    @Override
    public UserInfo loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserId(userName);
        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
        return user.map(UserInfo::new).get();
    }

    @Override
    public Status registerUser(User user) throws Exception {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userRepository.findByUserId(user.getUserId()).isPresent()) {
            throw new Exception("UserId already exists");
        }
        userRepository.save(user);
        return new Status("Success");
    }
}
