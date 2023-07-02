package com.restaurantreservation.api.global.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtManager jwtManager;

//    @Override
//    public void doFilter(ServletRequest request,
//                         ServletResponse response,
//                         FilterChain chain) throws IOException, ServletException {
//
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//
//        String jwt = resolveToken(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION));
//        String requestURI = httpServletRequest.getRequestURI();
//
//        if (StringUtils.hasText(jwt) && jwtManager.valid(jwt)) {
//            Authentication authentication = jwtManager.getAuthentication(jwt); // 정상 토큰이면 SecurityContext 저장
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            log.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
//        } else {
//            log.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
//        }
//
//        chain.doFilter(request, response);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = resolveToken(request.getHeader(HttpHeaders.AUTHORIZATION));
        String requestURI = request.getRequestURI();

        if (StringUtils.hasText(jwt) && jwtManager.valid(jwt)) {
            Authentication authentication = jwtManager.getAuthentication(jwt); // 정상 토큰이면 SecurityContext 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        } else {
            log.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 토큰이 다름 조건을 만족하는 경우 정제하여 반환한다.
     * 1. StringUtils.hasText(token): token != null && token.trim().length > 0
     * 2. token.startsWith("Bearer ")
     * @param token
     * @return
     */
    private String resolveToken(String token) {
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}

