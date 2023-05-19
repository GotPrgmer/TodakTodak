package com.ssafy.todaktodak.global.auth.config;

import com.ssafy.todaktodak.global.auth.jwt.JwtAuthorizationFilter;
import com.ssafy.todaktodak.global.auth.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtProvider jwtProvider;

    @Autowired
    public SecurityConfig(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers(
                        "/api/device/alarm/*",
                        "/api/device/alarm/**",
                        "/api/iot/sessions/*",
                        "/api/iot/sessions/**",
//                        "/api/sessions/*",
//                        "/api/sessions/**",
                        "/api/cry/record/*",
                        "/api/cry/record/**",
                        "/v2/api-docs/**",
                        "/api/login/oauth2/*",
                        "/api/login/oauth2/**",
                        "/webjars/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html/**",
                        "/swagger-resources/**",
                        "/swagger-ui.html"

                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .cors()
                .and()
                .formLogin().disable()
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers(
                        "/api/device/alarm/*",
                        "/api/device/alarm/**",
                        "/api/iot/sessions/*",
                        "/api/iot/sessions/**",
//                        "/api/sessions/*",
//                        "/api/sessions/**",
                        "/api/cry/record/*",
                        "/api/cry/record/**",
                        "/",
                        "/api/login/oauth2/*",
                        "/api/login/oauth2/**",
                        "/webjars/**",
                        "/swagger-ui.html/**", "/swagger-ui/**",
                        "/v2/api-docs/**", "/swagger-resources/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtAuthorizationFilter(jwtProvider),
                        UsernamePasswordAuthenticationFilter.class);

    }
}