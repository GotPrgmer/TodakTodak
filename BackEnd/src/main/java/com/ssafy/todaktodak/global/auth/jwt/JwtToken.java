package com.ssafy.todaktodak.global.auth.jwt;

import com.ssafy.todaktodak.domain.user.domain.Role;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtToken {

    private final String userId;

    private final Key key;

    private final String role;

    private final Date expiry;

    private static final String AUTHORITIES_KEY = "role";

    JwtToken(String userId, Role role, Date expiry, Key key) {
        String roleToString = role.getGrantedAuthority(); // USER, ADMIN
        this.userId = userId;
        this.key = key;
        this.role = roleToString;
        this.expiry = expiry;
    }

    public String createAccessToken() { // AccessToken(AppToken) 생성
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put(AUTHORITIES_KEY, role);
        Date currentTime = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(currentTime)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken() {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put(AUTHORITIES_KEY, role);
        Date currentTime = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(currentTime)
                .setExpiration(expiry)
                .signWith(key)
                .compact();
    }

    public boolean validate(String token) { // AccessToken(AppToken) 유효한지 체크
        return this.getTokenClaims(token) != null;
    }

    public Claims getTokenClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody(); // token의 Body가 하기 exception들로 인해 유효하지 않으면 각각에 해당하는 로그 콘솔에 찍음
        } catch (SecurityException e) {
            log.info("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            // 처음 로그인(/auth/kakao, /auth/google) 시, AccessToken(AppToken) 없이 접근해도 token validate을 체크하기 때문에 exception 터트리지 않고 catch합니다.
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
        }
        return null;
    }

}
