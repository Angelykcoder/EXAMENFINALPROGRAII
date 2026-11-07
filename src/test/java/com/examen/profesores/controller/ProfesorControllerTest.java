package com.examen.profesores.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.examen.profesores.dto.ProfesorResponseDTO;
import com.examen.profesores.exception.ProfesorNotFoundException;
import com.examen.profesores.model.EstadoProfesor;
import com.examen.profesores.service.ProfesorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProfesorController.class)
class ProfesorControllerTest {

    private static final String API_KEY = "examen-api-key-2026";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfesorService service;

    private static final String BODY_VALIDO = """
            {"nombre":"Juan","apellido":"Pérez","correo":"juan.perez@correo.com","especialidad":"Matemáticas"}
            """;

    @Test
    void crear_valido_devuelve201() throws Exception {
        when(service.crear(org.mockito.ArgumentMatchers.any())).thenReturn(new ProfesorResponseDTO(
                1L, "Juan", "Pérez", "juan.perez@correo.com", "Matemáticas", EstadoProfesor.ACTIVO));

        mockMvc.perform(post("/api/profesores")
                        .header("X-API-KEY", API_KEY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BODY_VALIDO))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("ACTIVO"));
    }

    @Test
    void obtenerPorId_existente_devuelve200() throws Exception {
        when(service.obtenerPorId(1L)).thenReturn(new ProfesorResponseDTO(
                1L, "Juan", "Pérez", "juan.perez@correo.com", "Matemáticas", EstadoProfesor.ACTIVO));

        mockMvc.perform(get("/api/profesores/1").header("X-API-KEY", API_KEY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    void obtenerPorId_inexistente_devuelve404ConCuerpoJson() throws Exception {
        when(service.obtenerPorId(99L)).thenThrow(new ProfesorNotFoundException(99L));

        mockMvc.perform(get("/api/profesores/99").header("X-API-KEY", API_KEY))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.mensaje").value("Profesor con id 99 no encontrado"))
                .andExpect(jsonPath("$.path").value("/api/profesores/99"));
    }

    @Test
    void crear_invalido_devuelve400ConDetalles() throws Exception {
        String body = """
                {"nombre":"","apellido":"Pérez","correo":"no-es-correo","especialidad":"Matemáticas"}
                """;

        mockMvc.perform(post("/api/profesores")
                        .header("X-API-KEY", API_KEY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.detalles.length()").value(2));
    }

    @Test
    void sinApiKey_devuelve401() throws Exception {
        mockMvc.perform(get("/api/profesores"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.error").value("Unauthorized"));
    }
}
