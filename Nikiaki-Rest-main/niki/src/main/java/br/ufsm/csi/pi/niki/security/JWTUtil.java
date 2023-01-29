package br.ufsm.csi.pi.niki.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {
    public static final long LiFE_TIME = Duration.ofSeconds(36000).toMillis();

    public String geraToken(String username) {
        final Map<String, Object> claims = new HashMap<>();
        claims.put("sub", username);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + LiFE_TIME))
                .signWith(SignatureAlgorithm.HS256, "nikiaki")
                .compact();
    }

    public String getUsernameToken(String token) {
        if (token != null) {
            return this.parseToken(token).getSubject();
        }
        else  {
            return null;
        }
    }

    public boolean isTokenExpired(String token) {
        if (token != null) {
            return this.parseToken(token).getExpiration().before(new Date());
        }
        else  {
            return false;
        }
    }

    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey("nikiaki")
                .parseClaimsJws(token.replace("Bearer", ""))
                .getBody();
    }
}
