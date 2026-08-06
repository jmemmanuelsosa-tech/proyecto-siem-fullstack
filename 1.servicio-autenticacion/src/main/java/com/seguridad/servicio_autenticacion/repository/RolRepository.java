package com.seguridad.servicio_autenticacion.repository;
import com.seguridad.servicio_autenticacion.model.Rol;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Estudiante
 */
public interface RolRepository extends JpaRepository <Rol, Long> {
  
    //método para buscar un rol por su nombre
    Optional<Rol> findByNombreRol(String nombreRol);
}
