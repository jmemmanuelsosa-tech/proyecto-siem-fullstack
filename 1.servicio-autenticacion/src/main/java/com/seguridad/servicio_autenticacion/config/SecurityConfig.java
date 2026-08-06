package com.seguridad.servicio_autenticacion.config;

import com.seguridad.servicio_autenticacion.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    //Este Bean activa el encriptador profesional BCrypt en todo el sistema
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Mantenemos tus reglas de tránsito y filtros de las rutas
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults()) // <-- Habilita CORS
            .csrf(csrf -> csrf.disable())
           // Autorización de peticiones
            .authorizeHttpRequests(auth -> auth
           // Permitir acceso público a endpoints de autenticación
               .requestMatchers("/api/auth/**", "/api/logs").permitAll()
            // Cualquier otra petición requiere autenticación
                .anyRequest().authenticated()
            );

        return http.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
}
}

