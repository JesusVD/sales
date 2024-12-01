package com.jesus.sales.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@Profile(value={"development", "production"}) //esto me indica que este file estara disponible solo en dev y prod
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //capturamos el campo Authroization del header.
        final String tokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        //evaluo que haya token y empieze con bearer
        if (tokenHeader != null){
            if(tokenHeader.startsWith("Bearer ") || tokenHeader.startsWith("bearer ")){
                //extraigo solo la cadena ,excluyo la palabra "bearer "
                jwtToken = tokenHeader.substring(7);
                //seteo el nombre de usuario sacando esa info del token
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            }
        }

        if (username != null) {
            //extraigo la la info como un UserDetails
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

            //y si el token es valido
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                //genera una instancia en memoria de spring usada para representar una authenticacion en curso
                //y mantiene la info del usuario autenticado, aca indica que un user ha sido autenticado con sus detalles y roles
                //pasamos null en credentials porque no es necesario guardar eso.
                UsernamePasswordAuthenticationToken userPassAuthToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                //generar la peticion
                userPassAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //y agregarla al contexto de espring, que se guarde en memoria el usuario que acaba de generar el token
                SecurityContextHolder.getContext().setAuthentication(userPassAuthToken);
            }
        }
        //y aqui es continua , dejalo pasar todo es valido.
        filterChain.doFilter(request, response);
    }
}
