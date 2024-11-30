package com.jesus.sales.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component //para que spring la gestione
public class JwtTokenUtil implements Serializable {

    //definir tiempo de vida en milisegundos
    public final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000; // 5 hours

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining()));  //Spring reconoce los roles como Authritiest
        claims.put("test", "value test");
        return doGenerateToken(claims, userDetails.getUsername());
    }

    //subject -> usuario que genero el token
    public String doGenerateToken(Map<String, Object> claims, String subject) {
        // retornar el token con sus atributos , expiracion, firma, sujeto, etc.
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    //metodo para obtener informacion de cuando venga el token en los rquests
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //funcion que recibe el token y empieza a decodificar el token conel metodo getAllClaimsFromToken
    // y asi obtener la informacion
    //abajo se uso este metodo.
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //aca uso el metodo getClaimFromToken para obtener lo que le pido en este caso el subject
    // saber quien genero el token, entra el token y retorno quien.
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //para saber la fecha de expiracion del token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    //saber si esque el token esta expirado
    //una comprobacion si la fecha actual esta antes de la fecha del token
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //para saber si el token es valido, comprobar el username si es igual el del userDetails y aparte no expirado.
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
