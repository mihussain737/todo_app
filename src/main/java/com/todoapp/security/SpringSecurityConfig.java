package com.todoapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers(HttpMethod.GET, "api/todos/**").permitAll(); // Allow GET requests to be accessed by anyone
                    authorize.requestMatchers(HttpMethod.POST, "api/todos/**").hasAnyRole("ADMIN");
                    authorize.requestMatchers(HttpMethod.PUT, "api/todos/**").hasAnyRole("ADMIN");
                    authorize.requestMatchers(HttpMethod.DELETE, "api/todos/**").hasAnyRole( "ADMIN");
                    authorize.requestMatchers(HttpMethod.PATCH, "api/todos/**").hasAnyRole("ADMIN","USER");
                    authorize.anyRequest().authenticated(); // Any other request requires authentication
                })
                .httpBasic(Customizer.withDefaults()); // Enable HTTP Basic Authentication

        return httpSecurity.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception{
        return configuration.getAuthenticationManager();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("admin123")
//                .roles("ADMIN")
//                .build();
//        UserDetails manager = User.withDefaultPasswordEncoder()
//                .username("manager")
//                .password("manager123")
//                .roles("MANAGER")
//                .build();
//        return new InMemoryUserDetailsManager(admin, manager);
//    }
}
