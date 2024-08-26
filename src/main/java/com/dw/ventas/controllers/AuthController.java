package com.dw.ventas.controllers;

import com.dw.ventas.exception.impl.BadRequestException;
import com.dw.ventas.models.*;
import com.dw.ventas.services.JwtUtilService;
import com.dw.ventas.services.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtilService jwtUtilService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(final UsuarioService usuarioService,
                          final AuthenticationManager authenticationManager,
                          final JwtUtilService jwtUtilService,
                          final PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.jwtUtilService = jwtUtilService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@RequestBody @Valid final RegisterRequest registerRequest) {
        return usuarioService.registerUser(registerRequest, passwordEncoder.encode(registerRequest.getContrasena()));
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> login(@RequestBody @Valid final AuthenticationRequest authenticationRequest) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsuario(),
                            authenticationRequest.getContrasena()));
        } catch (BadCredentialsException exception) {
            throw new BadRequestException.Builder()
                    .errorMessageKey("Usuario o contrasena invalido")
                    .errorMessageArgs(exception)
                    .cause(exception)
                    .build();
        }

        logger.info("Autenticando al usuario {}", authenticationRequest.getUsuario());

        final UserDetails userDetails = usuarioService.loadUserByUsername(
                authenticationRequest.getUsuario());
        final String jwt = jwtUtilService.generateToken(userDetails);

        final UsuarioResponse usuarioResponse = usuarioService.findUserByEmail(userDetails.getUsername());

        final TokenResponse tokenResponse = TokenResponse.builder()
                .usuario(usuarioResponse)
                .token(jwt)
                .build();
        return ResponseEntity.ok(tokenResponse);
    }
}
