package edu.cit.tiu.josephericson.campusequipmentloan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/login", "/logout").permitAll()
                        .requestMatchers("/h2-console/**").permitAll() // ✅ allow h2
                        .requestMatchers("/login", "/default-ui.css").permitAll()
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginProcessingUrl("/login")     // form action="/login"
                        .defaultSuccessUrl("/api/equipment", true) // redirect here after success
                        .failureUrl("/login?error=true")  // redirect on failed login
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());


        http.httpBasic(Customizer.withDefaults()); // enables Basic Auth for APIs
        // ✅ For H2 console frames

        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));


        return http.build();
    }
}