package com.epam.esm.jwt;

import com.epam.esm.jwt.util.TokenInterpreter;
import com.google.common.base.Strings;
import com.google.common.net.HttpHeaders;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class JwtTokenVerifier extends OncePerRequestFilter {

    private final TokenInterpreter tokenInterpreter;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(tokenInterpreter.getTokenPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorizationHeader.replace(tokenInterpreter.getTokenPrefix(), "");
        //
        Authentication authentication = tokenInterpreter.parseTokenToAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //
        filterChain.doFilter(request, response);
    }
}
