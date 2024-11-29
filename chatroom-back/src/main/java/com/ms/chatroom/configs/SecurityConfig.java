package com.ms.chatroom.configs;

import com.ms.chatroom.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfiguration {  

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)  
            .authorizeRequests()
                .antMatchers("/login", "/register").permitAll()  
                .anyRequest().authenticated();  // Toute autre requête nécessite une authentification
    }

   
}
