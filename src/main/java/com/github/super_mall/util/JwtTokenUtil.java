package com.github.super_mall.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

import static java.lang.System.getenv;

@Component
public class JwtTokenUtil {
    private Map<String, String> env = getenv();
    private Date now = new Date();

    public String createToken (String email) {


        return Jwts.builder()
                .setIssuer(env.get("ISSUER"))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Constants.TOKENVAILIDMILLISECOND))
                .claim("email", email)
                .signWith(SignatureAlgorithm.HS256, env.get("SECRET_KEY"))
                .compact();
    }

    public String resolveToken (HttpServletRequest request) {
       String bearerToken = request.getHeader(Constants.HEADER_TOKEN_KEY);
       if(bearerToken == null && !bearerToken.startsWith("Bearer ")) {
           return null;
       }
       return bearerToken.substring(7);
    }

    public boolean validation (String token) {
        boolean isInCompeteToken = isExpired(token);
        return isInCompeteToken && Jwts.parser().setSigningKey(env.get("SECRET_KEY")).parseClaimsJws(token) != null;
    }

    public boolean isExpired (String token) {
        return Jwts.parser()
                .setSigningKey(env.get("SECRET_KEY"))
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(now);
    }

    public Authentication getAuthentication (String token) {
        String email = getUserEmail(token);


    }

    public String getUserEmail (String token) {
        return Jwts.parser()
                .setSigningKey(env.get("SECRET_KEY"))
                .parseClaimsJws(token)
                .getBody()
                .get("email", String.class);
    }


}
