package hr.tvz.napredna.java.dijezetserver.security;

import hr.tvz.napredna.java.dijezetserver.dto.UserDto;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {
    public static String createToken(UserDto user, SecretKey key) {
        return Jwts.builder()
                .claims().add("role", user.getRole()).and()
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .signWith(key)
                .compact();
    }

    public static boolean validateToken(String token, SecretKey key) {
        return Jwts.parser().verifyWith(key).build().isSigned(token);
    }

    public static String extractUsername(String token, SecretKey key) throws JwtException {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}