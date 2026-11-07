package com.examen.profesores.repository.impl;

import com.examen.profesores.model.Profesor;
import com.examen.profesores.repository.ProfesorRepository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class ProfesorRepositoryInMemory implements ProfesorRepository {

    private final List<Profesor> profesores = new CopyOnWriteArrayList<>();
    private final AtomicLong secuencia = new AtomicLong(0);

    @Override
    public Profesor save(Profesor profesor) {
        profesor.setId(secuencia.incrementAndGet());
        profesores.add(profesor);
        return profesor;
    }

    @Override
    public List<Profesor> findAll() {
        return List.copyOf(profesores);
    }

    @Override
    public Optional<Profesor> findById(Long id) {
        return profesores.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    @Override
    public List<Profesor> findByEspecialidad(String especialidad) {
        return profesores.stream()
                .filter(p -> p.getEspecialidad().equalsIgnoreCase(especialidad))
                .toList();
    }
}
