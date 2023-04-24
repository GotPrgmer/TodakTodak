package com.ssafy.todaktodak.global.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.todaktodak.domain.user.domain.Role;
import com.ssafy.todaktodak.global.error.ErrorCode;
import com.ssafy.todaktodak.global.error.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String path = request.getServletPath();
            if (path.endsWith("reissue")) {
                filterChain.doFilter(request, response);
            } else {
                String token = request.getHeader("Authorization").replace("Bearer ", "");
                boolean isTokenValid = jwtProvider.validate(token);
                if (StringUtils.hasText(token) && isTokenValid) {
                    String userRole = jwtProvider.getRoleFromToken(token);
                    Role tokenRole = null;
                    if (userRole.equals("ROLE_ADMIN")) {
                        tokenRole = Role.ADMIN;
                    } else {
                        tokenRole = Role.USER;

                    }

                    String userId = jwtProvider.getId(token);

                    Authentication authentication = jwtProvider.getAuthentication(jwtProvider.createJwt(userId,tokenRole),token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
                filterChain.doFilter(request, response);
            }
        } catch (ExpiredJwtException e) {
            response.setStatus(ErrorCode.TOKEN_NOT_VALID.getStatus().value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(new ErrorResponse(ErrorCode.TOKEN_NOT_VALID)));
            response.getWriter().flush();
            response.getWriter().close();

        }

    }
}

