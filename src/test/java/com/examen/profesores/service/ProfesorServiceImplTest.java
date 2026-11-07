package com.examen.profesores.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.examen.profesores.dto.ProfesorRequestDTO;
import com.examen.profesores.dto.ProfesorResponseDTO;
import com.examen.profesores.exception.ProfesorNotFoundException;
import com.examen.profesores.mapper.ProfesorMapper;
import com.examen.profesores.model.EstadoProfesor;
import com.examen.profesores.model.Profesor;
import com.examen.profesores.repository.ProfesorRepository;
import com.examen.profesores.service.impl.ProfesorServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProfesorServiceImplTest {

    @Mock
    private ProfesorRepository repository;

    private ProfesorServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new ProfesorServiceImpl(repository, new ProfesorMapper());
    }

    @Test
    void crearProfesor_guardaYDevuelveConIdYEstadoActivoPorDefecto() {
        ProfesorRequestDTO request = new ProfesorRequestDTO(
                "Juan", "Pérez", "juan.perez@correo.com", "Matemáticas", null);
        when(repository.save(any(Profesor.class))).thenAnswer(inv -> {
            Profesor p = inv.getArgument(0);
            p.setId(1L);
            return p;
        });

        ProfesorResponseDTO response = service.crear(request);

        assertEquals(1L, response.id());
        assertEquals("Juan", response.nombre());
        assertEquals("Matemáticas", response.especialidad());
        assertEquals(EstadoProfesor.ACTIVO, response.estado());
        verify(repository).save(any(Profesor.class));
    }

    @Test
    void obtenerPorId_existente_devuelveProfesor() {
        Profesor profesor = new Profesor(1L, "Juan", "Pérez", "juan.perez@correo.com",
                "Matemáticas", EstadoProfesor.ACTIVO);
        when(repository.findById(1L)).thenReturn(Optional.of(profesor));

        ProfesorResponseDTO response = service.obtenerPorId(1L);

        assertEquals(1L, response.id());
        assertEquals("Pérez", response.apellido());
    }

    @Test
    void obtenerPorId_inexistente_lanzaProfesorNotFoundException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        ProfesorNotFoundException ex = assertThrows(ProfesorNotFoundException.class,
                () -> service.obtenerPorId(99L));

        assertEquals("Profesor con id 99 no encontrado", ex.getMessage());
    }
}
