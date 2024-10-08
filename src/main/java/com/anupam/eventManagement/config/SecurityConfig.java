package com.anupam.eventManagement.config;

import com.anupam.eventManagement.repository.UserRepository;
import com.anupam.eventManagement.service.impl.OauthAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OauthAuthenticationService oauthAuthenticationService;




    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider,
            UserRepository userRepository, PasswordEncoder passwordEncoder, OauthAuthenticationService oauthAuthenticationService
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.oauthAuthenticationService = oauthAuthenticationService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> {
//                            auth.requestMatchers(HttpMethod.OPTIONS, "*/**").permitAll();
                            auth
                                    .requestMatchers("/ws/**","/chat/**","/health-check/**","/comment/**","/auth/**", "/event/get-all", "/event/get-event/**", "*/login/oauth2/code/**", "/login-success","/feedback/feed/**","/ticket/ticket-sales/**","/ticket/get-no-ticket-booked/{eventId}")
                                    .permitAll();
                            auth.anyRequest().authenticated();
                        }
                )
                .oauth2Login(oauth->oauth
                        .successHandler(oauthAuthenticationService))
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:5173","http://localhost:5500","https://event-phi-one.vercel.app","http://localhost:5174","https://event-manager-frontend-kappa.vercel.app"));
        configuration.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}