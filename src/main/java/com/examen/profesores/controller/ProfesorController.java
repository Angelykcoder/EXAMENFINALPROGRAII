package com.examen.profesores.controller;

import com.examen.profesores.dto.ProfesorRequestDTO;
import com.examen.profesores.dto.ProfesorResponseDTO;
import com.examen.profesores.service.ProfesorService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profesores")
public class ProfesorController {

    private final ProfesorService service;

    public ProfesorController(ProfesorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProfesorResponseDTO> crear(@Valid @RequestBody ProfesorRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<ProfesorResponseDTO>> listar(
            @RequestParam(required = false) String especialidad) {
        List<ProfesorResponseDTO> resultado = (especialidad == null || especialidad.isBlank())
                ? service.listar()
                : service.listarPorEspecialidad(especialidad);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfesorResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }
}
