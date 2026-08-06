package com.seguridad.servicio_autenticacion.repository;
import com.seguridad.servicio_autenticacion.model.LogAcceso;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Estudiante
 */

public interface LogAccesoRepository extends JpaRepository<LogAcceso, Long>{
    //Para que la API (o tu módulo de Python en el futuro) 
    //pueda traer el historial de auditoría de un usuario específico.
   List <LogAcceso> findByNombreUsuario (String nombreUsuario);
}
