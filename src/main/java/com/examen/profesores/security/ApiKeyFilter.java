package com.examen.profesores.security;

import com.examen.profesores.dto.ErrorResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Valida el header X-API-KEY en todo request a /api/** antes de llegar al controlador.
 */
@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    public static final String HEADER = "X-API-KEY";

    private final String apiKey;
    private final ObjectMapper objectMapper;

    public ApiKeyFilter(@Value("${api.key}") String apiKey, ObjectMapper objectMapper) {
        this.apiKey = apiKey;
        this.objectMapper = objectMapper;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return !(path.equals("/api") || path.startsWith("/api/"));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String recibida = request.getHeader(HEADER);
        if (recibida != null && coincide(recibida)) {
            filterChain.doFilter(request, response);
            return;
        }

        String mensaje = recibida == null
                ? "Falta el header " + HEADER
                : "API Key inválida";
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorResponseDTO body = new ErrorResponseDTO(LocalDateTime.now(), status.value(),
                status.getReasonPhrase(), mensaje, request.getRequestURI(), List.of());

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        objectMapper.writeValue(response.getWriter(), body);
    }

    private boolean coincide(String recibida) {
        return MessageDigest.isEqual(
                recibida.getBytes(StandardCharsets.UTF_8),
                apiKey.getBytes(StandardCharsets.UTF_8));
    }
}
