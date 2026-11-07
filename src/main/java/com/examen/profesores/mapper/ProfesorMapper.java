package com.examen.profesores.mapper;

import com.examen.profesores.dto.ProfesorRequestDTO;
import com.examen.profesores.dto.ProfesorResponseDTO;
import com.examen.profesores.model.EstadoProfesor;
import com.examen.profesores.model.Profesor;
import org.springframework.stereotype.Component;

@Component
public class ProfesorMapper {

    public Profesor toEntity(ProfesorRequestDTO dto) {
        Profesor profesor = new Profesor();
        profesor.setNombre(dto.nombre());
        profesor.setApellido(dto.apellido());
        profesor.setCorreo(dto.correo());
        profesor.setEspecialidad(dto.especialidad());
        profesor.setEstado(dto.estado() != null ? dto.estado() : EstadoProfesor.ACTIVO);
        return profesor;
    }

    public ProfesorResponseDTO toResponse(Profesor profesor) {
        return new ProfesorResponseDTO(
                profesor.getId(),
                profesor.getNombre(),
                profesor.getApellido(),
                profesor.getCorreo(),
                profesor.getEspecialidad(),
                profesor.getEstado());
    }
}
