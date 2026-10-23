
package com.example.studentregister.jwtService;

import com.example.studentregister.config.CustomUserDetailsService;
import com.example.studentregister.user.orm.User;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // اگر هدر Authorization وجود ندارد، درخواست ادامه پیدا می‌کند.
        // مسیرهای عمومی و محافظت‌شده در SecurityConfig تعیین می‌شوند.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7).trim();

        // هدر Bearer بدون توکن معتبر نیست.
        if (jwt.isEmpty()) {
            writeUnauthorizedResponse(request, response);
            return;
        }

        try {
            String username = jwtService.extractUsername(jwt);

            if (username == null || username.isBlank()) {
                writeUnauthorizedResponse(request, response);
                return;
            }

            // اگر قبلاً کاربر احراز هویت شده باشد، دوباره احراز هویت نمی‌کنیم.
            if (SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(username);

                // این پروژه از کلاس User به‌عنوان UserDetails استفاده می‌کند.
                if (!(userDetails instanceof User user)
                        || !jwtService.isTokenValid(jwt, user)) {

                    SecurityContextHolder.clearContext();
                    writeUnauthorizedResponse(request, response);
                    return;
                }

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }

        } catch (JwtException
                 | IllegalArgumentException
                 | UsernameNotFoundException ex) {

            SecurityContextHolder.clearContext();
            writeUnauthorizedResponse(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void writeUnauthorizedResponse(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        if (response.isCommitted()) {
            return;
        }

        SecurityContextHolder.clearContext();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> body = Map.of(
                "status", 401,
                "error", "Unauthorized",
                "message", "توکن نامعتبر یا منقضی شده است",
                "path", request.getRequestURI()
        );

        response.getWriter().write(
                new ObjectMapper()
                        .writeValueAsString(body)
        );
    }
}
