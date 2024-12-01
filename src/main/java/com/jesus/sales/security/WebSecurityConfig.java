package com.jesus.sales.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@Profile(value={"development", "production"})
@Configuration //indica configuracion spring, contiene beans
@EnableWebSecurity //habilita seguridad en la aplicacion web , spring security se encargara de gestionar esta, authenticacion, autheorizacion y roles.
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    //generando instancia en contenedor de Sprting de un manejador de authenticacion
    //es responsable de gestionar el proceso de autenticación, verificando las credenciales del usuario (nombre y contraseña) durante el proceso de inicio de sesión.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //genrando istancia un metodo para codificar al password para guardar en bd
    //lo necesito en spring para saber que lo que envian en clear es lo mismo que en bd
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //autowired en un metodo, esta buscando una instancia del parametro en este caso AuthenticationmanagerBuilder
    //este metodo utiliza los anterioes creaados arriba
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //Le indica a Spring Security que use el servicio jwtUserDetailsService para cargar los detalles del usuario durante el proceso de autenticación
        //y usa el passwordEncoder para validar las contraseñas
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    //aca en este metodo mdodicairemos el comportamiento de un filtro que srping gestiona interanamente
    //desabilitamos csrf, es un ataque a formularios mediante codifo malicioso, desabilitamos porque no tenemos front.
    //luego indicamos que rutas permitir , como/login, , tmb puedes permtirlo solo para roles.
    //tambien por patrones /**
    //y cualquier otra url, pide proteccion .anyRequest().authenticated()
    //e indicamos que si hubiera una expcepcion que devuelva el authenticationEntruPoint qye creamos .
    //desactivamos el login que sale cuando agregas spring security al pom
    //como es una aplicacion rest y con token, indicamos que sera STATELESS.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/login").permitAll() //.hasAuthority("ADMIN")
                .requestMatchers("/categories/**").permitAll()
                .requestMatchers("/rest/**").permitAll()
                .requestMatchers("/v3/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //tambien le indicamos que despues de su filtro por defecto, valide nuestro fultro que hemos creado "jwtRequestFilter"
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        // ygeneramos la peticion
        return httpSecurity.build();
    }


}
