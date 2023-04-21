package com.ssafy.todaktodak.global.auth.jwt;

import com.ssafy.todaktodak.domain.user.domain.Role;
import com.ssafy.todaktodak.domain.user.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider {

    private final Key key;

    private

    @Value("${jwt.secret}")
    String jwtSecretKey;


    private static final String AUTHORITIES_KEY = "role";

    @Value("${jwt.expiration-accesstoken-minutes}")
    private String AccessTokenExpiry;

    public JwtProvider(@Value("${jwt.secret}") String jwtSecretKey) {
        this.key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
    }

    public JwtToken createJwt(String id, Role role) { // 추후 roleType 추가 시 interface 역할 하기 위해 생성
        Date expiryDate = getExpiryDate(AccessTokenExpiry);
        return new JwtToken(id, role, expiryDate, key);
    }


    public Date getExpiryDate(String AccessTokenExpiry) { // String to Date
        return new Date(System.currentTimeMillis() + Long.parseLong(AccessTokenExpiry));
    }

    public String getId(String token) {
        String id = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
        return id;
    }

    public Authentication getAuthentication(JwtToken jwtToken, String token) {
        Claims claims = jwtToken.getTokenClaims(token);
        String authority = (String) claims.get(AUTHORITIES_KEY);
        Role inputAuthority = null;
        SimpleGrantedAuthority simpleAuthority = new SimpleGrantedAuthority(authority);
        if (simpleAuthority.getAuthority().equals("ROLE_USER")) {
            inputAuthority = Role.USER;
        } else {
            inputAuthority = Role.ADMIN;
        }
        User principal = User.principalUser(Integer.parseInt(claims.getSubject()), inputAuthority);// 사실상 principal에 저장되는 값은 socialId값과 role뿐(소셜 로그인만 사용하여 password 저장하지 않아 ""로 넣음)
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleAuthority);
        return new UsernamePasswordAuthenticationToken(principal, jwtToken, authorities);

    }




}
