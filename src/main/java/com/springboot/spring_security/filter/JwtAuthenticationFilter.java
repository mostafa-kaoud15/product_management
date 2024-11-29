package com.springboot.spring_security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springboot.spring_security.model.User;
import com.springboot.spring_security.service.UserService;
import com.springboot.spring_security.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// @RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserService UserService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserService UserService) {
        this.jwtUtil = jwtUtil;
        this.UserService = UserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);
            try {
                String username = jwtUtil.extractUsername(token);
                User user = UserService.loadUserByUsername(username);

                if (jwtUtil.validateToken(token, user)
                        && SecurityContextHolder.getContext().getAuthentication() == null) {
                    setupSecurityContext(user);
                }

            } catch (Exception ex) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("this token is invalid : " + ex);
                return;
            }
        }

        filterChain.doFilter(request, response);

    }

    public void setupSecurityContext(UserDetails user) {
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));

    }

}
