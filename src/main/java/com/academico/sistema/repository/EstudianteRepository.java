package com.academico.sistema.repository;

import com.academico.sistema.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

    Optional<Estudiante> findByCodigo(String codigo);
    Optional<Estudiante> findByCorreo(String correo);

    @Query("SELECT e FROM Estudiante e WHERE " +
           "LOWER(e.codigo) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
           "LOWER(e.nombres) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
           "LOWER(e.apellidos) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
           "LOWER(e.carrera) LIKE LOWER(CONCAT('%', :filtro, '%'))")
    List<Estudiante> buscarPorCriterio(@Param("filtro") String filtro);
}