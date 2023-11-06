package com.Lotus.polyFood.Security.Jwt;

import com.Lotus.polyFood.Security.Service.UserDetailServiceImpl;
import com.Lotus.polyFood.Security.Service.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    JwtUltis jwtUltis;
    @Autowired
    UserDetailServiceImpl userDetailService;
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = parseJwt(request);
            if (token!=null|| jwtUltis.validateJwtToken(token)){
                String username = jwtUltis.getUsernameFromJwtToken(token);
                UserDetails userDetails =userDetailService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (Exception ex){
            logger.error("Cannot set user authentication: {}", ex);
        }
        filterChain.doFilter(request,response);
    }
    private String parseJwt(HttpServletRequest request){
        String header = request.getHeader("Authorization");

        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }

        return null;
    }
}
