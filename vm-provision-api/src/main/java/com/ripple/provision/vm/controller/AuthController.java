package com.ripple.provision.vm.controller;

import com.ripple.provision.vm.model.AuthenticationRequest;
import com.ripple.provision.vm.model.AuthenticationResponse;
import com.ripple.provision.vm.model.Status;
import com.ripple.provision.vm.model.User;
import com.ripple.provision.vm.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthService authService;

	@PostMapping(value = "/sign-in", consumes = "application/json", produces = "application/json")
	public AuthenticationResponse authenticateUser(@Valid @RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		return authService.authenticateUser(authenticationRequest);
	}

	@PostMapping("/sign-up")
	public Status registerUser(@Valid @RequestBody User user) throws Exception {
		return authService.registerUser(user);
	}
}
