package com.cognizant.microservices.authservice.security;


import com.cognizant.microservices.authservice.exceptions.TokenInvalidException;
import com.cognizant.microservices.authservice.model.LoginUser;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class TokenHandler {

    private static String secret = "secret";

    public String extractUsername(String token) {
        String s = extractClaim(token, Claims::getSubject);
        return s;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        T ap = claimsResolver.apply(claims);
        return ap;
    }
    private Claims extractAllClaims(String token) {
        Claims c = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return c;
    }

    public String generateToken(String userName){

        long time1 = System.currentTimeMillis();
        long time2 = time1+(2*60*60*1000);
        Date dateIssued = new Date(time1);
        Date dateExpiring = new Date(time2);

        Claims claims = Jwts.claims().setIssuer(userName).setSubject(userName)
                .setIssuedAt(dateIssued)
                .setExpiration(dateExpiring);

        return Jwts.builder().setClaims(claims)
                .signWith(SignatureAlgorithm.HS256,secret).compact();
    }

    public boolean validateToken(String token) throws TokenInvalidException {
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }
        catch (SignatureException ex) {
            log.error("Invalid JWT signature: {}", ex.getMessage());
            throw new TokenInvalidException("Invalid JWT signature: " + ex.getMessage());
        }
        catch (ExpiredJwtException ex) {
            log.error("The token has expired {}", ex.getMessage());
            throw new TokenInvalidException("Invalid JWT signature: " + ex.getMessage());
        }
        catch(Exception ex){
            log.error("****** Something went wrong with the token *******");
            throw new TokenInvalidException("Invalid JWT signature: " + ex.getMessage());
        }
    }
}
