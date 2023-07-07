package com.example.ecommerce.security.filter;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
    private String jwtKey = "abcdef";

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public Date extractExpirations(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    public Boolean hasClaim(String token, String claimName){
        Claims claims = extractAllClaims(token);
        return claims.get(claimName) != null;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    public Claims extractAllClaims (String token){
        return Jwts.parser().setSigningKey(jwtKey).parseClaimsJwt(token).getBody();
    }

    public Boolean isTokenExpired(String token){
        return extractExpirations(token).before(new Date());
    }
    public String generateToken(UserDetails userDetails){
        Map<String , Object> claims = new HashMap<>();
        return createToken(claims, userDetails);
    }
    public String generateToken(UserDetails userDetails, Map<String, Object> claims){
        return createToken(claims, userDetails);
    }

    private String createToken(Map<String, Object> claims, UserDetails userDetails){
        return Jwts.builder().setClaims(claims)
                .setSubject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)))
                .signWith(SignatureAlgorithm.HS256, jwtKey).compact();
    }
    public Boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
