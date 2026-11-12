package com.academico.sistema.service;

import com.academico.sistema.model.Estudiante;
import com.academico.sistema.repository.EstudianteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {

    private final EstudianteRepository repository;

    public EstudianteService(EstudianteRepository repository) {
        this.repository = repository;
    }

    public List<Estudiante> listarTodos(String filtro) {
        if (filtro != null && !filtro.trim().isEmpty()) {
            return repository.buscarPorCriterio(filtro.trim());
        }
        return repository.findAll();
    }

    public Optional<Estudiante> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    public void guardar(Estudiante estudiante) {
        // Validar unicidad de código y correo evitando falso positivo en edición
        repository.findByCodigo(estudiante.getCodigo()).ifPresent(existente -> {
            if (!existente.getId().equals(estudiante.getId())) {
                throw new IllegalArgumentException("Ya existe un estudiante registrado con el código: " + estudiante.getCodigo());
            }
        });

        repository.findByCorreo(estudiante.getCorreo()).ifPresent(existente -> {
            if (!existente.getId().equals(estudiante.getId())) {
                throw new IllegalArgumentException("Ya existe un estudiante registrado con el correo: " + estudiante.getCorreo());
            }
        });

        repository.save(estudiante);
    }

    public void eliminar(Integer id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("El estudiante con ID " + id + " no existe.");
        }
        repository.deleteById(id);
    }
}