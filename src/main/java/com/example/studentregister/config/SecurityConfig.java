
package com.example.studentregister.config;

import com.example.studentregister.jwtService.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ObjectMapper objectMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(customUserDetailsService);

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                // غیرفعال‌سازی CSRF برای API مبتنی بر JWT
//                .csrf(csrf -> csrf.disable())

                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                // عدم استفاده از Session
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // مدیریت خطاهای احراز هویت و مجوز دسترسی
                .exceptionHandling(exception -> exception

                        .authenticationEntryPoint(
                                (request, response, authException) -> {

                                    response.setStatus(
                                            HttpServletResponse.SC_UNAUTHORIZED
                                    );
                                    response.setContentType(
                                            MediaType.APPLICATION_JSON_VALUE
                                    );
                                    response.setCharacterEncoding("UTF-8");

                                    Map<String, Object> body =
                                            new LinkedHashMap<>();

                                    body.put("status", 401);
                                    body.put("error", "Unauthorized");
                                    body.put(
                                            "message",
                                            "احراز هویت انجام نشده است"
                                    );
                                    body.put(
                                            "path",
                                            request.getRequestURI()
                                    );

                                    objectMapper.writeValue(
                                            response.getOutputStream(),
                                            body
                                    );
                                }
                        )

                        .accessDeniedHandler(
                                (request, response, exception403) -> {

                                    response.setStatus(
                                            HttpServletResponse.SC_FORBIDDEN
                                    );
                                    response.setContentType(
                                            MediaType.APPLICATION_JSON_VALUE
                                    );
                                    response.setCharacterEncoding("UTF-8");

                                    Map<String, Object> body =
                                            new LinkedHashMap<>();

                                    body.put("status", 403);
                                    body.put("error", "Forbidden");
                                    body.put(
                                            "message",
                                            "شما مجوز دسترسی به این منبع را ندارید"
                                    );
                                    body.put(
                                            "path",
                                            request.getRequestURI()
                                    );

                                    objectMapper.writeValue(
                                            response.getOutputStream(),
                                            body
                                    );
                                }
                        )
                )


                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                HttpMethod.POST,
                                "/auth/register"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/auth/gender-custom-combo",
                                "/auth/user-list-grid"
                        ).permitAll()

                        .requestMatchers("/error").permitAll()

                        .anyRequest().authenticated()
                )

                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
