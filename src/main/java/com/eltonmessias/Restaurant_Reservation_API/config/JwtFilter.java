package com.eltonmessias.Restaurant_Reservation_API.config;

import com.eltonmessias.Restaurant_Reservation_API.enums.ROLE;
import com.eltonmessias.Restaurant_Reservation_API.service.JwtService;
import com.eltonmessias.Restaurant_Reservation_API.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    ApplicationContext context;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            email = jwtService.extractUserEmail(token);
        }


        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(email);

            if(jwtService.validateToken(token, userDetails)) {
                ROLE role = jwtService.extractRole(token);
                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
               UsernamePasswordAuthenticationToken authToken =
                       new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
               authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
