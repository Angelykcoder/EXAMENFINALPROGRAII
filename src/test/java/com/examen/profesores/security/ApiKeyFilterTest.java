package com.examen.profesores.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class ApiKeyFilterTest {

    private static final String KEY = "examen-api-key-2026";

    private ApiKeyFilter filter;

    @BeforeEach
    void setUp() {
        filter = new ApiKeyFilter(KEY, new ObjectMapper().findAndRegisterModules());
    }

    @Test
    void keyValida_continuaLaCadena() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/profesores");
        request.addHeader("X-API-KEY", KEY);
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(request, response, chain);

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertNotNull(chain.getRequest(), "La petición debió llegar a la cadena");
    }

    @Test
    void keyAusente_devuelve401Json() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/profesores");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(request, response, chain);

        assertEquals(401, response.getStatus());
        assertTrue(response.getContentType().startsWith("application/json"));
        assertTrue(response.getContentAsString().contains("\"status\":401"));
        assertEquals(null, chain.getRequest(), "No debe llegar al controlador");
    }

    @Test
    void keyIncorrecta_devuelve401Json() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/profesores");
        request.addHeader("X-API-KEY", "clave-incorrecta");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(request, response, chain);

        assertEquals(401, response.getStatus());
        assertTrue(response.getContentAsString().contains("API Key inválida"));
        assertEquals(null, chain.getRequest(), "No debe llegar al controlador");
    }

    @Test
    void rutaFueraDeApi_noSeValida() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/actuator/health");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(request, response, chain);

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertNotNull(chain.getRequest());
    }
}
