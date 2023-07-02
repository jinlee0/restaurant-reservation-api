package com.restaurantreservation.api.global.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtManager implements InitializingBean {
    private static final String AUTH_KEY = "auth";
    private final long expiredMillis;
    private final String secret;
    private Key key;

    public JwtManager(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expired-millis}") long expiredMillis) {
        this.secret = secret;
        this.expiredMillis = expiredMillis;
    }

    @Override
    public void afterPropertiesSet() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String generate(Authentication authentication) {
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + expiredMillis);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTH_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    /**
     * 토큰이 유효한지 검사한다.
     * @param token 검사할 토큰.
     */
    public boolean valid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    /**
     * token을 이용해 Authentication 객체를 만든다.
     * @param token token
     * @return Authentication from token
     */
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get(AUTH_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public String appendPrefix(String token) {
        return "Bearer " + token;
    }
}
