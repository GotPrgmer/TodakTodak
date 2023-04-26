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




    private final String AUTHORITIES_KEY = "role";

    @Value("${jwt.expiration-accesstoken-minutes}")
    private String AccessTokenExpiry;

    public JwtProvider(@Value("${jwt.secret}") String jwtSecretKey) {
        this.key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
    }

    public JwtToken createJwt(String id, Role role) { // 추후 roleType 추가 시 interface 역할 하기 위해 생성
        Date expiryDate = getExpiryDate();
        return new JwtToken(id, role, expiryDate, key);
    }




    public Date getExpiryDate() { // String to Date
        return new Date(System.currentTimeMillis() + Long.parseLong(this.AccessTokenExpiry));
    }

    public String getId(String token) {
        String id = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
        return id;
    }

    public Authentication getAuthentication(JwtToken jwtToken, String token) {
        Claims claims = this.getTokenClaims(token);
        String authority = (String) claims.get(AUTHORITIES_KEY);
        Role inputAuthority = null;
        SimpleGrantedAuthority simpleAuthority = new SimpleGrantedAuthority(authority);
        log.info(simpleAuthority.toString());
        log.info(simpleAuthority.getAuthority());
        if (simpleAuthority.getAuthority().equals("ROLE_USER")) {
            inputAuthority = Role.USER;
        } else {
            inputAuthority = Role.ADMIN;
        }
        User principal = User.principalUser(Integer.parseInt(claims.getSubject()), inputAuthority);// 사실상 principal에 저장되는 값은 userId값과 role뿐(소셜 로그인만 사용하여 password 저장하지 않아 ""로 넣음)
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleAuthority);

        return new UsernamePasswordAuthenticationToken(principal, null, authorities);
    }

    public boolean validate(String token) { // AccessToken(AppToken) 유효한지 체크
        return this.getTokenClaims(token) != null;
    }

    public String getRoleFromToken(String token) {
        Claims claims = getTokenClaims(token);
        String roleList = claims.get(AUTHORITIES_KEY, String.class);
        return roleList;
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
