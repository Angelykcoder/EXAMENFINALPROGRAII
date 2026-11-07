package com.examen.profesores.exception;

public class ProfesorNotFoundException extends RuntimeException {

    public ProfesorNotFoundException(Long id) {
        super("Profesor con id " + id + " no encontrado");
    }
}
