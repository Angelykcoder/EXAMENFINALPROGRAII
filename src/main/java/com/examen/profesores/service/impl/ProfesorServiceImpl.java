package com.examen.profesores.service.impl;

import com.examen.profesores.dto.ProfesorRequestDTO;
import com.examen.profesores.dto.ProfesorResponseDTO;
import com.examen.profesores.exception.ProfesorNotFoundException;
import com.examen.profesores.mapper.ProfesorMapper;
import com.examen.profesores.repository.ProfesorRepository;
import com.examen.profesores.service.ProfesorService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProfesorServiceImpl implements ProfesorService {

    private final ProfesorRepository repository;
    private final ProfesorMapper mapper;

    public ProfesorServiceImpl(ProfesorRepository repository, ProfesorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ProfesorResponseDTO crear(ProfesorRequestDTO request) {
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    @Override
    public List<ProfesorResponseDTO> listar() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public List<ProfesorResponseDTO> listarPorEspecialidad(String especialidad) {
        return repository.findByEspecialidad(especialidad).stream().map(mapper::toResponse).toList();
    }

    @Override
    public ProfesorResponseDTO obtenerPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ProfesorNotFoundException(id));
    }
}
