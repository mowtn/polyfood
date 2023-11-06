package com.Lotus.polyFood.Security;

import com.Lotus.polyFood.Security.Jwt.AuthEntryPointJwt;
import com.Lotus.polyFood.Security.Jwt.AuthTokenFilter;
import com.Lotus.polyFood.Security.Service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {
    @Autowired
    UserDetailServiceImpl userDetailService;

    @Autowired
    AuthEntryPointJwt unAuthorizeHandler;
    @Bean
    public AuthTokenFilter authTokenFilter(){
        return new AuthTokenFilter();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;

    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception{
        return auth.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unAuthorizeHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/test/**","/swagger-ui/**",
                                "/swagger-resources/*",
                                "/v3/api-docs/**",
                                "/order/payment_info").permitAll()
                        .anyRequest().authenticated());

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
