package com.examen.profesores.model;

public class Profesor {

    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private String especialidad;
    private EstadoProfesor estado;

    public Profesor() {
    }

    public Profesor(Long id, String nombre, String apellido, String correo,
                    String especialidad, EstadoProfesor estado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.especialidad = especialidad;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public EstadoProfesor getEstado() { return estado; }
    public void setEstado(EstadoProfesor estado) { this.estado = estado; }
}
