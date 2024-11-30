package com.jesus.sales.controller;

import com.jesus.sales.security.JwtRequest;
import com.jesus.sales.security.JwtResponse;
import com.jesus.sales.security.JwtTokenUtil;
import com.jesus.sales.security.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    //en este post recivo JwtRequest, que es username y password
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest req) throws Exception {
        //lo mandamos a authenticar
        authenticate(req.getUsername(), req.getPassword());

        //extraigo la info del usuario
        final UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUsername());

        //luego genero un token en base al usuario que esta solicitando
        final String token = jwtTokenUtil.generateToken(userDetails);

        //y responde ese token
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            //en nuestro WebSecurityConfig indicamos que el manejador de authenticacion que es lo que tiene que hacer
            //entonces ya esta linea se encarga de ejecutar ese codigo de verificacion si el token y el usauriu es corecto
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
