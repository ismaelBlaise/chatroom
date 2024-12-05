package com.ms.chatroom.configs;

import com.ms.chatroom.security.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {   

    
    @SuppressWarnings({ "removal" })
    public void configure(HttpSecurity http) throws Exception {  
        http
            .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class).formLogin(null) 
            .authorizeHttpRequests()
                .requestMatchers("api/auth/login", "api/auth/register").permitAll() 
                .anyRequest().authenticated();  
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
