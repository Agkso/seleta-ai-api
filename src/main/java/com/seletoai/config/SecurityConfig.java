package com.seletoai.config;

import com.seletoai.config.jwt.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final JwtFilter jwtFilter;

  public SecurityConfig(JwtFilter jwtFilter) {
    this.jwtFilter = jwtFilter;
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.ignoring().requestMatchers(
      "/v3/api-docs/**",
      "/swagger-ui/**",
      "/swagger-ui.html"
    );
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // 1. ARRANCANDO O SWAGGER DO SPRING SECURITY
  // Isso garante que NENHUM filtro ou bloqueio afete o gerador de DTOs
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(AbstractHttpConfigurer::disable)

      // 2. BLINDAGEM CONTRA CORS (Agora cobrindo localhost e 127.0.0.1)
      .cors(cors -> cors.configurationSource(request -> {
        var config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
          "http://localhost:3000",
          "http://127.0.0.1:3000",
          "http://localhost:8081"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        return config;
      }))

      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

      .authorizeHttpRequests(auth -> auth
        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

        .requestMatchers(
          "/auth/**",
          "/v3/api-docs/**",
          "/swagger-ui/**",
          "/swagger-ui.html"
        ).permitAll()

        .anyRequest().authenticated()
      )
      .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}