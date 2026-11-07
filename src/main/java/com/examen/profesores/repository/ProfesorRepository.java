package com.examen.profesores.repository;

import com.examen.profesores.model.Profesor;
import java.util.List;
import java.util.Optional;

public interface ProfesorRepository {

    Profesor save(Profesor profesor);

    List<Profesor> findAll();

    Optional<Profesor> findById(Long id);

    List<Profesor> findByEspecialidad(String especialidad);
}
