package com.dw.ventas.controllers;

import com.dw.ventas.services.JwtUtilService;
import com.dw.ventas.services.UsuarioService;
import com.dw.ventas.models.AuthenticationReq;
import com.dw.ventas.models.TokenResponse;
import com.dw.ventas.models.UsuarioDTO;
import com.dw.ventas.models.UsuarioResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtilService jwtUtilService;

    @Autowired
    public AuthController(UsuarioService usuarioService,
                          AuthenticationManager authenticationManager,
                          JwtUtilService jwtUtilService) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.jwtUtilService = jwtUtilService;
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody final UsuarioDTO usuarioDTO) {
        usuarioService.registrarUsuario(usuarioDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> autenticacion(@RequestBody AuthenticationReq authenticationReq) {
        logger.info("Autenticando al usuario {}", authenticationReq.getUsuario());

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationReq.getUsuario(),
                            authenticationReq.getClave()));

        final UserDetails userDetails = usuarioService.loadUserByUsername(
                authenticationReq.getUsuario());

        final String jwt = jwtUtilService.generateToken(userDetails);

        final UsuarioResponse usuarioResponse = usuarioService.getUserByEmail(userDetails.getUsername());

        final TokenResponse tokenResponse = TokenResponse.builder()
                .usuario(usuarioResponse)
                .token(jwt)
                .build();
        return ResponseEntity.ok(tokenResponse);
    }

}
