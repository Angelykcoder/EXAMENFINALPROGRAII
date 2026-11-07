package com.examen.profesores.service;

import com.examen.profesores.dto.ProfesorRequestDTO;
import com.examen.profesores.dto.ProfesorResponseDTO;
import java.util.List;

public interface ProfesorService {

    ProfesorResponseDTO crear(ProfesorRequestDTO request);

    List<ProfesorResponseDTO> listar();

    List<ProfesorResponseDTO> listarPorEspecialidad(String especialidad);

    ProfesorResponseDTO obtenerPorId(Long id);
}
