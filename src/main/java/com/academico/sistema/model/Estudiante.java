package com.academico.sistema.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "estudiantes")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El código es obligatorio.")
    @Size(min = 4, max = 20, message = "El código debe tener entre 4 y 20 caracteres.")
    @Column(nullable = false, unique = true, length = 20)
    private String codigo;

    @NotBlank(message = "Los nombres son obligatorios.")
    @Size(min = 2, max = 100, message = "Los nombres deben tener entre 2 y 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios.")
    @Size(min = 2, max = 100, message = "Los apellidos deben tener entre 2 y 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String apellidos;

    @NotBlank(message = "El correo es obligatorio.")
    @Email(message = "Ingrese un formato de correo válido.")
    @Column(nullable = false, unique = true, length = 120)
    private String correo;

    @NotBlank(message = "La carrera es obligatoria.")
    @Column(nullable = false, length = 100)
    private String carrera;

    @NotNull(message = "El semestre es obligatorio.")
    @Min(value = 1, message = "El semestre mínimo es 1.")
    @Max(value = 14, message = "El semestre máximo es 14.")
    @Column(nullable = false)
    private Integer semestre;

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }

    public Integer getSemestre() { return semestre; }
    public void setSemestre(Integer semestre) { this.semestre = semestre; }
}