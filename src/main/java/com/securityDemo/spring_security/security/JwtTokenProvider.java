package com.securityDemo.spring_security.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static  final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    //generate jwt token

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime()+jwtExpirationDate);

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expireDate)
                .signWith(key())
                .compact();
        return token;
    }



    //get username from JWT token

    public String getUsername(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token){
        try{
            System.out.println("Validate");
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parse(token);
            return true;
        }catch (MalformedJwtException e){
            logger.error("Invalid JWT token: {}", e.getMessage());
        }
        catch (ExpiredJwtException e){
            logger.error("JWT token is expired: {}", e.getMessage());
        }
        catch (UnsupportedJwtException e){
            logger.error("JWT token is unsupported: {}", e.getMessage());
        }
        catch (IllegalArgumentException e){
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}
