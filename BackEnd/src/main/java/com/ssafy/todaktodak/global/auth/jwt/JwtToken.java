package com.ssafy.todaktodak.global.auth.jwt;

import com.ssafy.todaktodak.domain.user.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class JwtToken {

    private final String userId;

    private final Key key;

    private final String role;

    private final Date expiry;

    private static final String AUTHORITIES_KEY = "role";

    JwtToken(String userId, Role role, Date expiry, Key key) {
        String roleToString = role.getGrantedAuthority(); // ROLE_USER, ROLE_ADMIN
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
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiry)
                .signWith(key)
                .compact();
    }


}
