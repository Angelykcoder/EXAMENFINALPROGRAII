package com.examen.profesores.dto;

import com.examen.profesores.model.EstadoProfesor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ProfesorRequestDTO(
        @NotBlank(message = "El nombre es obligatorio") String nombre,
        @NotBlank(message = "El apellido es obligatorio") String apellido,
        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no tiene un formato válido") String correo,
        @NotBlank(message = "La especialidad es obligatoria") String especialidad,
        EstadoProfesor estado) {
}
