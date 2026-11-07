package com.examen.profesores.dto;

import com.examen.profesores.model.EstadoProfesor;

public record ProfesorResponseDTO(
        Long id,
        String nombre,
        String apellido,
        String correo,
        String especialidad,
        EstadoProfesor estado) {
}
